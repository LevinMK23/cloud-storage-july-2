package streamApi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FilesExample {

    public static void main(String[] args) throws IOException {

        Path dir = Paths.get("dir");

        List<String> files = Files.list(dir)
                .filter(path -> !Files.isDirectory(path))
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
        System.out.println(files);

        String filesStr = Files.list(dir)
                .filter(path -> !Files.isDirectory(path))
                .map(path -> path.getFileName().toString())
                .collect(Collectors.joining(", "));

        System.out.println(filesStr);
    }

    /**
     * Сделать через стримы
     *
     * @param shopsBusiness - бизнесы магазинов
     * @param vendorsShops - магазины вендора
     *
     * @return бизнесы вендора
     * */
    static Map<Integer, Set<Integer>> getBusinessMap(
            Map<Integer, Set<Integer>> vendorsShops,
            Map<Integer, Integer> shopsBusiness
    ) {
        // TODO: 13.07.2021 ДЗ
        return null;
    }
}
