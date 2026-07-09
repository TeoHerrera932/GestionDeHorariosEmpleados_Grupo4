package objetosNegocio;

public class Cargo {

    private String codigoCargo;     // Ej: "GE", "ST", "AN"
    private String nombreCargo;     // Ej: "Gerente General"
    private String centroTrabajo;
    private String tipoHorario;     // Ej: "Diurno", "Nocturno", "Mixto"

    public Cargo() {
    }

    public Cargo(String codigoCargo, String nombreCargo, String centroTrabajo, String tipoHorario) {
        this.codigoCargo = codigoCargo;
        this.nombreCargo = nombreCargo;
        this.centroTrabajo = centroTrabajo;
        this.tipoHorario = tipoHorario;
    }

    // Getters y Setters
    public String getCodigoCargo() { return codigoCargo; }
    public void setCodigoCargo(String codigoCargo) { this.codigoCargo = codigoCargo; }

    public String getNombreCargo() { return nombreCargo; }
    public void setNombreCargo(String nombreCargo) { this.nombreCargo = nombreCargo; }

    public String getCentroTrabajo() { return centroTrabajo; }
    public void setCentroTrabajo(String centroTrabajo) { this.centroTrabajo = centroTrabajo; }

    public String getTipoHorario() { return tipoHorario; }
    public void setTipoHorario(String tipoHorario) { this.tipoHorario = tipoHorario; }

    @Override
    public String toString() {
        String cod = (codigoCargo != null) ? codigoCargo : "";
        String nom = (nombreCargo != null) ? nombreCargo : "Sin nombre";
        String centro = (centroTrabajo != null) ? centroTrabajo : "";
        return cod + " - " + nom + (centro.isEmpty() ? "" : " (" + centro + ")");
    }
}