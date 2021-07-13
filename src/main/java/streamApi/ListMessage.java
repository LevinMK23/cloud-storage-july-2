package streamApi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ListMessage {

    private final List<String> names;

    public ListMessage(Path path) throws IOException {
        names = Files.list(path)
                .map(p -> p.getFileName().toString())
                .collect(Collectors.toList());
    }

    public List<String> getNames() {
        return names;
    }
}
