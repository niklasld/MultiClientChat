import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private static ArrayList<Socket> sockets;

    public static void main(String[] args) {
        sockets = new ArrayList<>();
        new ChatServer().runServer();
    }

    private static void runServer() {
        SocketHandler socketHandler = new SocketHandler();

        int port = 1333;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while(true) {
                System.out.println("Venter på at oprette forbindelse til klient...");
                Socket socket = serverSocket.accept();
                System.out.println("Forbindelse til klient er oprettet...");

                sockets.add(socket);

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        System.out.println("Forbindelse er startet...");
                        socketHandler.handleSockets(socket);
                    }
                };
                t.start();

            }
        }

        catch (IOException e){
            e.printStackTrace();
        }


    }

    public static void broadcast(String message) throws IOException {
        DataOutputStream dataOutput;
        for (Socket client: sockets) {
            dataOutput = new DataOutputStream(client.getOutputStream());
            dataOutput.writeUTF(message);
        }
    }
}
