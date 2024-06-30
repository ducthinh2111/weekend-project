package ori;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterElements {
    public static void main(String[] args) throws Exception {
        /*Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int k = Integer.parseInt(args[0].split(" ")[1]);
            List<Integer> array = Arrays.stream(args[i+1].split(" "))
                    .map(Integer::parseInt).collect(Collectors.toList());
            filter(array, k);
        }
        for (int i = 0; i < args.length; i+=2) {
            int k = Integer.parseInt(args[0].split(" ")[1]);
            List<Integer> array = Arrays.stream(args[i+1].split(" "))
                    .map(Integer::parseInt).collect(Collectors.toList());
            filter(array, k);
        }*/
    }

    private static void filter(List<Integer> input, int k) {
        Map<Integer, List<Integer>> group = input.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet().stream().filter(e -> e.getValue().size() >= k)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<String> result = input.stream().distinct().
                filter(group::containsKey).map(Object::toString).collect(Collectors.toList());
        if (result.isEmpty()) {
            System.out.println(-1);
        } else {
            System.out.println(String.join(",", result));
        }
    }
}
