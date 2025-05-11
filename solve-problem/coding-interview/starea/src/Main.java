import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nodeStr = scanner.nextLine();
        int nodeInfoNumber = scanner.nextInt();
        scanner.nextLine();
        String[][] treeStr = new String[nodeInfoNumber][2];
        for (int i = 0; i < nodeInfoNumber; i++) {
            String nodeInfoStr = scanner.nextLine();
            String[] nodeInfo = nodeInfoStr.split(" ");
            treeStr[i][0] = nodeInfo[0];
            treeStr[i][1] = nodeInfo[1];
        }
        scanner.close();
        Map<String, DependencyNode> tree = createTree(treeStr);
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