// SistemaRegistro.java
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Clase que gestiona la lógica principal del sistema:
 * empleados y registros usando ArrayList.
 */
public class SistemaRegistro {

    private final ArrayList<Empleado> empleados = new ArrayList<>();
    private final ArrayList<RegistroHora> registros = new ArrayList<>();

    /**
     * Agrega un nuevo empleado al sistema
     */
    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        System.out.println("Empleado agregado: " + empleado.getNombre());
    }

    /**
     * Busca empleado por ID
     */
    public Optional<Empleado> buscarEmpleadoPorId(String id) {
        return empleados.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    /**
     * Registra entrada de un empleado
     */
    public void registrarEntrada(Empleado empleado, LocalDate fecha, LocalTime horaEntrada) {
        RegistroHora registro = new RegistroHora(empleado, fecha, horaEntrada);
        registros.add(registro);
        System.out.println("Entrada registrada correctamente para " + empleado.getNombre());
    }

    /**
     * Registra salida del empleado
     */
    public void registrarSalida(String empleadoId, LocalDate fecha, LocalTime horaSalida) {
        registros.stream()
                .filter(r -> r.getEmpleado().getId().equals(empleadoId) &&
                        r.getFecha().equals(fecha) &&
                        r.getHoraSalida() == null)
                .findFirst()
                .ifPresent(registro -> {
                    registro.registrarSalida(horaSalida);
                    System.out.println("Salida registrada correctamente.");
                });
    }

    /**
     * Muestra todos los registros de un empleado
     */
    public void mostrarRegistrosEmpleado(String id) {
        System.out.println("\n=== REGISTROS DEL EMPLEADO ID: " + id + " ===");
        boolean encontrado = false;

        for (RegistroHora r : registros) {
            if (r.getEmpleado().getId().equals(id)) {
                encontrado = true;
                System.out.printf("%s | Entrada: %s | Salida: %s | Horas: %s%n",
                        r.getFecha(),
                        r.getHoraEntrada(),
                        r.getHoraSalida() != null ? r.getHoraSalida() : "PENDIENTE",
                        r.getHorasTrabajadas().toHours() + "h " +
                                (r.getHorasTrabajadas().toMinutes() % 60) + "m");
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron registros para este empleado.");
        }
    }

    public ArrayList<RegistroHora> getRegistros() {
        return registros;
    }
}