package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

// cd *
// ls
// cat fileName
// mkdir name
// touch name
public class NioServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer buf;
    private Path root;

    public NioServer() throws IOException {
        root = Paths.get("dir").toAbsolutePath();
        buf = ByteBuffer.allocate(256);
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8189));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (serverSocketChannel.isOpen()) {
            selector.select();
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                System.out.println(key);
                if (key.isAcceptable()) {
                    handleAccept(key);
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioServer();
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        buf.clear();
        StringBuilder s = new StringBuilder();
        int read;
        while (true) {
            read = channel.read(buf);
            if (read == -1) {
                channel.close();
                break;
            }
            if (read == 0) {
                break;
            }
            buf.flip();
            while (buf.hasRemaining()) {
                s.append((char) buf.get());
            }
            buf.clear();
        }
        System.out.println("Received: " + s);
        String command = s.toString().trim();

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {

            }
        };

        if (command.equals("ls")) {
            String files = Files.list(root)
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.joining("\n")) + "\r\n";
            ByteBuffer response = ByteBuffer.wrap(files.getBytes(StandardCharsets.UTF_8));
            channel.write(response);
        } else if (command.startsWith("cd")) {
            command = command.replaceAll("cd +", "").trim();
            if (Files.isDirectory(Paths.get(command)) && Files.exists(Paths.get(command))) {
                root = Paths.get(command);
            } else {
                if (command.equals("..")) {
                    if (root.getParent() == null) {
                        root = root.toAbsolutePath();
                    }
                    root = root.getParent();
                } else if (Files.isDirectory(root.resolve(command))) {
                    root = root.resolve(command);
                } else {
                    ByteBuffer response =
                            ByteBuffer.wrap((command + " is not directory\r\n").getBytes(StandardCharsets.UTF_8));
                    channel.write(response);
                }
            }
        } else if (command.startsWith("cat")) {
            command = command.replaceAll("cat +", "").trim();
            String content = String.join("\n", Files.readAllLines(root.resolve(command))) + "\r\n";
            ByteBuffer response = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));
            channel.write(response);
        } else {
            ByteBuffer response = ByteBuffer.wrap("unknown command\r\n".getBytes(StandardCharsets.UTF_8));
            channel.write(response);
        }
        printPath(channel);
    }

    private void printPath(SocketChannel channel) throws IOException {
        String path = root.toString() + " ";
        ByteBuffer response = ByteBuffer.wrap(path.getBytes(StandardCharsets.UTF_8));
        channel.write(response);
    }

    private void handleAccept(SelectionKey key) throws IOException {
        SocketChannel channel = serverSocketChannel.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        printPath(channel);
    }
}
