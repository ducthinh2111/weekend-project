import java.util.*;
import java.lang.reflect.*;
import java.util.Map.Entry;
import com.google.gson.*;

public class Core {
	private LinkedList<String> trace;

	private static final Object NOPE = new Object(){};

	public Object toDto(String json, Tipe tipe) throws Exception {
		JsonElement je = JsonParser.parseString(json);
		trace = new LinkedList<>();
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

	Object readJe(JsonElement je, Tipe tip) throws Exception {
		Object leaf = readLeaf(je, tip.base);
		if (leaf != NOPE)
			return leaf;
		if (tip.isArray())
			return readArr(je.getAsJsonArray(), tip);
		Object obj = tip.newBase();
		JsonObject jo = je.getAsJsonObject();
		for (Entry<String, JsonElement> en: jo.entrySet()) {
			String field = en.getKey();
			if ("_t".equals(field)) continue;
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

	Object readLeaf(JsonElement je, Class<?> clazz) {
		switch(clazz.getSimpleName()) {
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

	public static Tipe t(Class<?> b, Tipe... ts) {
		return new Tipe(b, ts);
	}

	public static class Tipe {
		Class<?> base;
		Tipe[] args;
		Tipe (Class<?> b, Tipe[] ts) {
			base = b;
			args = ts;
		}
		Object newBase() throws Exception {
			return base.getDeclaredConstructor().newInstance();
		}
		boolean isArray() {
			return base == List.class;
		}
		static Tipe of(Type t) {
			if (t instanceof Class)
				return new Tipe((Class)t, null);
			throw new Error("not supported type: " + t.getClass());
		}
	}

	static class Error extends RuntimeException {
		Error(String msg) { super(msg); }
		Error(String msg, Exception e) { super(msg, e); }
	}
}
