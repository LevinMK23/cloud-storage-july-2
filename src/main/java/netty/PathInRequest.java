package netty;

public class PathInRequest extends AbstractCommand {

    private final String dir;

    public PathInRequest(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return dir;
    }

    @Override
    public CommandType getType() {
        return CommandType.PATH_IN_REQUEST;
    }
}
