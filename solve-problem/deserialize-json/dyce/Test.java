import java.util.*;
import java.io.*;

public class Test {
	public static void main(String[] args) throws Exception {
		Test ins = new Test();

		ins.testArrayInArray();
		ins.testObjectInObject();
		ins.testObjectHaveArrayField();
		ins.testArrayOfObject();
		ins.testSimpleArray();
		ins.testSimpleObject();
	}

	void testArrayInArray() throws Exception {
		Object raw = new Core().toDto(
				f2s("a6-arr-in-arr.json"),
				t(List.class, t(List.class, t(String.class))));
		check(raw instanceof List);
		List<?> list = (List)raw;
		check(2 == list.size());
		Object rSub = list.get(0);
		check(rSub instanceof List);
		List<?> lSub = (List)rSub;
		check(2 == lSub.size());
		check("13".equals(lSub.get(0)));
		check("14".equals(lSub.get(1)));
		rSub = list.get(1);
		check(rSub instanceof List);
		lSub = (List)rSub;
		check(2 == lSub.size());
		check("15".equals(lSub.get(0)));
		check("16".equals(lSub.get(1)));
	}
	void testObjectInObject() throws Exception {
		Object raw = new Core().toDto(f2s("a5-obj-in-obj.json"), t(Mollis.class));
		check(raw instanceof Mollis);
		Mollis obj = (Mollis)raw;
		check(1732182249050L == obj.getDiam().getTime());
		check(11 == obj.getLobortis().getEuismod());
		check("12".equals(obj.getLobortis().getCongue()));
	}
	void testObjectHaveArrayField() throws Exception {
		Object raw = new Core().toDto(f2s("a4-objec-have-array-field.json"), t(Tempor.class));
		check(raw instanceof Tempor);
		Tempor obj = (Tempor)raw;
		check(obj.getMagna() == 8);
		raw = obj.getTellus();
		check(raw instanceof List);
		List<?> arr = (List)raw;
		check(arr.size() == 2);
		raw = arr.get(0);
		check(raw instanceof String);
		check("9".equals(raw));
		raw = arr.get(1);
		check(raw instanceof String);
		check("10".equals(raw));
	}

	void testArrayOfObject() throws Exception {
		Object raw = new Core().toDto(f2s("a3-arr-of-obj.json"), t(List.class, t(Blandit.class)));
		check(raw instanceof List);
		List<?> arr = (List)raw;
		check(arr.size() == 2);
		Object el;
		Blandit obj;
		el = arr.get(0);
		check(el instanceof Blandit);
		obj = (Blandit)el;
		check(obj.getCursus());
		check("6".equals(obj.getAc()));
		el = arr.get(1);
		check(el instanceof Blandit);
		obj = (Blandit)el;
		check(!obj.getCursus());
		check("7".equals(obj.getAc()));
	}

	void testSimpleArray() throws Exception {
		Object raw = new Core().toDto(f2s("a2-arr.json"), t(List.class, t(Integer.class)));
		check(raw instanceof List);
		List<?> arr = (List)raw;
		check(arr.size() == 2);
		Object e = arr.get(0);
		check(e instanceof Integer);
		check((int)e == 4);
		e = arr.get(1);
		check(e instanceof Integer);
		check((int)e == 5);
	}
	void testSimpleObject() throws Exception {
		Object raw = new Core().toDto(f2s("a1-simple.json"), t(Cras.class));
		check(raw instanceof Cras);
		Cras obj = (Cras)raw;
		check("1".equals(obj.getSit()));
		check(1731832697345L == obj.getSuscipit().getTime());
		check(3 == obj.getMassa());
	}

	void check(boolean fact, String... msg) {
		if (fact) return;
		throw new RuntimeException("fail check: " + (msg.length == 0 ? "<unknown>" : msg[0]));
	}

	String f2s(String file) throws Exception {
		try (BufferedReader r = new BufferedReader(new FileReader(file))){
			StringBuilder sb = new StringBuilder();
			String l;
			while (null != (l=r.readLine()))
				sb.append(l).append('\n');
			return sb.toString();
		}
	}

	public static Core.Tipe t(Class<?> clazz, Core.Tipe... subs) {
		return Core.t(clazz, subs);
	}

	static class Cras {
		String sit;
		Date suscipit;
		int massa;
		public String getSit(){ return sit; }
		public Date getSuscipit(){ return suscipit; }
		public int getMassa(){ return massa; }
		public void setSit(String sit){ this.sit = sit; }
		public void setSuscipit(Date suscipit){ this.suscipit = suscipit; }
		public void setMassa(int massa){ this.massa = massa; }
	}

	static class Blandit {
		boolean cursus;
		String ac;
		public boolean getCursus(){ return cursus; }
		public String getAc(){ return ac; }
		public void setCursus(boolean cursus){ this.cursus = cursus; }
		public void setAc(String ac){ this.ac = ac; }
	}

	static class Tempor {
		int magna;
		List<String> tellus;
		public int getMagna(){ return magna; }
		public List<String> getTellus(){ return tellus; }
		public void setMagna(int magna){ this.magna = magna; }
		public void setTellus(List<String> tellus){ this.tellus = tellus; }
	}

	public static class Mollis {
		Date diam;
		public Date getDiam() { return diam; }
		public void setDiam(Date v) { diam=v; }
		Hendrerit lobortis;
		public Hendrerit getLobortis() { return lobortis; }
		public void setLobortis(Hendrerit v) { lobortis=v; }
	}

	public static class Hendrerit {
		int euismod;
		public int getEuismod() { return euismod; }
		public void setEuismod(int v) { euismod=v; }
		String congue;
		public String getCongue() { return congue; }
		public void setCongue(String v) { congue=v; }
	}
}
