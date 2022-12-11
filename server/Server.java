package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Database database;
    private static final int MIN_ID = 0;
    private static final int MAX_ID = 1000;

    private static final int PORT = 41220;

    public void init() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            this.database = new Database();
            System.out.println("Server started!");

            while (true) {

                try (
                        Socket socket = serverSocket.accept();
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())
                ) {

                    String message = dataInputStream.readUTF();
                    String[] arguments = message.split("\\s+");
                    String requestType = arguments[0];

                    if ("exit".equals(requestType)) {
                        dataOutputStream.writeUTF("OK");
                        break;
                    }

                    int cellIndex = Integer.parseInt(arguments[1]);
                    String valueToSave = message.split("\\d+")[1].trim();

                    String response = execCommand(requestType, cellIndex, valueToSave);

                    dataOutputStream.writeUTF(response);
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String execCommand(String command, int index, String value) {

        if (index < MIN_ID || index > MAX_ID) {
            return "ERROR";
        }

        return switch (command) {
            case "set" -> {
                this.database.insertRecord(index, value);
                yield "OK";
            }

            case "get" -> {
                String response = this.database.getRecord(index);
                yield response == null ? "ERROR" : response;
            }

            case "delete" -> {
                this.database.deleteRecord(index);
                yield "OK";
            }

            default -> "ERROR";
        };
    }

}
