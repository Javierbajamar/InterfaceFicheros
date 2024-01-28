package ui;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class GestorFicheroAleatorio {


    private final String filePath;
    private static final int TAMANO_REGISTRO = 30; // Ajusta según la estructura de tus datos

    public GestorFicheroAleatorio(String filePath) {
        this.filePath = System.getProperty("user.home") + File.separator + "miArchivo.dat";
        inicializarArchivo();
    }

    private void inicializarArchivo() {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            if (file.length() == 0) {
                // Configuración inicial del archivo, si es necesario
                // Por ejemplo, escribir una cabecera o datos iniciales
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void insertarProducto(Producto producto) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            file.seek(file.length()); // Mover al final del fichero
            escribirProducto(file, producto);
        }
    }

    public String visualizarFichero() throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            while (file.getFilePointer() < file.length()) {
                Producto producto = leerProducto(file);
                contenido.append(producto.toString()).append("\n");
            }
        }
        return contenido.toString();
    }

    public String visualizarProducto(int codProducto) throws IOException {
        StringBuilder productoInfo = new StringBuilder();
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            file.seek((codProducto - 1) * TAMANO_REGISTRO);
            Producto producto = leerProducto(file);
            productoInfo.append(producto.toString());
        }
        return productoInfo.toString();
    }

    public void modificarUnidades(int codProducto, int nuevasUnidades) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            file.seek((codProducto - 1) * TAMANO_REGISTRO + 14); // Ajusta el desplazamiento según tu estructura
            file.writeInt(nuevasUnidades);
        }
    }

    private void escribirProducto(RandomAccessFile file, Producto producto) throws IOException {
        file.writeInt(producto.getCodProducto());
        file.write(fixedSizeString(producto.getNombreProducto(), 10).getBytes(StandardCharsets.UTF_8));
        file.writeInt(producto.getUnidades());
        file.write(fixedSizeString(producto.getCodigoFamilia(), 4).getBytes(StandardCharsets.UTF_8));
    }

    private Producto leerProducto(RandomAccessFile file) throws IOException {
        int codProducto = file.readInt();
        String nombreProducto = leerString(file, 10);
        int unidades = file.readInt();
        String codigoFamilia = leerString(file, 4);
        return new Producto(codProducto, nombreProducto, unidades, codigoFamilia);
    }

    private String leerString(RandomAccessFile file, int length) throws IOException {
        byte[] bytes = new byte[length];
        file.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8).trim();
    }

    private String fixedSizeString(String string, int length) {
        return String.format("%1$-" + length + "s", string);
    }
}
