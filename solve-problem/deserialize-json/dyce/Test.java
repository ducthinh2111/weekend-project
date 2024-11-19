import java.util.*;
import java.io.*;

public class Test {
	public static void main(String[] args) throws Exception {
		Test ins = new Test();
		ins.testArrayOfObject();
		ins.testSimpleArray();
		ins.testSimpleObject();
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
		check("2".equals(obj.getAc()));
		el = arr.get(1);
		check(el instanceof Blandit);
		obj = (Blandit)el;
		check(!obj.getCursus());
		check("4".equals(obj.getAc()));
	}

	void testSimpleArray() throws Exception {
		Object raw = new Core().toDto(f2s("a2-arr.json"), t(List.class, t(Integer.class)));
		check(raw instanceof List);
		List<?> arr = (List)raw;
		check(arr.size() == 2);
		Object e = arr.get(0);
		check(e instanceof Integer);
		check((int)e == 11);
		e = arr.get(1);
		check(e instanceof Integer);
		check((int)e == 32);
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
}
