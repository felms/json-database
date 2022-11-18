package server;

import java.util.Arrays;

public class Database {

    private final String[] db;

    public Database() {
        this.db = new String[100];
        Arrays.fill(this.db, "");
    }

    public void set(int slot, String data) {

        if (slot > 100 || slot < 1) {
            System.out.println("ERROR");
            return;
        }

        this.db[slot - 1] = data;

        System.out.println("OK");
    }

    public void get(int slot) {

        if (slot > 100 || slot < 1 || this.db[slot - 1].isBlank()) {
            System.out.println("ERROR");
            return;
        }

        System.out.println(this.db[slot - 1]);
    }

    public void delete(int slot) {

        if (slot > 100 || slot < 1) {
            System.out.println("ERROR");
            return;
        }

        this.db[slot - 1] = "";

        System.out.println("OK");
    }
}
