package client;

import com.google.gson.Gson;
import controller.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 41220;

    private final Request request;
    public Client(String type, String key, String value) {

        this.request = new Request(type, key, value);
    }

    public void init() {

        System.out.println("Client started!");
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())
        ) {

            String message = new Gson().toJson(request);
            dataOutputStream.writeUTF(message);
            System.out.println("Sent: " + message);

            String response = dataInputStream.readUTF();
            System.out.println("Received: " + response);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
