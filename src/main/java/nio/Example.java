package nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class Example {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("dir", "1.JPG");
        Path txt = Paths.get("dir", "1.txt");

        List<String> strings = Files.readAllLines(txt);
        System.out.println(strings);

        Path res = Paths.get("dir", "res.txt");
        Files.write(
                res,
                Collections.singletonList("Hello world!"),
                StandardOpenOption.APPEND
        );

        Path img = Paths.get("dir", "res.JPG");
        //Files.copy(path, img);

        Files.walk(Paths.get("./"))
                .filter(p -> !Files.isDirectory(p))
                .forEach(System.out::println);



    }
}
