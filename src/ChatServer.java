import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void startServer(TextArea messages) {
        try {
            ServerSocket server = new ServerSocket(12345);
            while (true) {
                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String msg = in.readLine();
                if (msg != null) {
                    Database.messages.add(msg);
                    messages.appendText(msg + "\n");
                }
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}