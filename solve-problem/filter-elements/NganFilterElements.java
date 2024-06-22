import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NganFilterElements {
    public static void main(String[] args) {
        filter(List.of(4,5,2,5,4,3,1,3,4), 2);
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
