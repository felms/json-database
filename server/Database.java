package server;

import java.util.HashMap;
import java.util.Map;

public class Database {

   private final Map<Integer, String> storage;

   public Database() {
       this.storage = new HashMap<>();
   }

   public String getRecord(int index) {
       return this.storage.get(index);
   }

   public void insertRecord(int index, String value) {
      this.storage.put(index, value);
   }

   public void deleteRecord(int index) {
       this.storage.remove(index);
   }

}
