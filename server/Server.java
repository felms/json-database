package server;

import com.google.gson.Gson;
import controller.Request;
import controller.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    private Database database;
    private static final int PORT = 41220;

    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            this.database = new Database();
            System.out.println("Server started!");
            int poolSize = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(poolSize);

            while (!executor.isShutdown()) {

                Socket socket = serverSocket.accept();
                var future = executor.submit(() -> new ClientHandler(socket).call());

                if (future.get()) {
                    executor.shutdown();
                    executor.awaitTermination(100, TimeUnit.MILLISECONDS);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Callable<Boolean>{

        Socket socket;
        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public Boolean call() {

            boolean isFinished = false;

            try (DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream());
                 DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream())) {
                String message = dataInputStream.readUTF();
                Request request = new Gson().fromJson(message, Request.class);

                if ("exit".equals(request.getType())) {
                    Response response = new Response("OK");
                    String responseMessage = new Gson().toJson(response);
                    dataOutputStream.writeUTF(responseMessage);
                    isFinished = true;
                } else {
                    Response response = execCommand(request);
                    String responseMessage = new Gson().toJson(response);
                    dataOutputStream.writeUTF(responseMessage);
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            return isFinished;
        }
        private Response execCommand(Request request) {

            Response response;

            String requestType = request.getType();
            String key = request.getKey();
            String value = request.getValue();

            switch (requestType) {
                case "set" -> {
                    database.insertRecord(key, value);
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

}
