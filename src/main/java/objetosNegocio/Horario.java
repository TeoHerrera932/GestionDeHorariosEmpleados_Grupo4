package objetosNegocio;

public class Horario {
    private String codigo;
    private String nombre;
    private String horaInicio;
    private String horaFin;

    public Horario() {}

    public Horario(String codigo, String nombre, String horaInicio, String horaFin) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + horaInicio + " - " + horaFin + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Horario h)) return false;
        return codigo != null && codigo.equals(h.codigo);
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
}