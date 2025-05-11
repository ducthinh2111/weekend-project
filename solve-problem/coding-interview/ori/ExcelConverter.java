import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExcelConverter {

    private static final List<Character> letters = IntStream.rangeClosed('A', 'Z')
            .mapToObj(v -> (char) v).collect(Collectors.toList());
    private static final Map<Character, Integer> cmp = IntStream.range(0, letters.size())
            .boxed()
            .collect(Collectors.toMap(letters::get, i -> i));

    public static void main(String[] args) {
        //convertToIndex("YQHOW");
        double input = 987915;
        convertToName(input);
    }

    private static void convertToIndex(String name) {
        int ms = cmp.size();
        int ns = name.length();
        int index = 0;
        char[] cn = name.toCharArray();
        for (int i = 1; i <= ns; i++) {
            int ic = cmp.get(cn[i - 1]) + 1;
            int t = ns - i;
            index += (int) ((Math.pow(ms, t) ) * ic);
        }
        System.out.println(name +": " + (index -1));
    }

    private static void convertToName(double index) {
        convertToEachLetter(index, "");
    }

    private static void convertToEachLetter(double index, String letter) {
        if (index <= 0) {
            System.out.println("result: " + letter);
            return;
        }
        double exp = findExponent(index);
        double pow = Math.pow(26, exp);
        double mod = index%pow;
        double i = (index - mod)/pow;
        letter += letters.get((int) (i - 1));
        convertToEachLetter(mod, letter);
    }

    private static double findExponent(double number) {
        double temp = number;
        double time = 0;
        while (temp >= 26) {
            temp /= 26;
            time ++;
        }
        return time;
    }
}
