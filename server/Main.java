package server;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Database database = new Database();

        String command = scanner.nextLine();

        while (!"exit".equals(command)) {

            if (command.matches("get.+")) {
                int slot = Integer.parseInt(command.split("\\s+")[1]);
                database.get(slot);
            } else if (command.matches("set.+")) {
               int slot = Integer.parseInt(command.split("\\s+")[1]);
               String data = command.replaceAll(".*\\d", "").trim();
               database.set(slot, data);
            } else if (command.matches("delete.+")) {
                int slot = Integer.parseInt(command.split("\\s+")[1]);
                database.delete(slot);
            }

            command = scanner.nextLine();
        }
    }
}
