import java.util.*;

public class FilterElements {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = Integer.parseInt(scanner.nextLine());
        while (testCases-- > 0) {
            String dataDescription = scanner.nextLine();
            int appearance = Integer.parseInt(dataDescription.split(" ")[1]);
            String[] elements = scanner.nextLine().split(" ");
            Map<String, Integer> keeper = new LinkedHashMap<>();
            for (String element : elements) {
                keeper.merge(element, 1, Integer::sum);
            }
            List<String> result = new ArrayList<>();
            keeper.forEach((key, value) -> {
                if (value >= appearance) {
                    result.add(key);
                }
            });
            if (result.isEmpty()) {
                System.out.println("-1");
            } else {
                System.out.println(String.join(" ", result));
            }
        }

    }
}
