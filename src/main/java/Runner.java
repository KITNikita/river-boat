import input.BaseInputReader;
import input.InputReader;
import model.State;
import path.BasePathFinder;
import path.Path;
import path.PathFinder;
import path.PathFormatter;

import java.util.List;

public class Runner {

    public static void main(String[] args) {
        try {
            InputReader reader = new BaseInputReader();
            State inputState = reader.read(args);

            PathFinder finder = new BasePathFinder();
            List<Path> paths = finder.find(inputState);

            System.out.printf("Found %d paths%n", paths.size());
            paths.stream().map(PathFormatter::format).forEach(System.out::println);
            if (paths.isEmpty()) {
                System.out.println();
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}
