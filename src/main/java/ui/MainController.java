package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private File archivoActual;

    private RandomAccessController randomAccessController;

    @FXML
    private CheckBox carpetaCheck;

    @FXML
    private TextField carpetaficheroField;

    @FXML
    private Button   contenidoButton;

    @FXML
    private Button crearButton;

    @FXML
    private Button eliminarButton;

    @FXML
    private Button fichcarpButton;

    @FXML
    private CheckBox ficheroCheck;

    @FXML
    private Button modificarButton;

    @FXML
    private Button moverButton;

    @FXML
    private TextField rutaLabel;

    @FXML
    private BorderPane view;

    @FXML
    private TextArea contentArea;

    @FXML
    private TreeView<String> ficheroTree;

    @FXML
    private Button onFicherosAleatorios;

    @FXML
    private void handleFicherosAleatoriosAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RandomAccessView.fxml"));
            Parent randomAccessView = loader.load();
            view.setCenter(randomAccessView); // 'view' es tu BorderPane
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }


    public MainController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    void onCarpetaCheck(ActionEvent event) {

        // Deshabilitar el campo de texto
        carpetaficheroField.setDisable(!carpetaCheck.isSelected()); // Habilitar el campo de texto
    }


    @FXML
    void onContentFichero(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar un archivo para mostrar su contenido");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            archivoActual = selectedFile;
            rutaLabel.setText(selectedFile.getAbsolutePath()); // Actualizar la ruta en el TextField

            try {
                String contenido = new String(Files.readAllBytes(selectedFile.toPath()));
                contentArea.setText(contenido);
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo leer el contenido del archivo.");
            }
        }
    }


    @FXML
    void onCreate(ActionEvent event) throws IOException {
        String nombreCarpetaFichero = carpetaficheroField.getText().trim();
        // Cambia la ubicación de destino al escritorio
        String directorioEscritorio = System.getProperty("user.home") + File.separator + "Desktop";

        if (carpetaCheck.isSelected()) {
            File carpeta = new File(directorioEscritorio, nombreCarpetaFichero);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
        }

        if (ficheroCheck.isSelected()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar ubicación del archivo");

            // Establece la ubicación inicial en el escritorio
            fileChooser.setInitialDirectory(new File(directorioEscritorio));

            fileChooser.setInitialFileName(nombreCarpetaFichero);
            File selectedFile = fileChooser.showSaveDialog(null);

            if (selectedFile != null) {
                try {
                    selectedFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void onDelete(ActionEvent event) {
        String nombreCarpetaFichero = carpetaficheroField.getText().trim();
        String directorioUsuario = System.getProperty("user.home");

        if (carpetaCheck.isSelected()) {
            File carpeta = new File(directorioUsuario, nombreCarpetaFichero);
            if (carpeta.exists() && carpeta.isDirectory()) {
                borrarDirectorio(carpeta);
            } else {
                mostrarAlerta("Error", "La carpeta no existe o no es válida.");
            }
        }

        if (ficheroCheck.isSelected()) {
            File archivo = new File(directorioUsuario, nombreCarpetaFichero);
            if (archivo.exists() && archivo.isFile()) {
                if (archivo.delete()) {
                    mostrarAlerta("Éxito", "Archivo eliminado correctamente.");
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar el archivo.");
                }
            } else {
                mostrarAlerta("Error", "El archivo no existe o no es válido.");
            }
        }
    }

    private void borrarDirectorio(File directorio) {
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    borrarDirectorio(archivo);
                } else {
                    archivo.delete();
                }
            }
        }
        directorio.delete();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    void onEditFichero() {
        if (archivoActual != null) {
            try {
                Files.write(archivoActual.toPath(), contentArea.getText().getBytes(), StandardOpenOption.CREATE);
                mostrarAlerta("Éxito", "Cambios guardados correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo guardar los cambios.");
            }
        } else {
            mostrarAlerta("Error", "No hay un archivo abierto para editar.");
        }
    }


    @FXML
    void onFicheroCheck(ActionEvent event) {

      /*  if (ficheroCheck.isSelected()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar ubicación del archivo");
            File selectedFile = fileChooser.showSaveDialog(null);

            if (selectedFile != null) {
                // Aquí puedes realizar acciones adicionales con el archivo seleccionado
                System.out.println("Ubicación del archivo seleccionada: " + selectedFile.getAbsolutePath());
            }
        } else {
            // Aquí puedes implementar lógica adicional si es necesario
        }*/
    }

    @FXML
    void onMove(ActionEvent event) {
        String nombreCarpetaFichero = carpetaficheroField.getText().trim();
        String directorioUsuario = System.getProperty("user.home");

        if (carpetaCheck.isSelected()) {
            File carpetaOrigen = new File(directorioUsuario, nombreCarpetaFichero);
            if (carpetaOrigen.exists() && carpetaOrigen.isDirectory()) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Seleccionar nueva ubicación para la carpeta");
                File carpetaDestino = directoryChooser.showDialog(null);

                if (carpetaDestino != null) {
                    moverDirectorio(carpetaOrigen, carpetaDestino);
                }
            } else {
                mostrarAlerta("Error", "La carpeta no existe o no es válida.");
            }
        }

        if (ficheroCheck.isSelected()) {
            File archivoOrigen = new File(directorioUsuario, nombreCarpetaFichero);
            if (archivoOrigen.exists() && archivoOrigen.isFile()) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Seleccionar nueva ubicación para el archivo");
                File archivoDestino = fileChooser.showSaveDialog(null);

                if (archivoDestino != null) {
                    moverArchivo(archivoOrigen, archivoDestino);
                }
            } else {
                mostrarAlerta("Error", "El archivo no existe o no es válido.");
            }
        }
    }


    private void moverDirectorio(File directorioOrigen, File directorioDestino) {
        try {
            if (directorioOrigen.renameTo(new File(directorioDestino, directorioOrigen.getName()))) {
                mostrarAlerta("Éxito", "Carpeta movida correctamente.");
            } else {
                mostrarAlerta("Error", "No se pudo mover la carpeta.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al mover la carpeta.");
        }
    }

    private void moverArchivo(File archivoOrigen, File archivoDestino) {
        try {
            if (archivoOrigen.renameTo(archivoDestino)) {
                mostrarAlerta("Éxito", "Archivo movido correctamente.");
            } else {
                mostrarAlerta("Error", "No se pudo mover el archivo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al mover el archivo.");
        }
    }

    @FXML
    void onFicheroCarpeta() {
        visualizarFicherosYCarpetas();  // Llama a la función para actualizar la visualización
    }

    private void visualizarFicherosYCarpetas() {
        String directorioUsuario = System.getProperty("user.home");
        File rootDirectory = new File(directorioUsuario);

        TreeItem<String> rootItem = createTreeItem(rootDirectory);
        ficheroTree.setRoot(rootItem);
        ficheroTree.setShowRoot(true);
    }

    private TreeItem<String> createTreeItem(File file) {
        TreeItem<String> item = new TreeItem<>(file.getName());

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    item.getChildren().add(createTreeItem(subFile));
                }
            }
        }

        return item;
    }


    public BorderPane getView() {
        return view;
    }

}
