import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String[][] treeStr = {
                { "A", "B" },
                { "A", "C" },
                { "B", "D" },
                { "C", "D" },
        };
        Map<String, DependencyNode> tree = createTree(treeStr);
        String nodeStr = "A";
        List<String> compileSequence = computeCompileSequence(nodeStr, tree);
        List<String> reversed = compileSequence.reversed();
        List<String> result = new ArrayList<>();
        for (String dependency : reversed) {
            if (!result.contains(dependency)) {
                result.add(dependency);
            }
        }
        System.out.println(result);
    }

    private static List<String> computeCompileSequence(String nodeStr,
                                                       Map<String, DependencyNode> tree) {
        DependencyNode root = tree.get(nodeStr);
        if (root == null) {
            throw new IllegalArgumentException("Node is not in the input tree");
        }
        List<String> compileSequence = new ArrayList<>();
        compileSequence.add(nodeStr);
        if (root.getChildren().isEmpty()) {
            return compileSequence;
        }
        for (DependencyNode child : root.getChildren()) {
            compileSequence.addAll(computeCompileSequence(child.getName(), tree));
        }
        return compileSequence;
    }

    private static Map<String, DependencyNode> createTree(String[][] treeStr) {
        Map<String, DependencyNode> tree = new HashMap<>();
        for (String[] strings : treeStr) {
            DependencyNode node = tree.computeIfAbsent(strings[0],
                    k -> new DependencyNode(strings[0]));
            DependencyNode child = tree.computeIfAbsent(strings[1],
                    k -> new DependencyNode(strings[1]));
            node.getChildren().add(child);
        }
        return tree;
    }
}