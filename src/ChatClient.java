import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static Scanner scanner;
    private static DataOutputStream dataOutput;
    private static DataInputStream dataInput;

    public static void main(String[] args) {
        new ChatClient().runClient();
    }

    private static void runClient() {
        scanner = new Scanner(System.in);
        int port = 1333;
        String ip = "localhost";

        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Forbindelse oprettet...");

            dataOutput = new DataOutputStream(socket.getOutputStream());
            dataInput = new DataInputStream(socket.getInputStream());

            scanner = new Scanner(System.in);

            Thread tSend = new Thread() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            System.out.println("Skriv din besked:");
                            dataOutput.writeUTF("Klient: "+scanner.nextLine());
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            tSend.start();

            Thread tRecieve = new Thread() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            System.out.println(dataInput.readUTF());
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            tRecieve.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
