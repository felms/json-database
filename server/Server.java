package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 41220;

    public void init() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started!");

            try (
                    Socket socket = serverSocket.accept();
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())
            ) {

                String message = dataInputStream.readUTF();
                System.out.printf("Received: %s\n", message);
                int recordNumber = Integer.parseInt(message.replaceAll("[\\D]", ""));
                String response = String.format("A record # %d was sent!", recordNumber);

                dataOutputStream.writeUTF(response);
                System.out.println("Sent: " + response);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
