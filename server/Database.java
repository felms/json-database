package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();
    private Map<String, String> storage;
    private final File db;

    public Database() {

        this.storage = new HashMap<>();
        this.db = new File( System.getProperty("user.dir") + "/src/server/data/db.json");
        readLock.lock();
        loadFromFile();
        readLock.unlock();

    }

    public String getRecord(String index) {
        return this.storage.get(index);
    }

    public void insertRecord(String index, String value) {
        writeLock.lock();
        this.storage.put(index, value);
        this.saveToFile();
        writeLock.unlock();
    }

    public void deleteRecord(String key) {
        writeLock.lock();
        this.storage.remove(key);
        this.saveToFile();
        writeLock.unlock();
    }

    private void loadFromFile() {
        try  {
            String contents = new String(Files.readAllBytes(Paths.get(this.db.getPath())));
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            this.storage = new Gson().fromJson(contents, mapType);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(this.db)) {
            String jsonData = new Gson().toJson(this.storage);
            writer.write(jsonData);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
