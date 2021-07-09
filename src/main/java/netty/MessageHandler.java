package netty;

import java.io.FileOutputStream;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<AbstractCommand> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractCommand command) throws Exception {
        log.debug("received: {}", command);
        switch (command.getType()) {
            case FILE_MESSAGE:
                FileMessage message = (FileMessage) command;
                try (FileOutputStream fos = new FileOutputStream("server_dir/" + message.getName())) {
                    fos.write(message.getData());
                }
                break;
        }
    }
}
