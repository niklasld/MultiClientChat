import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketHandler {
    public void handleSockets(Socket socket) {
        while (true) {
            DataInputStream dataInput = null;
            try {
                dataInput = new DataInputStream(socket.getInputStream());

                String message = dataInput.readUTF();
                ChatServer.broadcast(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
