// Empleado.java
import objetosNegocio.Usuario;

import java.time.LocalTime;

/**
 * Clase Empleado que hereda de ObjetosNegocio.Usuario.
 * Representa a un trabajador del sistema con información laboral.
 */
public class Empleado extends Usuario {

    private String cargo;
    private LocalTime horaEntradaEstandar;
    private LocalTime horaSalidaEstandar;

    /**
     * Constructor completo de Empleado
     */
    public Empleado(String id, String nombre, String apellido, String usuario,
                    String contrasena, String cargo,
                    LocalTime horaEntradaEstandar, LocalTime horaSalidaEstandar) {
        super(id, nombre, apellido, usuario, contrasena);
        this.cargo = cargo;
        this.horaEntradaEstandar = horaEntradaEstandar;
        this.horaSalidaEstandar = horaSalidaEstandar;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getCargo() {
        return cargo;
    }

    public LocalTime getHoraEntradaEstandar() {
        return horaEntradaEstandar;
    }

    public LocalTime getHoraSalidaEstandar() {
        return horaSalidaEstandar;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setHorarioEstandar(LocalTime entrada, LocalTime salida) {
        this.horaEntradaEstandar = entrada;
        this.horaSalidaEstandar = salida;
    }

    @Override
    public String getTipoUsuario() {
        return "Empleado";
    }
}