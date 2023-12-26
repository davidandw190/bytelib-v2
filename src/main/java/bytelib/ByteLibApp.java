package bytelib;

import bytelib.scenes.MenuScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ByteLibApp extends Application {
    private static final String DEFAULT_FILE_PATH = "lms.dat";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Library library = loadLibrary();

        primaryStage.setTitle("ByteLib - by Andrei-David Nan");
        primaryStage.setOnCloseRequest(event -> saveLibrary(library, DEFAULT_FILE_PATH));

        showMenuScene(primaryStage, library);
    }

    private Library loadLibrary() {
        Library library = null;

        try {
            library = new Library();
            library.loadData(ByteLibApp.DEFAULT_FILE_PATH, "objectstream");
            System.out.println(" [*] Library loaded successfully from " + ByteLibApp.DEFAULT_FILE_PATH);
        } catch (Exception e) {
            System.out.println("Error loading library from " + ByteLibApp.DEFAULT_FILE_PATH + ": " + e.getMessage());
        }

        return library;
    }

    private Library initNewLibraryWithMockData() {
        return null;
    }

    private void saveLibrary(Library library, String filePath) {
        try {
            library.saveData(filePath, "objectstream");
        } catch (Exception e) {
            System.out.println("Error saving library to " + filePath + ": " + e.getMessage());
        }
    }

    private void showMenuScene(Stage primaryStage, Library library) {
        MenuScene menuScene = new MenuScene(primaryStage, library);
        primaryStage.setScene(new Scene(menuScene.getRoot(), 500, 400));
        primaryStage.show();
    }
}
