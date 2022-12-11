package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 41220;

    public void init(String requestType, int cellIndex, String valueToSave) {

        System.out.println("Client started!");
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())
        ) {

            String message = String.format("%s %d %s", requestType, cellIndex, valueToSave);
            dataOutputStream.writeUTF(message);
            System.out.println("Sent: " + message);

            String response = dataInputStream.readUTF();
            System.out.println("Received: " + response);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
