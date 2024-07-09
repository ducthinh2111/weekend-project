import java.util.*;
public class Kit {
    int[] pre;
    int val;
    Kit(int[] p, int v) { pre = p; val = v; }

    public static void main(String[] args) {
        Kit k = new Kit(null, 0);
        k.test("1st", inp1st, oup1st);
        k.test("2nd", inp2nd, oup2nd);
        k.test("3rd", inp3rd, oup3rd);
    }

    void test(String name, String inp , int expected) {
        int actual = count(inp);
        if (actual == expected)
            System.out.println("[ok] " + name);
        else
            System.out.println("[n!] " + name + ": expected >"+expected+"< but was >"+actual+"<");
    }

    int count(String inp) {
        boolean verbose = false;
        String[] lines = inp.split("\n");
        int n = Integer.parseInt(lines[0]);
        int[] cost = new int[n+1];
        for (int i = 1; i <= n; i++)
            cost[i] = Integer.parseInt(lines[i]);
        int t = lines.length-1;
        int k = Integer.parseInt(lines[t]);
        List<Link> links = new LinkedList<>();
        for (int i = n+2; i < t; i++) {
            String[] p = lines[i].split(" ");
            if (verbose) System.out.println("link: " + p[0] + " - " + p[1]);
            links.add(new Link(Integer.parseInt(p[0]), Integer.parseInt(p[1])));
        }
        int out = 0;
        int[] pre = new int[0];
        int val = 1;
        Deque<Kit> stack = new LinkedList<>();
        stack.offerFirst(new Kit(null, 0));
        while (!stack.isEmpty()) {
            if (verbose) System.out.println("load " + val);
            int[] newpre = new int[pre.length+1];
            System.arraycopy(pre, 0, newpre, 0, pre.length);
            int vcost = cost[val];
            for (int i = 0; i < newpre.length; i++) {
                int v = (newpre[i] + vcost) % k;
                newpre[i] = v;
                if (v == 0)
                    out++;
            }
            int newval = getLink(val, links);
            if (newval == -1) {
                Kit head = stack.peekFirst();
                if (verbose) System.out.println("backtry " + head.val);
                while (head!=null && (newval = getLink(head.val, links)) == -1) {
                    stack.pollFirst();
                    head = stack.peekFirst();
                    if (verbose) if (head!=null) System.out.println("backtry " + head.val);
                }
                if (head!=null) {
                    if (verbose) System.out.println("backfound " + newval);
                    pre = head.pre;
                    val = newval;
                }
            } else {
                if (verbose) System.out.println("found " + newval);
                stack.offerFirst(new Kit(newpre, val));
                pre = newpre;
                val = newval;
            }
        }
        return out;
    }

    int getLink(int v, List<Link> links) {
        Iterator<Link> ite = links.iterator();
        while (ite.hasNext()) {
            Link l = ite.next();
            if (l.left == v) {
                ite.remove();
                return l.right;
            } else if (l.right == v) {
                ite.remove();
                return l.left;
            }
        }
        return -1;
    }

    private class Link {
        int left;
        int right;
        Link(int l, int r) { left = l; right = r; }
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
