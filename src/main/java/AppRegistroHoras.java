// AppRegistroHoras.java
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Clase principal del sistema.
 * Contiene el método main() y la interfaz por consola.
 */
public class AppRegistroHoras {

    private static final GestorRegistro sistema = new SistemaRegistro();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     SISTEMA DE REGISTRO DE HORAS ");
        System.out.println("=================================================");

        if (!loginAdministrador()) {
            System.out.println("Acceso denegado. Contraseña incorrecta.");
            return;
        }

        cargarDatosPrueba();
        mostrarMenuPrincipal();
    }

    /**
     * Login simple de administrador
     */
    private static boolean loginAdministrador() {
        System.out.print("ObjetosNegocio.Usuario admin: ");
        String user = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        return "admin".equals(user) && "1234".equals(pass);
    }

    /**
     * Carga empleados de prueba
     */
    private static void cargarDatosPrueba() {
        Empleado e1 = new Empleado("E001", "Juan", "Pérez", "juanp", "1234",
                "Desarrollador", LocalTime.of(8, 0), LocalTime.of(17, 0));

        Empleado e2 = new Empleado("E002", "María", "González", "mariag", "1234",
                "Diseñadora", LocalTime.of(9, 0), LocalTime.of(18, 0));

        sistema.agregarEmpleado(e1);
        sistema.agregarEmpleado(e2);
    }

    /**
     * Menú principal del sistema
     */
    private static void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n------------------- MENÚ PRINCIPAL -------------------");
            System.out.println("1. Registrar Entrada");
            System.out.println("2. Registrar Salida");
            System.out.println("3. Ver Registros de Empleado");
            System.out.println("4. Calcular Horas Extras");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> registrarEntrada();
                case 2 -> registrarSalida();
                case 3 -> verRegistros();
                case 4 -> calcularHorasExtras();
                case 0 -> System.out.println("¡Gracias por usar el sistema!");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void registrarEntrada() {
        System.out.print("Ingrese ID del empleado: ");
        String id = scanner.nextLine();

        try {
            Empleado empleado = sistema.obtenerEmpleadoPorId(id);
            sistema.registrarEntrada(empleado, LocalDate.now(), LocalTime.now());
        } catch (RegistroException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void registrarSalida() {
        System.out.print("Ingrese ID del empleado: ");
        String id = scanner.nextLine();
        try {
            sistema.registrarSalida(id, LocalDate.now(), LocalTime.now());
        } catch (RegistroNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void verRegistros() {
        System.out.print("Ingrese ID del empleado: ");
        String id = scanner.nextLine();
        try {
            sistema.mostrarRegistrosEmpleado(id);
        } catch (EmpleadoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void calcularHorasExtras() {
        System.out.print("Ingrese ID del empleado: ");
        String id = scanner.nextLine();

        sistema.getRegistros().stream()
                .filter(r -> r.getEmpleado().getId().equals(id) && r.getHoraSalida() != null)
                .forEach(registro -> {
                    Duration trabajadas = registro.getHorasTrabajadas();
                    Duration estandar = Duration.between(
                            registro.getEmpleado().getHoraEntradaEstandar(),
                            registro.getEmpleado().getHoraSalidaEstandar()
                    );

                    if (trabajadas.compareTo(estandar) > 0) {
                        Duration extras = trabajadas.minus(estandar);
                        double horasExtras = extras.toMinutes() / 60.0;
                        System.out.printf("Fecha: %s | Horas extras: %.2f horas%n",
                                registro.getFecha(), horasExtras);
                        System.out.println("→ Aplicar 50% o 100% según tipo de empleado.");
                    }
                });
    }
}