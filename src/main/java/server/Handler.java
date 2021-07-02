package server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Handler implements Runnable {


    private String dir = "dir";
    private final byte[] buffer;
    private final Socket socket;
    private final InputStream is;
    private final OutputStream os;

    public Handler(Socket socket) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        buffer = new byte[1024];
    }


    @Override
    public void run() {
        try {
            String welcome = "Welcome on server, for show command list input -help\n\r";
            os.write(welcome.getBytes(StandardCharsets.UTF_8));
            os.flush();
            StringBuilder builder = new StringBuilder();
            while (true) {
                int read = is.read(buffer);
                for (int i = 0; i < read; i++) {
                    if (buffer[i] != '\r') {
                        builder.append((char) buffer[i]);
                    } else {
                        String message = builder.toString().trim();
                        System.out.println(message);
                        System.out.println(Arrays.toString(message.getBytes(StandardCharsets.UTF_8)));
                        if (message.equals("ls")) {
                            String response = Arrays.toString(new File(dir).list());
                            os.write(response.getBytes(StandardCharsets.UTF_8));
                            os.flush();
                        } else if (message.startsWith("cat")) {
                            String filename = message.replaceAll("cat ", "");
                            byte[] bytes = Files.readAllBytes(Paths.get(dir, filename));
                            os.write(bytes);
                            os.flush();
                        } else {
                            message = "Hello form server: " + message + "\n\r";
                            os.write(message.getBytes(StandardCharsets.UTF_8));
                            os.flush();
                        }
                        builder = new StringBuilder();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Exception while read");
        }
    }
}
