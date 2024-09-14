package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class MissingNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> a = new ArrayList<>();
        while (n-- > 0) {
            int e = scanner.nextInt();
            a.add(e);
        }
        n = scanner.nextInt();
        List<Integer> b = new ArrayList<>();
        while (n-- > 0) {
            int e = scanner.nextInt();
            b.add(e);
        }
        var missingNumbers = new MissingNumbers();
        System.out.println(missingNumbers.diff(a, b));
    }

    public List<Integer> diff(List<Integer> a, List<Integer> b) {
        long start = System.currentTimeMillis();
        Map<Integer, Integer> frequentAs = new HashMap<>();
        for (Integer i : a) {
            frequentAs.merge(i, 1, Integer::sum);
        }
        Map<Integer, Integer> frequentBs = new HashMap<>();
        for (Integer i : b) {
            frequentBs.merge(i, 1, Integer::sum);
        }

        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : frequentBs.entrySet()) {
            Integer frequentA = frequentAs.getOrDefault(entry.getKey(), 0);
            int diff = entry.getValue() - frequentA;
            if (diff > 0) {
                result.add(entry.getKey());
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return result.stream().sorted().collect(Collectors.toList());
    }
}
