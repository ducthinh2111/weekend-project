import java.util.ArrayList;
import java.util.List;

public class DependencyNode {
    private final String name;
    private final List<DependencyNode> children;

    public DependencyNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<DependencyNode> getChildren() {
        return children;
    }
}
