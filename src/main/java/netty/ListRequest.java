package netty;

public class ListRequest extends AbstractCommand{

    @Override
    public CommandType getType() {
        return CommandType.LIST_REQUEST;
    }
}
