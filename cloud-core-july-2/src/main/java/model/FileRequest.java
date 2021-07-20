package model;

public class FileRequest extends AbstractCommand {

    private final String name;

    public FileRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public CommandType getType() {
        return CommandType.FILE_REQUEST;
    }
}
