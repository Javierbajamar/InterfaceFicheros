package ui;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RandomAccessController {

    @FXML
    private TextField codigoProductoField;
    @FXML
    private TextField nombreProductoField;
    @FXML
    private TextField unidadesField;
    @FXML
    private TextField codigoFamiliaField;
    @FXML
    private TextArea resultArea;

    private GestorFicheroAleatorio gestorFichero;

    public RandomAccessController() {
        // Inicializa el gestor de fichero con la ruta del fichero
        gestorFichero = new GestorFicheroAleatorio("ruta/a/tu/fichero.dat");
    }

    @FXML
    private void insertarProducto() {
        try {
            int codigo = Integer.parseInt(codigoProductoField.getText());
            String nombre = nombreProductoField.getText();
            int unidades = Integer.parseInt(unidadesField.getText());
            String codigoFamilia = codigoFamiliaField.getText();

            Producto producto = new Producto(codigo, nombre, unidades, codigoFamilia);
            gestorFichero.insertarProducto(producto);
            resultArea.setText("Producto insertado con éxito.");
        } catch (NumberFormatException | IOException e) {
            resultArea.setText("Error al insertar producto: " + e.getMessage());
        }
    }

    @FXML
    private void visualizarProductos() {
        try {
            String contenido = gestorFichero.visualizarFichero();
            resultArea.setText(contenido);
        } catch (IOException e) {
            resultArea.setText("Error al visualizar productos: " + e.getMessage());
        }
    }

    @FXML
    private void visualizarProductoEspecifico() {
        try {
            int codigo = Integer.parseInt(codigoProductoField.getText());
            String productoInfo = gestorFichero.visualizarProducto(codigo);
            resultArea.setText(productoInfo);
        } catch (NumberFormatException | IOException e) {
            resultArea.setText("Error al visualizar producto: " + e.getMessage());
        }
    }

    @FXML
    private void modificarUnidades() {
        try {
            int codigo = Integer.parseInt(codigoProductoField.getText());
            int nuevasUnidades = Integer.parseInt(unidadesField.getText());
            gestorFichero.modificarUnidades(codigo, nuevasUnidades);
            resultArea.setText("Unidades del producto actualizadas con éxito.");
        } catch (NumberFormatException | IOException e) {
            resultArea.setText("Error al modificar unidades: " + e.getMessage());
        }
    }
}
