package nio;

import java.nio.ByteBuffer;

public class BufferExample {

    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(5);
        buf.put((byte) 97);
        buf.put((byte) 98);
        buf.put((byte) 99);
        buf.flip();

        while (buf.hasRemaining()) {
            System.out.println(buf.get());
        }
        buf.rewind();
        System.out.println();
        while (buf.hasRemaining()) {
            System.out.println(buf.get());
        }

    }

}
