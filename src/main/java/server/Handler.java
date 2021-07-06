package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Handler implements Runnable {


    private final byte[] buffer;
    private final Socket socket;
    private final DataInputStream is;
    private final DataOutputStream os;
    private String dir = "server_dir";

    public Handler(Socket socket) throws IOException {
        this.socket = socket;
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        buffer = new byte[1024];
    }


    @Override
    public void run() {
        try {
            while (true) {
                String fileName = is.readUTF();
                System.out.println("File: " + fileName);
                long size = is.readLong();
                System.out.println("Size: " + size);
                try (FileOutputStream fos = new FileOutputStream(dir + "/" + fileName)) {
                    for (int i = 0; i < (size + 1023) / 1024; i++) {
                        int read = is.read(buffer);
                        fos.write(buffer, 0, read);
                    }
                }
                os.writeUTF("File: " + fileName + " successfully received!");
                os.flush();
            }
        } catch (Exception e) {
            System.err.println("Exception while read");
        }
    }
}
