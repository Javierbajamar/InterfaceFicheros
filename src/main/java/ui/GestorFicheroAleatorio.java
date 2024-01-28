package ui;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class GestorFicheroAleatorio {


    private final String filePath;
    private static final int TAMANO_REGISTRO = 30; // Ajusta según la estructura de tus datos

    public GestorFicheroAleatorio(String filePath) {
        this.filePath = filePath;
    }

    public void insertarProducto(Producto producto) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            file.seek(file.length()); // Mover al final del fichero
            escribirProducto(file, producto);
        }
    }

    public void visualizarFichero() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            while (file.getFilePointer() < file.length()) {
                Producto producto = leerProducto(file);
                System.out.println(producto);
            }
        }
    }

    public void visualizarProducto(int codProducto) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            file.seek((codProducto - 1) * TAMANO_REGISTRO);
            Producto producto = leerProducto(file);
            System.out.println(producto);
        }
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
