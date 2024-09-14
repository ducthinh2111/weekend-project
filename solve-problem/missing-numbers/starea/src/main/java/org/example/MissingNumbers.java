package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissingNumbers {

    public List<Integer> diff(List<Integer> a, List<Integer> b) {
        Map<Integer, Integer> frequentA = new HashMap<>();
        for (Integer i : a) {
            frequentA.merge(i, 1, Integer::sum);
        }
        Map<Integer, Integer> frequentB = new HashMap<>();
        for (Integer i : b) {
            frequentB.merge(i, 1, Integer::sum);
        }

        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : frequentB.entrySet()) {
            Integer mutualFrequent = frequentA.get(entry.getKey());
            if (mutualFrequent == null) {
                result.add(entry.getKey());
            } else {
                int diff = entry.getValue() - mutualFrequent;
                if (diff > 0) {
                    result.add(entry.getKey());
                }
            }
        }

        return result.stream().sorted().collect(Collectors.toList());
    }
}
