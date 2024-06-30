import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class HackTree {
    private static int countVerticalPaths(List<Integer> cost,
                                          int edgeNodes,
                                          List<Integer> edgeFrom,
                                          List<Integer> edgeTo,
                                          int k) {

        Map<Integer, List<Integer>> connection = new HashMap<>();
        List<Integer> goneThoughEdgeIndex = new ArrayList<>();
        List<Long> costPath = new ArrayList<>();

        int count = 0;
        for (Integer c : cost) {
            if (c % k == 0) {
                count++;
            }
        }
        return count + traverse(1,
                edgeFrom, edgeTo, edgeNodes - 1, connection, goneThoughEdgeIndex, cost, costPath, 0, k);
    }

    private static int traverse(Integer parent,
                                 List<Integer> edgeFrom,
                                 List<Integer> edgeTo,
                                 int edges,
                                 Map<Integer, List<Integer>> connection,
                                 List<Integer> goneThoughEdgeIndex,
                                 List<Integer> costs,
                                 List<Long> costPath,
                                int deep,
                                int k) {

        for (int i = 0; i < edges; i++) {
            if (goneThoughEdgeIndex.contains(i)) continue;

            if (Objects.equals(edgeFrom.get(i), parent)) {
                if (!connection.containsKey(edgeFrom.get(i))) {
                    connection.put(edgeFrom.get(i), new ArrayList<>());
                }
                connection.get(edgeFrom.get(i)).add(edgeTo.get(i));
                goneThoughEdgeIndex.add(i);
            }

            if (Objects.equals(edgeTo.get(i), parent)) {
                if (!connection.containsKey(edgeTo.get(i))) {
                    connection.put(edgeTo.get(i), new ArrayList<>());
                }
                connection.get(edgeTo.get(i)).add(edgeFrom.get(i));
                goneThoughEdgeIndex.add(i);
            }
        }

        List<Integer> children = connection.get(parent);
        if (Objects.isNull(children)) {
            int count = 0;
            for (Long calculatedCost : costPath) {
                if (calculatedCost % k == 0) {
                    count++;
                }
            }
            return count;
        }

        int count = 0;
        for (Integer child : connection.get(parent)) {
            long cost = costs.get(child - 1).longValue();
            List<Long> clonedCostPath = new ArrayList<>(costPath);
            clonedCostPath.add(cost + costs.get(parent - 1).longValue());
            for (int i = costPath.size() - deep; i < costPath.size(); i++) {
                clonedCostPath.add(cost + costPath.get(i));
            }
            count += traverse(child, edgeFrom, edgeTo, edges, connection, goneThoughEdgeIndex, costs, clonedCostPath, deep + 1, k);
        }

        return count;
    }
}
