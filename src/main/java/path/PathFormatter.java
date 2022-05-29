package path;

import java.util.Deque;

public final class PathFormatter {

    public static String format(Path path) {
        StringBuilder builder = new StringBuilder();

        Deque<Path.Element> elements = path.getElements();
        for (Path.Element element : elements) {
            if (element.getAction() == null) {
                continue;
            }
            builder.append( element );
            builder.append( ", -->" );
            builder.append( element.applyActionAndGetState() );
            builder.append( System.lineSeparator() );
        }

        return builder.toString();
    }
}
