public class Verify {
    public static void main(String[] args) throws Exception {
        int[] seq = new int[4932];
        int si = 0;
        for (int i = 0; i < 1000; i++) {
            String bf = Integer.toBinaryString(i);
            for (int j =0; j < bf.length(); j++)
                if (bf.charAt(j) == '1')
                    seq[si++] = i;
        }
        boolean broke = false;
        System.out.println("verify first " + si + " item in seq ...");
        for (int i = 0; i < si; i++)
            if (seq[i] != ValAt.valAt(i)) {
                System.out.println("!!! broke at: " + i);
                broke = true;
                break;
            }
        if (!broke)
            System.out.println("ok!");
    }
}
