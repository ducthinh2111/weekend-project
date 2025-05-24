public class SumEvenFibonacci {
    public static void main(String[] args) {
        double pre = 1;
        double mid = 1;
        double next = pre + mid;
        double sum = 0;
        System.out.println(mid);
        System.out.println(next);
        do {
            if (next%2 == 0) {
                sum += next;
            }
            pre = mid;
            mid = next;
            next = pre + mid;
            System.out.println(next);
        } while (next < 4000000);
        System.out.println(sum);
    }
}
