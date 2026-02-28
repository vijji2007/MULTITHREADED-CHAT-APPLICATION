import java.io.*;
import java.net.*;

// ChatClient: Connects to server
public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1"; // localhost
    private static final int SERVER_PORT = 12345;        // Must be initialized

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {

            // Thread to listen messages from server
            Thread readThread = new Thread(() -> {
                String msgFromServer;
                try {
                    while ((msgFromServer = reader.readLine()) != null) {
                        System.out.println(msgFromServer);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            readThread.start();

            // Main thread: send messages to server
            String msgToServer;
            while ((msgToServer = consoleReader.readLine()) != null) {
                writer.println(msgToServer);
            }

        } catch (IOException e) {
            System.out.println("Unable to connect to server.");
        }
    }
}