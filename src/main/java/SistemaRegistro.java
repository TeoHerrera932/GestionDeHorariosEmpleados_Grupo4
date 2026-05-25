// SistemaRegistro.java
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Clase que gestiona la lógica principal del sistema:
 * empleados y registros usando ArrayList.
 */
public class SistemaRegistro implements GestorRegistro {

    private final ArrayList<Empleado> empleados = new ArrayList<>();
    private final ArrayList<RegistroHora> registros = new ArrayList<>();

    /**
     * Agrega un nuevo empleado al sistema
     */
    @Override
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

    @Override
    public Empleado obtenerEmpleadoPorId(String id) throws EmpleadoNoEncontradoException {
        return buscarEmpleadoPorId(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Empleado no encontrado: " + id));
    }

    /**
     * Registra entrada de un empleado
     */
    @Override
    public void registrarEntrada(Empleado empleado, LocalDate fecha, LocalTime horaEntrada) throws RegistroException {
        if (empleado == null) {
            throw new RegistroException("Empleado inválido para registrar entrada.");
        }
        RegistroHora registro = new RegistroHora(empleado, fecha, horaEntrada);
        registros.add(registro);
        System.out.println("Entrada registrada correctamente para " + empleado.getNombre());
    }

    /**
     * Registra salida del empleado
     */
    @Override
    public void registrarSalida(String empleadoId, LocalDate fecha, LocalTime horaSalida) throws RegistroNoEncontradoException {
        Optional<RegistroHora> registroOpcional = registros.stream()
                .filter(r -> r.getEmpleado().getId().equals(empleadoId) &&
                        r.getFecha().equals(fecha) &&
                        r.getHoraSalida() == null)
                .findFirst();

        if (registroOpcional.isEmpty()) {
            throw new RegistroNoEncontradoException("No se encontró un registro pendiente de salida para el empleado: " + empleadoId);
        }

        registroOpcional.get().registrarSalida(horaSalida);
        System.out.println("Salida registrada correctamente.");
    }

    /**
     * Muestra todos los registros de un empleado
     */
    @Override
    public void mostrarRegistrosEmpleado(String id) throws EmpleadoNoEncontradoException {
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
            throw new EmpleadoNoEncontradoException("No se encontraron registros para el empleado: " + id);
        }
    }

    public ArrayList<RegistroHora> getRegistros() {
        return registros;
    }
}