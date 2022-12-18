package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import controller.Request;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    @Parameter(names = "-in")
    String fileName;

    @Parameter(names = "-t")
    String requestType;

    @Parameter(names = "-k")
    String key;

    @Parameter(names = "-v")
    public String value;

    public static void main(String[] args) {

        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        Client client;

        if (!(main.fileName == null)) {
            try {
                String path = System.getProperty("user.dir") + "/src/client/data/";
                String contents = new String(Files.readAllBytes(Paths.get( path + main.fileName)));
                Request request = new Gson().fromJson(contents, Request.class);
                client = new Client(request);
                client.run();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            client = new Client(main.requestType, main.key, main.value);
            client.run();
        }

    }
}
