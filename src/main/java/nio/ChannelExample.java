package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ChannelExample {

    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("dir/1.txt", "rw");
        FileChannel channel = file.getChannel();
//        ByteBuffer buf = ByteBuffer.allocate(256);
//        channel.read(buf);
//        buf.flip();
//
//        while (buf.hasRemaining()) {
//            System.out.print((char)buf.get());
//        }



        ByteBuffer msg = ByteBuffer.wrap("My message".getBytes(StandardCharsets.UTF_8));
        channel.write(msg);
    }
}
