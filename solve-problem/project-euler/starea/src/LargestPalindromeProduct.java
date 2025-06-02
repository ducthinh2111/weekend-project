
public class LargestPalindromeProduct {
    public static void main(String[] args) {
        int largestProduct = 99980001;
        for (int i = largestProduct; i > 0; i--) {
            if (isPalindrome(String.valueOf(i)) && isProductOfThreeDigits(i)) {
                System.out.println(i);
                break;
            }
        }
    }

    private static boolean isProductOfThreeDigits(int number) {
        for (int i = 9999; i >= 1000; i--) {
            if (number % i == 0 && String.valueOf(number / i).length() == 4) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPalindrome(String text) {
        boolean isPalindrome = true;
        for (int i = 0; i < text.length() / 2; i++) {
            if (text.charAt(i) != text.charAt(text.length() - 1 - i)) {
                isPalindrome = false;
                break;
            }
        }
        return isPalindrome;
    }
}
