import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public  class DependencyChecker {
    public static void main(String[] args) throws Exception{
        File f = new File("D:/Learn/out-learning/weekend-project/solve-problem/coding-interview/out/production/coding-interview/dependency-checker-input.txt");
        try (Scanner scanner = new Scanner(f)){
            if (scanner.hasNext()) {
                String name = scanner.nextLine();
                int size = Integer.parseInt(scanner.nextLine());
                Map<String, List<String>> map = new HashMap<>();
                int i = 0;
                while (i <  size) {
                    String[] line = scanner.nextLine().split(" ");
                    List<String> l = map.getOrDefault(line[0], new ArrayList<>());
                    l.add(line[1]);
                    map.put(line[0], l);
                    i++;
                }
                List<String> l = map.get(name);
                Set<String> r = new LinkedHashSet<>();
                r.add(name);
                for (String s : l) {
                    r.addAll(map.getOrDefault(s, List.of(s)));
                }
                List<String> a = new ArrayList<>(r);
                Collections.reverse(a);
                System.out.println("Result: "+ String.join(" ", a));
            }
        }
    }
}
