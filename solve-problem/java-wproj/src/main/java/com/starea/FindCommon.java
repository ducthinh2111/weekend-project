import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FindCommon {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(2, 4, 6, 8, 10, 12, 14, 16);
        List<Integer> list2 = Arrays.asList(3, 4, 7, 8, 9, 12, 13, 16, 20, 21, 22, 23);

        findCommon(list1, list2);
    }

    public static Set<Integer> findCommon(List<Integer> list1, List<Integer> list2) {
        Map<Integer, Integer> occurence = new HashMap<>();
        Set<Integer> result = new HashSet<>();

        for (int i = 0; i < list1.size(); i++) {
            occurence.put(list1.get(i), 1);
        }

        for (int i = 0; i < list2.size(); i++) {
            int count = occurence.getOrDefault(list2.get(i), 0);
            count += 2;
            occurence.put(list2.get(i), count);
        }

        List<Integer> list1AfterRemoved = new ArrayList<>();        
        List<Integer> list2AfterRemoved = new ArrayList<>();        
        List<Integer> common = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : occurence.entrySet()) {
            switch (entry.getValue()) {
                case 1: 
                    list1AfterRemoved.add(entry.getKey());
                    break;
                case 2:
                    list2AfterRemoved.add(entry.getKey());
                    break;
                default:
                    common.add(entry.getKey());
            }
        }

        System.out.println(Arrays.toString(list1AfterRemoved.toArray()));
        System.out.println(Arrays.toString(list2AfterRemoved.toArray()));
        System.out.println(Arrays.toString(common.toArray()));
        return result;
    }
}