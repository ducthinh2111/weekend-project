import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HackTree {
    private static int countVerticalPaths(List<Integer> cost,
                                          int edgeNodes,
                                          List<Integer> edgeFrom,
                                          List<Integer> edgeTo,
                                          int k) {

        Map<Integer, List<Integer>> connection = new HashMap<>();

        // make the root
        for (int i = 0; i < edgeNodes - 1; i++) {
            if (Objects.equals(edgeFrom.get(i), 1)) {
                if (!connection.containsKey(edgeFrom.get(i))) {
                    connection.put(edgeFrom.get(i), new ArrayList<>());
                }
                connection.get(edgeFrom.get(i)).add(edgeTo.get(i));
            }
            if (Objects.equals(edgeTo.get(i), 1)) {
                if (!connection.containsKey(edgeTo.get(i))) {
                    connection.put(edgeTo.get(i), new ArrayList<>());
                }
                connection.get(edgeTo.get(i)).add(edgeFrom.get(i));
            }
        }

        // from the root
        for (int i = 0; i < edgeNodes - 1; i++) {
            if (connection.get(1).contains(edgeFrom.get(i))) {
                if (!connection.containsKey(edgeFrom.get(i))) {
                    connection.put(edgeFrom.get(i), new ArrayList<>());
                }
                connection.get(edgeFrom.get(i)).add(edgeTo.get(i));
            }

            if (connection.get(1).contains(edgeTo.get(i))) {
                if (!connection.containsKey(edgeTo.get(i))) {
                    connection.put(edgeTo.get(i), new ArrayList<>());
                }
                connection.get(edgeTo.get(i)).add(edgeFrom.get(i));
            }
        }

        // Calculate cost
        int result = 0;
        for (int i = 2; i <= edgeNodes; i++) {
            if (cost.get(i - 1).longValue() % k == 0) {
                result++;
            }
        }

        List<Long> previousCost = new ArrayList<>();
        previousCost.add(cost.getFirst().longValue());
        for (Integer child : connection.get(1)) {
            long childCost = cost.get(child - 1).longValue();
            List<Long> newPreviousCost = previousCost.stream().map(pre -> pre + childCost).collect(Collectors.toList());
            newPreviousCost.add(childCost);
            result += calculateCost(connection, connection.get(child), cost, newPreviousCost, k);
        }
        return result;
    }

    private static int calculateCost(Map<Integer, List<Integer>> connection,
                                     List<Integer> children,
                                     List<Integer> cost,
                                     List<Long> previousCost,
                                     int k) {

        if (children != null) {
            for (Integer child : children) {
                long childCost = cost.get(child - 1).longValue();
                List<Long> newPreviousCost = previousCost.stream().map(pre -> pre + childCost).collect(Collectors.toList());
                newPreviousCost.add(childCost);
                return calculateCost(connection, connection.get(child), cost, newPreviousCost, k);
            }
        }

        int count = 0;
        for (Long costPath : previousCost) {
            if (costPath % k == 0) {
                count++;
            }
        }
        return count;
    }
}
