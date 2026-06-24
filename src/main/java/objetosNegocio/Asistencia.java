package objetosNegocio;

import objetosServicio.Fecha;

public class Asistencia {

    private String codigoEmpleado;
    private Fecha fecha;
    private String horaIngreso;
    private String horaSalida;
    private String estado; // PRESENTE, TARDE, AUSENTE, JUSTIFICADO

    public Asistencia() {
    }

    public Asistencia(String codigoEmpleado, Fecha fecha, String horaIngreso) {
        this.codigoEmpleado = codigoEmpleado;
        this.fecha = fecha;
        this.horaIngreso = horaIngreso;
        this.estado = "PRESENTE";
    }

    // Getters y Setters
    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }

    public Fecha getFecha() { return fecha; }
    public void setFecha(Fecha fecha) { this.fecha = fecha; }

    public String getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(String horaIngreso) { this.horaIngreso = horaIngreso; }

    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return codigoEmpleado + " | " + fecha + " | In: " + horaIngreso +
                (horaSalida != null ? " | Out: " + horaSalida : "");
    }
}