public class EvenFibonacci {
    public static void main(String[] args) {
        Long sum = 0L;
        Long previous1 = 1L;
        Long previous2 = 2L;
        Long current = 2L;

        while (current <= 4000000L) {
            if (current % 2 == 0) {
                sum += current;
            }
            current = previous1 + previous2;
            previous1 = previous2;
            previous2 = current;
        }
        System.out.println(sum);
    }
}