package persistencia;// Persistencia.RegistroHora.java
import objetosNegocio.Empleado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

/**
 * Representa un registro de entrada y salida de un empleado.
 * Sigue los principios de encapsulación.
 */
public class RegistroHora {

    private final Empleado empleado;        // Relación con objetosNegocio.Empleado
    private final LocalDate fecha;
    private final LocalTime horaEntrada;
    private LocalTime horaSalida;

    /**
     * Constructor: siempre se crea con entrada
     */
    public RegistroHora(Empleado empleado, LocalDate fecha, LocalTime horaEntrada) {
        this.empleado = empleado;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = null; // Salida pendiente
    }

    /**
     * Registra la hora de salida
     */
    public void registrarSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    // ==================== GETTERS ====================

    public Empleado getEmpleado() {
        return empleado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    /**
     * Calcula las horas trabajadas
     */
    public Duration getHorasTrabajadas() {
        if (horaSalida == null) {
            return Duration.ZERO;
        }
        return Duration.between(horaEntrada, horaSalida);
    }
}