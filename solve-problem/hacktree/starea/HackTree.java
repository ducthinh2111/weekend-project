import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class HackTree {

    private static int countVerticalPaths(List<Integer> cost,
                                          List<Integer> edgeFrom,
                                          List<Integer> edgeTo,
                                          int k) {
        int count = 0;
        for (Integer c : cost) {
            if (c % k == 0) {
                count++;
            }
        }
        return count + traverse(edgeFrom, edgeTo, cost, k);
    }

    private static int traverse(List<Integer> edgeFrom,
                                List<Integer> edgeTo,
                                List<Integer> costs,
                                int k) {

        Queue<Integer> parents = new LinkedList<>();
        List<Integer> checkedConnections = new ArrayList<>();
        parents.add(1);
        int count = 0;
        Map<Integer, List<Long>> costStorage = new HashMap<>();

        while (!parents.isEmpty()) {

            Integer parent = parents.poll();

            List<Integer> children = new ArrayList<>();
            for (int i = 0; i < costs.size() - 1; i++) {
                if (checkedConnections.contains(i)) continue;

                if (Objects.equals(edgeFrom.get(i), parent)) {
                    children.add(edgeTo.get(i));
                    checkedConnections.add(i);
                }

                if (Objects.equals(edgeTo.get(i), parent)) {
                    children.add(edgeFrom.get(i));
                    checkedConnections.add(i);
                }
            }

            for (Integer child : children) {
                long childCost = costs.get(child - 1).longValue();

                List<Long> childCosts = new ArrayList<>();

                long costFromParent = childCost + costs.get(parent - 1).longValue();
                if (costFromParent % k == 0) {
                    count++;
                }
                childCosts.add(costFromParent);

                if (costStorage.containsKey(parent)) {
                    List<Long> ancestorCosts = costStorage.get(parent);
                    for (Long ancestorCost : ancestorCosts) {
                        long costFromAncestors = childCost + ancestorCost;
                        childCosts.add(costFromAncestors);
                        if (costFromAncestors % k == 0) {
                            count++;
                        }
                    }
                }
                costStorage.put(child, childCosts);
                parents.add(child);
            }
        }

        return count;
    }
}
