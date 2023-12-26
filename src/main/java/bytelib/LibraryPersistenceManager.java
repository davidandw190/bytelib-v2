package bytelib;

import java.io.*;

public class LibraryPersistenceManager implements PersistenceManager<Library>{
    @Override
    public void save(Library library, String filePath) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectOutputStream.writeObject(library);
            System.out.println("[*] Library saved successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving library to " + filePath + ": " + e.getMessage());
        }
    }

    @Override
    public Library load(String filePath) {
        Library library = null;

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            library = (Library) objectInputStream.readObject();
            System.out.println("[*] Library loaded successfully from " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading library from " + filePath + ": " + e.getMessage());
        }

        return library;
    }
}
