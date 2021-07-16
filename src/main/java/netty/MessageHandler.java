package netty;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<AbstractCommand> {

    private Path currentPath;

    public MessageHandler() {
        currentPath = Paths.get("server_dir");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new ListResponse(currentPath));
        ctx.writeAndFlush(new PathUpResponse(currentPath.toString()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractCommand command) throws Exception {
        log.debug("received: {}", command.getType());
        switch (command.getType()) {
            case FILE_REQUEST:
                FileRequest fileRequest = (FileRequest) command;
                FileMessage msg = new FileMessage(currentPath.resolve(fileRequest.getName()));
                ctx.writeAndFlush(msg);
                break;
            case FILE_MESSAGE:
                FileMessage message = (FileMessage) command;
                Files.write(currentPath.resolve(message.getName()), message.getData());
                ctx.writeAndFlush(new ListResponse(currentPath));
                break;
            case PATH_UP:
                if (currentPath.getParent() != null) {
                    currentPath = currentPath.getParent();
                }
                ctx.writeAndFlush(new PathUpResponse(currentPath.toString()));
                ctx.writeAndFlush(new ListResponse(currentPath));
                break;
            case LIST_REQUEST:
                ctx.writeAndFlush(new ListResponse(currentPath));
                break;
            case PATH_IN_REQUEST:
                PathInRequest request = (PathInRequest) command;
                Path newPath = currentPath.resolve(request.getDir());
                if (Files.isDirectory(newPath)) {
                    currentPath = newPath;
                    ctx.writeAndFlush(new PathUpResponse(currentPath.toString()));
                    ctx.writeAndFlush(new ListResponse(currentPath));
                }
                break;
        }
    }
}
