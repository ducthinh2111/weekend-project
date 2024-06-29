package ngan;

import java.io.File;
import java.util.*;

public class SummationTreePath {
    public static void main(String[] args) throws Exception {
        File f = new File("solve-problem/hacktree/ngan/input.txt");
        Scanner scanner = new Scanner(f);
        List<Integer> cost = new ArrayList<>();
        List<Map.Entry<Integer, Integer>> connections = new ArrayList<>();
        Integer k = null;
        int index = 0;
        boolean firstMatchPair = true;
        while (scanner.hasNext()) {
            if (index == 0) {
                scanner.nextLine();
            }
            String str = scanner.nextLine();
            String[] chars = str.split(" ");
            if (chars.length == 1) {
                if (scanner.hasNext()) {
                    cost.add(Integer.parseInt(str));
                } else {
                    k = Integer.parseInt(str);
                }
            } else {
                if (firstMatchPair) {
                    firstMatchPair = false;
                    continue;
                }
                connections.addAll(Map.of(Integer.parseInt(chars[0]), Integer.parseInt(chars[1])).entrySet());
            }
            index ++;
        }
        System.out.print("Cost:= ");
        cost.forEach(i -> System.out.print(i + ", "));
        System.out.println();
        System.out.print("Connections:= ");
        connections.forEach(i -> System.out.print("[" + i.getKey() + "," + i.getValue() +"]" + ", "));
        System.out.println();
        System.out.println("k:= " + k);
        Node parent = new Node(1, cost.get(0));
        buildTree(parent, connections, cost);
        List<List<Node>> result = parent.possiblePaths();
        count(result, k);
    }

    private static void count(List<List<Node>> nodes, int k) {
        int count = 0;
        for (List<Node> item : nodes) {
            long sum = item.stream().map(Node::getCost).reduce(0L, Long::sum);
            if (sum % k == 0) {
                count++;
            }
        }
        System.out.println("Count: " + count);
    }


    private static void buildTree(Node parent,
                                  List<Map.Entry<Integer, Integer>> connections,
                                  List<Integer> cost) {
        List<Node> children = new ArrayList<>();
        List<Map.Entry<Integer, Integer>> remainConnections = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : connections){
            if (e.getKey() == parent.getNum()) {
                Node node = new Node(e.getValue(), cost.get(e.getValue() - 1));
                children.add(node);
            } else if (e.getValue() == parent.getNum()) {
                Node node = new Node(e.getKey(), cost.get(e.getKey() - 1));
                children.add(node);
            } else {
                remainConnections.add(e);
            }
        }
        for (Node child : children) {
            buildTree(child, remainConnections, cost);
            parent.addChild(child);
        }
    }
}
