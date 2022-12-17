package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {

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

        Client client = new Client(main.requestType, main.key, main.value);
        client.init();
    }
}
