package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class app extends Application {

    MainController mainController;
    static Scene mainScene;

    @Override
    public void start(Stage stage) throws Exception {
        mainController = new MainController(); // Ahora estamos usando MainController
        mainScene = new Scene(mainController.getView(), 800, 600);
        stage.setTitle("Javier Pérez Escribano");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}