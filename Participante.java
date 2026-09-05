package actividad33;

public class Participante {
    private String documento;
    private String nombre;
    private String correo;

    public Participante(String documento, String nombre, String correo) {
        this.documento = documento;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getDocumento() { return documento; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }

    @Override
    public String toString() {
        return nombre + " (" + documento + ")";
    }
}