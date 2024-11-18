import java.util.*;
import java.util.Map.Entry;
import com.google.gson.*;

public class Core {

	private static final Object NOPE = new Object(){};

	public Object toDto(String json, Tipe tipe) throws Exception {
		JsonElement je = JsonParser.parseString(json);
		return readJe(je, tipe);
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
			Class<?> ftype = tip.base.getMethod(preG(field)).getReturnType();
			Object val = readJe(en.getValue(), t(ftype));
			tip.base.getMethod(preS(field), ftype).invoke(obj, val);
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
		for (JsonElement i: ja.asList())
			arr.add( readJe(i, tip.args[0]) );
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
	}
}
