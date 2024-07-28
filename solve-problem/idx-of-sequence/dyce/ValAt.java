public class ValAt {
    public static void main(String[] args) {
        int idx = 920897186;
        long tp = System.nanoTime();
        int v = valAt(idx);
        double taken = (System.nanoTime() - tp)/1000.0;
        System.out.println("seq["+idx+"] <- " + v + " in "+taken+" ms");
    }
    public static int valAt(int idx) {
        short[] val = new short[27];
        int lead = 0;
        int base = 0;
        for (int i = 0; i < 27; i++) {
            int tail = 26 - i;
            int breaki = countBreak(lead, base, tail);
            if (breaki <= idx) {
                lead++;
                base = breaki;
                val[i] = 1;
            } else {
                val[i] = 0;
            }
        }
        //System.out.println("base: " + Integer.toBinaryString(base) + " << " + base);
        //printarr(val);
        return bi2de(val);
    }
    static int countBreak(int lead, int base, int tail) {
        int w = tail;
        int h = (int)Math.pow(2, w);
        int block = (int)(h * 0.5 * w);
        int idx =  h*lead + block + base;
        //System.out.println("lead: " + lead + "; base: " + base + "; tail: " + tail + "; idx: " + idx);
        return idx;
    }
    static void printarr(short[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++)
            System.out.print(", "+arr[i]);
        System.out.println("]");

    }
    static int bi2de(short[] arr) {
        int v = 0;
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == 1)
                v = v*2 + 1;
            else
                v = v*2;
        //System.out.println("v: " + v + "; bv: " + Integer.toBinaryString(v));
        return v;
    }
}
