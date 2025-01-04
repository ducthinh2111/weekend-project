import java.util.*;
import java.lang.reflect.*;
import java.util.Map.Entry;
import com.google.gson.*;

public class Core {
	private LinkedList<String> trace;
	private JsonObject refStore;
	private Map<String, Object> refs;

	private static final Object NOPE = new Object(){};

	public Object toDto(String json, Tipe tipe) throws Exception {
		JsonElement je = JsonParser.parseString(json);
		trace = new LinkedList<>();
		refStore = loadRefStore(je);
		if (refStore != null) refs = new HashMap<>();
		try {
			return readJe(je, tipe);
		} catch(Exception e) {
			StringBuilder sb = new StringBuilder();
			sb.append("die at [");
			for (int i = trace.size()-1; i>=0; i--)
				sb.append('.').append(trace.get(i));
			sb.append("]");
			throw new Error(sb.toString(), e);
		}
	}

	Object readJe(JsonElement je, Tipe tf0) throws Exception {
		Tipe tip = readT(je, tf0);
		Object leaf = readLeaf(je, tip);
		if (leaf != NOPE)
			return leaf;
		if (tip.isArray())
			return readArr(je.getAsJsonArray(), tip);
		if (tip.isMap())
			return readMap(je.getAsJsonObject(), tip);
		Object obj = tip.newBase();
		JsonObject jo = je.getAsJsonObject();
		for (Entry<String, JsonElement> en: jo.entrySet()) {
			String field = en.getKey();
			if ("_t".equals(field)) continue;
			if ("_ref".equals(field)) continue;
			trace.push(field);
			Type gent = tip.base.getMethod(preG(field)).getGenericReturnType();
			Tipe ftype = Tipe.of(gent);
			Object val = readJe(en.getValue(), ftype);
			tip.base.getMethod(preS(field), ftype.base).invoke(obj, val);
			trace.pop();
		}
		return obj;
	}

	String preG(String field) { return preX("get", field); }

	String preX(String p, String field) {
		StringBuilder sb = new StringBuilder(field.length() + 3);
		sb.append(p);
		sb.append(Character.toUpperCase(field.charAt(0)));
		for (int i = 1; i < field.length(); i++)
			sb.append(field.charAt(i));
		return sb.toString();
	}

	String preS(String field) { return preX("set", field); }

	Object readLeaf(JsonElement je, Tipe tip) throws Exception {
		switch(tip.base.getSimpleName()) {
			case "Boolean":
			case "boolean":
				return je.getAsBoolean();
			case "Integer":
			case "int":
				return je.getAsInt();
			case "String":
				return je.getAsString();
			case "Date":
				return new Date(je.getAsLong());
		}
		if (!je.isJsonPrimitive())
			return NOPE;
		JsonPrimitive jp = je.getAsJsonPrimitive();
		if (!jp.isString())
			return NOPE;
		String txt = jp.getAsString();
		if (txt.startsWith("_ref:"))
			return getRef(txt, tip);
		if (txt.startsWith("_t:")) {
			int semi = txt.indexOf(';');
			String type = txt.substring(3, semi);
			String val = txt.substring(semi+1, txt.length());
			return txt2Prim(val, Tipe.from(type));
		}
		return NOPE;
	}

	@SuppressWarnings("unchecked")
	Object readArr(JsonArray ja, Tipe tip) throws Exception {
		ArrayList arr = new ArrayList(ja.size());
		int idx = 0;
		for (JsonElement i: ja.asList()) {
			trace.push(idx + "");
			arr.add( readJe(i, tip.args[0]) );
			trace.pop();
			idx++;
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	Object readMap(JsonObject jo, Tipe tip) throws Exception {
		if (tip.args.length != 2)
			throw new Error("unmatch given params for map, expected=2 but was="+tip.args.length);
		Map m = new HashMap();
		for (Entry<String, JsonElement> ent: jo.entrySet()) {
			String skey = ent.getKey();
			if ("_ref".equals(skey)) continue;
			if ("_t".equals(skey)) continue;
			trace.push(skey);
			Object key = txt2Prim(skey, tip.args[0]);
			Object val = readJe(ent.getValue(), tip.args[1]);
			m.put(key, val);
			trace.pop();
		}
		return m;
	}

	Object txt2Prim(String skey, Tipe tip) throws Exception {
		switch(tip.base.getSimpleName()) {
			case "Boolean":
				return "true".equals(skey);
			case "Integer":
				return Integer.parseInt(skey);
			case "String":
				return skey;
			case "Date":
				long millis = Long.parseLong(skey);
				return new Date(millis);
		}
		if (skey.startsWith("_ref:")) {
			return getRef(skey, tip);
		}
		throw new Error("unsupported inline type: " + tip.base);
	}

	JsonObject loadRefStore(JsonElement je) {
		if (je.isJsonObject())
			return je.getAsJsonObject().getAsJsonObject("_ref");
		if (je.isJsonArray()) {
			JsonArray root = je.getAsJsonArray();
			JsonElement tail = root.get(root.size()-1);
			if (!tail.isJsonArray()) return null;
			JsonArray tailArr = tail.getAsJsonArray();
			if (tailArr.size() != 2) return null;
			JsonElement mark = tailArr.get(0);
			if (!mark.isJsonPrimitive() || !"_ref".equals(mark.getAsString())) return null;
			return tailArr.get(1).getAsJsonObject();
		}
		return null;
	}

	Object getRef(String name, Tipe tip) throws Exception {
		if (refStore == null) throw new Error("undefined ref: " + name);
		Object cache = refs.get(name);
		if (cache != null) return cache;
		JsonElement je = refStore.get(name);
		if (je == null) throw new Error("ref not found: " + name);
		Object ref = readJe(je, tip);
		refs.put(name, ref);
		return ref;
	}

	Tipe readT(JsonElement je, Tipe tf0) throws Exception {
		if (je.isJsonObject()) {
			JsonElement jt = je.getAsJsonObject().get("_t");
			if (jt == null)
				return tf0;
			return Tipe.from(jt.getAsString());
		}
		return tf0;
	}

	public static Tipe t(Class<?> b, Tipe... ts) {
		return new Tipe(b, ts);
	}

	public static class Tipe {
		Class<?> base;
		Tipe[] args;
		short l; // layer
		Tipe (Class<?> b, Tipe[] ts) { base=b; args=ts; }
		Tipe (Class<?> b, Tipe[] ts, short n) { base=b; args=ts; l=n; }
		Object newBase() throws Exception {
			return base.getDeclaredConstructor().newInstance();
		}
		boolean isArray() { return base == List.class; }
		boolean isMap() { return base == Map.class; }
		static Tipe of(Type t) {
			if (t instanceof Class)
				return new Tipe((Class)t, null);
			if (t instanceof ParameterizedType) {
				ParameterizedType p = (ParameterizedType)t;
				Type rt = p.getRawType();
				Type[] args = p.getActualTypeArguments();
				if (!(rt instanceof Class)) {
					throw new Error("unsupported raw: " + rt.getClass());
				}
				Tipe[] tips = new Tipe[args.length];
				for (int i = 0; i < args.length; i++)
					tips[i] = Tipe.of(args[i]);
				return new Tipe((Class)rt, tips);
			}
			throw new Error("unsupported type: " + t.getClass());
		}
		static Tipe from(String t) throws Exception {
			Deque<Tipe> stack = new LinkedList<>();
			char[] buf = new char[t.length()];
			int bi = 0;
			short l = 0;
			for (int i = 0; i < buf.length; i++) {
				char c = t.charAt(i);
				switch(c) {
					case '<':
						stack.push(loadTipe(buf, bi, l));
						bi = 0;
						l++;
						continue;
					case '>':
						LinkedList<Tipe> args = new LinkedList<>();
						if (bi != 0)
							args.addFirst( loadTipe(buf, bi, l) );
						while (stack.peek().l == l)
							args.addFirst(stack.pop());
						stack.peek().args = args.toArray(new Tipe[args.size()]);
						bi = 0;
						l--;
						continue;
					case ',':
						if (bi != 0)
							stack.push(loadTipe(buf, bi, l));
						bi = 0;
						continue;
					case ' ':
						continue;
					default:
						buf[bi++] = c;
						break;
				}
			}
			if (!stack.isEmpty())
				return stack.pop();
			return loadTipe(buf, bi, (short)0);
		}
		static Tipe loadTipe(char[] buf, int bi, short l) throws Exception {
			char[] dst = new char[bi];
			System.arraycopy(buf, 0, dst, 0, bi);
			String name = new String(dst);
			Class<?> clazz = null;
			switch(name) {
				case "Boolean":
					clazz = Boolean.class; break;
				case "Integer":
					clazz = Integer.class; break;
				case "String":
					clazz = String.class; break;
				case "Date":
					clazz = Date.class; break;
				case "List":
					clazz = List.class; break;
				case "Map":
					clazz = Map.class; break;
				default:
					clazz = Class.forName(name);
					break;
			}
			return new Tipe(clazz, null, l);
		}
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(base.getSimpleName());
			if (args == null || args.length == 0)
				return sb.toString();
			sb.append("{ ");
			for (int i = 0; i < args.length; i++)
				sb.append(args[i].toString()).append(" , ");
			return sb.append('}').toString();
		}
	}

	static class Error extends RuntimeException {
		Error(String msg) { super(msg); }
		Error(String msg, Exception e) { super(msg, e); }
	}
}
