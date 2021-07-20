package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class FileMessage extends AbstractCommand {

    private final String name;
    private final long size;
    private final byte[] data;

    public FileMessage(Path path) throws IOException {
        name = path.getFileName().toString();
        size = Files.size(path);
        data = Files.readAllBytes(path);
    }

    @Override
    public CommandType getType() {
        return CommandType.FILE_MESSAGE;
    }
}
