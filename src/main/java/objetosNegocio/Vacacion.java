package objetosNegocio;

import objetosServicio.Fecha;

public class Vacacion {

    private String codigoEmpleado;
    private Fecha fechaInicio;
    private Fecha fechaFin;
    private Fecha fechaReincorporacion;
    private String estado; // APROBADA, PENDIENTE, RECHAZADA

    public Vacacion() {
    }

    public Vacacion(String codigoEmpleado, Fecha fechaInicio, Fecha fechaFin) {
        this.codigoEmpleado = codigoEmpleado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = "PENDIENTE";
    }

    // Getters y Setters
    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }

    public Fecha getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Fecha fechaInicio) { this.fechaInicio = fechaInicio; }

    public Fecha getFechaFin() { return fechaFin; }
    public void setFechaFin(Fecha fechaFin) { this.fechaFin = fechaFin; }

    public Fecha getFechaReincorporacion() { return fechaReincorporacion; }
    public void setFechaReincorporacion(Fecha fechaReincorporacion) { this.fechaReincorporacion = fechaReincorporacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}