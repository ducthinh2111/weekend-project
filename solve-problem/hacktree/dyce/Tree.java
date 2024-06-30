import java.util.*;
public class Tree {
    public static void main(String[] args) {
        Tree t = new Tree();
        t.test("1st", inp1st, oup1st);
        t.test("2nd", inp2nd, oup2nd);
        t.test("3rd", inp3rd, oup3rd);
    }

    void test(String name, String inp , int expected) {
        int actual = count(inp);
        if (actual == expected)
            System.out.println("[ok] " + name);
        else
            System.out.println("[n!] " + name + ": expected >"+expected+"< but was >"+actual+"<");
    }

    int count(String inp) {
        String[] lines = inp.split("\n");
        int n = Integer.parseInt(lines[0]);
        int[] cost = new int[n+1];
        for (int i = 1; i <= n; i++)
            cost[i] = Integer.parseInt(lines[i]);
        int t = lines.length-1;
        int k = Integer.parseInt(lines[t]);
        int[][] links = new int[n-1][];
        int gi = 0;
        for (int i = n+2; i < t; i++) {
            String[] p = lines[i].split(" ");
            int[] link = new int[2];
            link[0] = Integer.parseInt(p[0]);
            link[1] = Integer.parseInt(p[1]);
            links[gi++] = link;
        }
        Nut tree = grow(links);
        //tree.print("", cost);
        return walk(tree, 0, "", cost, k, new HashMap<>());
    }

    Nut grow(int[][] links) {
        Nut r = new Nut();
        r.num = 1;
        Queue<Nut> q = new LinkedList<>();
        q.offer(r);
        int[][] lo = links;
        while (!q.isEmpty()) {
            Nut n = q.poll();
            Dto d = filter(lo, n.num);
            lo = d.lo;
            int[] arr = d.arr;
            n.subs = new Nut[arr.length];
            for (var i = 0; i < arr.length; i++) {
                Nut s = new Nut();
                s.num = arr[i];
                n.subs[i] = s;
                q.offer(s);
            }
        }
        return r;
    }

    Dto filter(int[][] links, int num) {
        int len = links.length;
        int[][] lo = new int[len][];
        int[] arr = new int[len];
        if (len == 0)
            return new Dto(lo, arr);
        int li = 0;
        int ai = 0;
        for (int i = 0; i < len; i++)
            if (links[i][0] == num)
                arr[ai++] = links[i][1];
            else if (links[i][1] == num)
                arr[ai++] = links[i][0];
            else
                lo[li++] = links[i];
        int[][] loTrim = lo;
        if (li != len) {
            loTrim = new int[li][];
            System.arraycopy(lo, 0, loTrim, 0, li);
        }
        int[] arrTrim = arr;
        if (ai != len) {
            arrTrim = new int[ai];
            System.arraycopy(arr, 0, arrTrim, 0, ai);
        }
        return new Dto(loTrim, arrTrim);
    }

    int walk(Nut t, int pre, String ap, int[] cost, int k, Map<String, Boolean> m) {
        String nap = ap+t.num;
        //System.out.println("at "+t.num+": mod="+mod+" ap="+nap);
        if (m.put(nap, true) != null)
            return 0;
        int sum = 0;
        int mod = (pre + cost[t.num]) % k;
        if (mod == 0)
            sum++;
        for (int i = 0; i < t.subs.length; i++) 
            sum += walk(t.subs[i], mod, nap, cost, k, m) + walk(t.subs[i], 0, "", cost, k, m);
        return sum;
    }

    private static class Nut {
        private int num;
        private Nut[] subs;

        void print(String pre, int[] cost) {
            System.out.println(pre + num + "("+cost[num]+")");
            String npre = pre + "-- ";
            if (subs == null) throw new RuntimeException("null @" + num);
            for (int i = 0; i < subs.length; i++)
                subs[i].print(npre, cost);
        }
    }

    private static class Dto {
        private int[][] lo;
        private int[] arr;
        Dto (int[][] a, int[] b) { lo = a; arr = b; }
    }

    private static final String inp1st = ""
        + "5\n"
        + "1\n"
        + "2\n"
        + "2\n"
        + "1\n"
        + "2\n"
        + "5 4\n"
        + "2 3\n"
        + "2 1\n"
        + "1 4\n"
        + "2 5\n"
        + "2\n";
    private static final int oup1st = 6;
    private static final String inp2nd = ""
        + "5\n"
        + "2\n"
        + "3\n"
        + "0\n"
        + "3\n"
        + "0\n"
        + "5 4\n"
        + "2 3\n"
        + "3 1\n"
        + "3 4\n"
        + "3 5\n"
        + "3\n";
    private static final int oup2nd = 7;
    private static final String inp3rd = ""
        + "4\n"
        + "1\n"
        + "1\n"
        + "1\n"
        + "1\n"
        + "4 3\n"
        + "1 2\n"
        + "1 4\n"
        + "4 3\n"
        + "1\n";
    private static final int oup3rd = 8;
}
