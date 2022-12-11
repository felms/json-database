package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {

    @Parameter(names = "-t")
    String requestType;

    @Parameter(names = "-i")
    int cellIndex;

    @Parameter(names = "-m")
    public String valueToSave;

    public static void main(String[] args) {

        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        Client client = new Client();
        client.init(main.requestType,
                main.cellIndex,
                main.valueToSave);
    }
}
