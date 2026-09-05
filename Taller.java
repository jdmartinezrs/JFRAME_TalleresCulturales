package actividad33;

import java.util.ArrayList;
import java.util.List;

public class Taller {
    private String codigo;
    private String nombre;
    private String instructor;
    private int cupoMaximo;
    private double costo;
    private boolean activo;
    private List<Participante> participantesInscritos;

    public Taller(String codigo, String nombre, String instructor, int cupoMaximo, double costo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.instructor = instructor;
        this.cupoMaximo = cupoMaximo;
        this.costo = costo;
        this.activo = true;
        this.participantesInscritos = new ArrayList<>();
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getInstructor() { return instructor; }
    public int getCupoMaximo() { return cupoMaximo; }
    public double getCosto() { return costo; }
    public boolean isActivo() { return activo; }
    public List<Participante> getParticipantesInscritos() { return participantesInscritos; }

    // Setters
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }
    public void setCosto(double costo) { this.costo = costo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public int getCuposDisponibles() {
        return cupoMaximo - participantesInscritos.size();
    }

    public boolean inscribirParticipante(Participante p) {
        if (activo && getCuposDisponibles() > 0) {
            participantesInscritos.add(p);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return nombre + " [Código: " + codigo + " | Libres: " + getCuposDisponibles() + "/" + cupoMaximo + "]";
    }
}