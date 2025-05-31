import java.util.*;

public class ProblemOn2452025 {
    public static void main(String[] args) {
        largestPalindrome();
    }

    private static void sumEvenFibonacciLessThan4Million() {
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

    private static void largestPrimeFactorsOf(long num) {
        long t = new Date().getTime();
        long index = 2;
        long max = num/2;
        while (index <= max) {
            if (num%index != 0) {
                index++;
                continue;
            }
            long temp = num / index;
            boolean flag = true;
            for(long i = 2; i < temp - 1; i++ ) {
                if (temp%i == 0) {
                    flag = false;
                    break;
                }
            }
            index++;
            if (flag) {
                System.out.println(temp);
                break;
            }
        }
        System.out.println((new Date().getTime()) - t);
    }

    private static void largestPalindrome() {
        long num = 999;
        int each = 10;
        boolean flag = false;
        for (int i = 0; i < 100; i+= each){
            long k = num - i;
            for (int j  = 0; j < 10; j++) {
                long num1 = k - j;
                int t = i + 1;
                while (t <= (each + i) && t < num1){
                    long num2 = num1 - t;
                    long temp = num1 * num2;
                    String s1 = Long.toString(temp);
                    String s2 = new StringBuilder(s1).reverse().toString();
                    if (s1.equals(s2)){
                        System.out.println(temp);
                        flag = true;
                        break;
                    }
                    t++;
                }
                if (flag) {
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
    }
}


