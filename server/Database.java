package server;

import java.util.HashMap;
import java.util.Map;

public class Database {

   private final Map<String, String> storage;

   public Database() {
       this.storage = new HashMap<>();
   }

   public String getRecord(String index) {
       return this.storage.get(index);
   }

   public void insertRecord(String index, String value) {
      this.storage.put(index, value);
   }

   public void deleteRecord(String key) {
       this.storage.remove(key);
   }

}
