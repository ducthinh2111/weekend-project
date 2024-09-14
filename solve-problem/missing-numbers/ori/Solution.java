package ori;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File ("solve-problem/missing-numbers/ori/input03.txt");
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            String aList = scanner.nextLine();
            scanner.nextLine();
            String bList = scanner.nextLine();
            long t = new Date().getTime();
            List<Integer> a = Arrays.stream(aList.split(" "))
                    .map(Integer::parseInt).sorted().collect(Collectors.toList());
            List<Integer> b = Arrays.stream(bList.split(" "))
                    .map(Integer::parseInt).sorted().collect(Collectors.toList());
            List<Integer> result = new ArrayList<>();
            collect(b.get(0), a, b, result);
            System.out.println(result.stream().map(Object::toString).collect(Collectors.joining(", ")));
            System.out.println("total time: " + (new Date().getTime() - t));
        }
    }

    private static void collect(Integer num, List<Integer> a, List<Integer> b, List<Integer> result) {
        int sizeInA = a.contains(num) ? a.lastIndexOf(num) - a.indexOf(num) : -1;
        int sizeInB = b.lastIndexOf(num) - b.indexOf(num);
        if (sizeInB > sizeInA) {
            result.add(num);
        }
        if(b.lastIndexOf(num) == b.size() - 1) {
            return;
        }
        List<Integer> remainA = a.subList(a.lastIndexOf(num) + 1, a.size());
        List<Integer> remainB = b.subList(b.lastIndexOf(num) + 1, b.size());
        if (a.isEmpty()) {
            result.addAll(remainB);
            return;
        }
        if (b.isEmpty()) {
            return;
        }
        collect(remainB.get(0), remainA, remainB, result);
    }

}
