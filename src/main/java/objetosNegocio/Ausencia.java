package objetosNegocio;

import objetosServicio.Fecha;

public class Ausencia {

    private String codigoEmpleado;
    private Fecha fecha;
    private String motivo;
    private String estado; // JUSTIFICADA, NO_JUSTIFICADA

    public Ausencia() {
    }

    public Ausencia(String codigoEmpleado, Fecha fecha, String motivo) {
        this.codigoEmpleado = codigoEmpleado;
        this.fecha = fecha;
        this.motivo = motivo;
        this.estado = "NO_JUSTIFICADA";
    }

    // Getters y Setters
    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }

    public Fecha getFecha() { return fecha; }
    public void setFecha(Fecha fecha) { this.fecha = fecha; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}