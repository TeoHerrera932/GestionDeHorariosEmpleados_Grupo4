package pruebas;

import java.util.ArrayList;
import objetosServicio.Fecha;
import objetosNegocio.Empleado;
import objetosNegocio.Usuario;
import excepciones.FachadaException;
import interfaces.IFachada;
import fachadas.FachadaArchivos;

public class PruebaDeFuncionamiento {

    public static void main(String[] args) {
        IFachada fachada = new FachadaArchivos();
        ArrayList lista = null;
        Empleado empleado;

        System.out.println("=== PRUEBA DE FUNCIONAMIENTO - EMPLEADOS Y USUARIOS ===\n");

        // ==================== PRUEBAS DE EMPLEADOS ====================

        // Creación de empleados (SIN CI)
        Empleado emp1 = new Empleado("EMP001", "Juan Carlos", "Pérez López",
                new Fecha(15, 5, 1985), "Masculino", "Casado",
                "Av. Principal 123", "juan.perez@email.com", "Gerente",
                "Centro Principal", new Fecha(1, 3, 2020), null,
                "jperez", "12345678", "Lunes a Viernes 8-17", "0987654321");

        Empleado emp2 = new Empleado("EMP002", "María Elena", "Gómez Ruiz",
                new Fecha(22, 8, 1992), "Femenino", "Soltera",
                "Calle Secundaria 45", "maria.gomez@email.com", "Soporte",
                "Centro Norte", new Fecha(15, 6, 2022), null,
                "mgomez", "87654321", "Lunes a Sábado 9-18", "0998877665");

        Empleado emp3 = new Empleado("EMP003", "Carlos Alberto", "Mendoza",
                new Fecha(10, 11, 1988), "Masculino", "Divorciado",
                "Urbanización Los Pinos", "carlos.m@email.com", "Empleado",
                "Centro Sur", new Fecha(1, 9, 2023), null,
                "cmendoza", "45678901", "Lunes a Viernes 7-16", "0976543210");

        System.out.println("--- Agregando Empleados ---");

        try {
            fachada.agrega(emp1);
            System.out.println("✓ Se agregó el empleado EMP001");
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        try {
            fachada.agrega(emp2);
            System.out.println("✓ Se agregó el empleado EMP002");
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        try {
            fachada.agrega(emp3);
            System.out.println("✓ Se agregó el empleado EMP003");
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        // Intento de agregar duplicado
        try {
            fachada.agrega(emp1);
        } catch (FachadaException fe) {
            System.out.println("✓ Detectado correctamente: " + fe.getMessage());
        }

        // Listar empleados
        System.out.println("\n--- Lista de Empleados ---");
        try {
            lista = fachada.consultaEmpleados();
            System.out.println(lista);
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        // Actualizar empleado
        System.out.println("\n--- Actualizando Empleado ---");
        try {
            empleado = fachada.obten(new Empleado("EMP002"));
            if (empleado != null) {
                empleado.setCargo("Supervisor");
                empleado.setCentroDeTrabajo("Centro Principal");
                fachada.actualiza(empleado);
                System.out.println("✓ Se actualizó el empleado EMP002");
            }
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        // Eliminar empleado
        System.out.println("\n--- Eliminando Empleado ---");
        try {
            fachada.elimina(new Empleado("EMP003"));
            System.out.println("✓ Se eliminó el empleado EMP003");
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        // Lista final
        System.out.println("\n--- Lista Final de Empleados ---");
        try {
            lista = fachada.consultaEmpleados();
            System.out.println(lista);
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        // ==================== PRUEBAS DE USUARIOS ====================

        System.out.println("\n=== PRUEBAS DE USUARIOS ===\n");

        Usuario user1 = new Usuario("admin", "admin", "admin123");
        Usuario user2 = new Usuario("soporte", "soporte1", "soporte2025");
        Usuario user3 = new Usuario("empleado", "cmendoza", "pass123");

        try {
            fachada.agrega(user1);
            System.out.println("✓ Se agregó usuario admin");
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        try {
            fachada.agrega(user2);
            System.out.println("✓ Se agregó usuario soporte");
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        try {
            fachada.agrega(user3);
            System.out.println("✓ Se agregó usuario empleado");
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        System.out.println("\n--- Lista de Usuarios ---");
        try {
            lista = fachada.consultaUsuarios();
            System.out.println(lista);
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        // Prueba de login
        System.out.println("\n--- Búsqueda por Usuario (Login) ---");
        try {
            Usuario u = fachada.obtenPorUsuario("admin");
            System.out.println("Usuario encontrado: " + u);
        } catch (FachadaException fe) {
            System.out.println("ERROR: " + fe.getMessage());
        }

        System.out.println("\n=== Prueba de Funcionamiento Finalizada Correctamente ===");
    }
}