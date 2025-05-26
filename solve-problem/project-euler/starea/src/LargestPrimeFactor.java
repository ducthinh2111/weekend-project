public class LargestPrimeFactor {
    public static void main(String[] args) {
        long number = 600851475143L;
        long largestFactor = 0L;

        for (long i = 2L; i < number; i++) {
            if (number % i == 0) {
                largestFactor = number / i;
                boolean isPrime = true;
                for (long j = 2L; j < largestFactor; j++) {
                    if (largestFactor % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) {
                    break;
                }
            }
        }
        System.out.println(largestFactor);
    }
}
