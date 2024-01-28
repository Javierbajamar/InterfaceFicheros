package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private MainController mainController;

    private BorderPane view;

    public Controller(MainController mainController) {
        this.mainController = mainController;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            loader.setController(mainController);  // Establecer el controlador principal
            this.view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Puedes realizar inicializaciones aquí si es necesario
    }

    public BorderPane getView() {
        return view;
    }
}