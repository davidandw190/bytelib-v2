package bytelib;

import bytelib.persistence.DBConnector;
import bytelib.scenes.MainMenuScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class ByteLibApp extends Application {
    private static final String DEFAULT_FILE_PATH = "lms.dat";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Connection dbConnection = DBConnector.getConnection();
        Library library = loadLibrary(dbConnection);

        primaryStage.setTitle("ByteLib - by Andrei-David Nan");
//        primaryStage.setOnCloseRequest(event -> saveLibrary(library, DEFAULT_FILE_PATH));

        showMenuScene(primaryStage, library);
    }

    private Library loadLibrary(Connection dbConnection) {
        Library library = null;

        try {
            library = new Library(dbConnection);
            System.out.println(" [*] Library loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading library");
        }

        return library;
    }


    private void showMenuScene(Stage primaryStage, Library library) {
        MainMenuScene mainMenuScene = new MainMenuScene(primaryStage, library);
        primaryStage.setScene(new Scene(mainMenuScene.getRoot(), 500, 400));
        primaryStage.show();
    }
}
