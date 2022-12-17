package server;

import com.google.gson.Gson;
import controller.Request;
import controller.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Database database;
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
                    Request request = new Gson().fromJson(message, Request.class);

                    if ("exit".equals(request.getType())) {
                        Response response = new Response("OK");
                        String responseMessage = new Gson().toJson(response);
                        dataOutputStream.writeUTF(responseMessage);
                        break;
                    }

                    Response response = execCommand(request);
                    String responseMessage = new Gson().toJson(response);
                    dataOutputStream.writeUTF(responseMessage);
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private Response execCommand(Request request) {

        Response response;

        String requestType = request.getType();
        String key = request.getKey();
        String value = request.getValue();

        switch (requestType) {
            case "set" -> {
                this.database.insertRecord(key, value);
                response = new Response("OK");
            }

            case "get" -> {
                String record = database.getRecord(key);
                if (record == null) {
                    response = new Response("ERROR", "", "No such key");
                } else {
                    response = new Response("OK", record, "");
                }
            }

            case "delete" -> {
                if (database.getRecord(key) == null) {
                    response = new Response("ERROR", "", "No such key");
                } else {
                    database.deleteRecord(key);
                    response = new Response("OK");
                }
            }

            default -> throw new IllegalArgumentException("Invalid Request");
        }

        return response;
    }

}
