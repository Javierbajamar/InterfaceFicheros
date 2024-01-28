package ui;

public class Producto {


    private int codProducto;
    private String nombreProducto; // Asegúrate de que siempre tenga 10 caracteres
    private int unidades;
    private String codigoFamilia; // Asegúrate de que siempre tenga 4 caracteres

    public Producto(int codProducto, String nombreProducto, int unidades, String codigoFamilia) {

        this.codProducto = codProducto;
        this.nombreProducto = nombreProducto;
        this.unidades = unidades;
        this.codigoFamilia = codigoFamilia;

    }


    public int getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public String getCodigoFamilia() {
        return codigoFamilia;
    }

    public void setCodigoFamilia(String codigoFamilia) {
        this.codigoFamilia = codigoFamilia;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codProducto=" + codProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", unidades=" + unidades +
                ", codigoFamilia='" + codigoFamilia + '\'' +
                '}';
    }
}
