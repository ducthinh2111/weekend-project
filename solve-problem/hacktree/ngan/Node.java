package ngan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node {
    private final int num;
    private final int cost;
    private final List<Node> children = new ArrayList<>();

    public Node(int num, int cost) {
        this.num = num;
        this.cost = cost;
    }

    public int getNum() {
        return this.num;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public long getCost() {
        return cost;
    }

    public List<List<Node>> possiblePaths() {
        List<List<Node>> result = new ArrayList<>();
        List<Node> path = new ArrayList<>();
        path.add(this);
        result.add(path);
        result.addAll(possiblePaths(path, this));
        return result.stream().distinct().collect(Collectors.toList());
    }

    private List<List<Node>> possiblePaths(List<Node> path, Node node) {
        List<List<Node>> result = new ArrayList<>();
        if (node.children.isEmpty()){
            return result;
        }
        for (Node child : node.children) {
            result.add(List.of(child));
            List<Node> extraPath = new ArrayList<>(path);
            extraPath.add(child);
            result.add(extraPath);
            result.addAll(possiblePaths(extraPath, child));
            result.addAll(possiblePaths(new ArrayList<>(List.of(child)), child));
        }
        return result;
    }
}
