package pruebas;

import java.util.ArrayList;
import objetosServicio.Fecha;
import objetosNegocio.*;
import excepciones.FachadaException;
import interfaces.IFachada;
import fachadas.FachadaArchivos;

public class PruebaDeFuncionamiento {

    public static void main(String[] args) {
        IFachada fachada = new FachadaArchivos();
        System.out.println("=== PRUEBA DE FUNCIONAMIENTO - SISTEMA COMPLETO ===\n");

        try {
            // ==================== 1. CREAR CARGOS ====================
            System.out.println("--- Creando Cargos ---");
            Cargo cargoGerente = new Cargo("GE", "Gerente General", "Matriz", "Diurno");
            Cargo cargoSoporte = new Cargo("SP", "Soporte Técnico", "Centro Norte", "Mixto");
            Cargo cargoEmpleado = new Cargo("EM", "Empleado Operativo", "Centro Sur", "Vespertino");

            fachada.agregaCargo(cargoGerente);
            fachada.agregaCargo(cargoSoporte);
            fachada.agregaCargo(cargoEmpleado);
            System.out.println("✓ Cargos creados: GE, SP, EM");

            // ==================== 2. CREAR HORARIOS ====================
            System.out.println("\n--- Creando Horarios ---");
            Horario horarioMatutino = new Horario("MAT", "Matutino", "08:00", "16:00");
            Horario horarioVespertino = new Horario("VES", "Vespertino", "14:00", "22:00");
            Horario horarioNocturno = new Horario("NOC", "Nocturno", "22:00", "06:00");

            fachada.agregaHorario(horarioMatutino);
            fachada.agregaHorario(horarioVespertino);
            fachada.agregaHorario(horarioNocturno);
            System.out.println("✓ Horarios creados: MAT, VES, NOC");

            // ==================== 3. CREAR CENTROS ====================
            System.out.println("\n--- Creando Centros ---");
            Centro centroMatriz = new Centro("MATRIZ", "Av. Principal 123");
            Centro centroNorte = new Centro("NORTE", "Calle Norte 456");
            Centro centroSur = new Centro("SUR", "Av. Sur 789");

            fachada.agregaCentro(centroMatriz);
            fachada.agregaCentro(centroNorte);
            fachada.agregaCentro(centroSur);
            System.out.println("✓ Centros creados: MATRIZ, NORTE, SUR");

            // ==================== 4. CREAR EMPLEADOS ====================
            System.out.println("\n--- Creando Empleados ---");

            // Empleado 1 - Gerente
            Empleado emp1 = new Empleado("EMP001", "Juan Carlos", "Pérez López",
                    new Fecha(15, 5, 1985), "Masculino", "Casado",
                    "Av. Principal 123", "juan.perez@email.com",
                    cargoGerente, new Fecha(1, 3, 2020), null,
                    "jperez", "12345678", "MAT", "0987654321");

            // Empleado 2 - Soporte
            Empleado emp2 = new Empleado("EMP002", "María Elena", "Gómez Ruiz",
                    new Fecha(22, 8, 1992), "Femenino", "Soltera",
                    "Calle Secundaria 45", "maria.gomez@email.com",
                    cargoSoporte, new Fecha(15, 6, 2022), null,
                    "mgomez", "87654321", "VES", "0998877665");

            // Empleado 3 - Operativo
            Empleado emp3 = new Empleado("EMP003", "Carlos Alberto", "Mendoza",
                    new Fecha(10, 11, 1988), "Masculino", "Divorciado",
                    "Urbanización Los Pinos", "carlos.m@email.com",
                    cargoEmpleado, new Fecha(1, 9, 2023), null,
                    "cmendoza", "45678901", "NOC", "0976543210");

            fachada.agrega(emp1);
            fachada.agrega(emp2);
            fachada.agrega(emp3);
            System.out.println("✓ Empleados creados: EMP001, EMP002, EMP003");

            // ==================== 5. CREAR USUARIOS ====================
            System.out.println("\n--- Creando Usuarios ---");
            Usuario userAdmin = new Usuario("admin", "admin", "admin123");
            Usuario userSoporte = new Usuario("soporte", "soporte1", "soporte2025");
            Usuario userEmpleado = new Usuario("empleado", "cmendoza", "pass123");

            fachada.agrega(userAdmin);
            fachada.agrega(userSoporte);
            fachada.agrega(userEmpleado);
            System.out.println("✓ Usuarios creados: admin, soporte1, cmendoza");

            // ==================== 6. REGISTRAR ASISTENCIAS ====================
            System.out.println("\n--- Registrando Asistencias ---");
            Fecha fechaHoy = new Fecha(9, 7, 2026);
            Asistencia asis1 = new Asistencia("EMP001", fechaHoy, "08:00");
            asis1.setHoraSalida("16:30");
            Asistencia asis2 = new Asistencia("EMP002", fechaHoy, "14:15");
            asis2.setHoraSalida("22:00");
            Asistencia asis3 = new Asistencia("EMP003", fechaHoy, "22:05");
            asis3.setHoraSalida("06:10");

            fachada.registraAsistencia(asis1);
            fachada.registraAsistencia(asis2);
            fachada.registraAsistencia(asis3);
            System.out.println("✓ Asistencias registradas para hoy");

            // ==================== 7. REGISTRAR AUSENCIAS ====================
            System.out.println("\n--- Registrando Ausencias ---");
            Fecha fechaAyer = new Fecha(8, 7, 2026);
            Ausencia aus1 = new Ausencia("EMP001", fechaAyer, "Permiso médico");
            aus1.setEstado("JUSTIFICADA");
            Ausencia aus2 = new Ausencia("EMP003", fechaAyer, "Falta injustificada");

            fachada.registraAusencia(aus1);
            fachada.registraAusencia(aus2);
            System.out.println("✓ Ausencias registradas para ayer");

            // ==================== 8. REGISTRAR VACACIONES ====================
            System.out.println("\n--- Registrando Vacaciones ---");
            Vacacion vac1 = new Vacacion("EMP002", new Fecha(1, 8, 2026), new Fecha(15, 8, 2026));
            vac1.setFechaReincorporacion(new Fecha(16, 8, 2026));
            vac1.setEstado("APROBADA");

            fachada.registraVacacion(vac1);
            System.out.println("✓ Vacación registrada para EMP002");

            // ==================== 9. CONSULTAS Y LISTADOS ====================
            System.out.println("\n--- Listado de Empleados ---");
            ArrayList<Empleado> empleados = fachada.consultaEmpleados();
            for (Empleado e : empleados) {
                System.out.println(e);
            }

            System.out.println("\n--- Listado de Usuarios ---");
            ArrayList<Usuario> usuarios = fachada.consultaUsuarios();
            for (Usuario u : usuarios) {
                System.out.println(u);
            }

            System.out.println("\n--- Listado de Cargos ---");
            ArrayList<Cargo> cargos = fachada.consultaCargos();
            for (Cargo c : cargos) {
                System.out.println(c);
            }

            System.out.println("\n--- Listado de Horarios ---");
            ArrayList<Horario> horarios = fachada.consultaHorarios();
            for (Horario h : horarios) {
                System.out.println(h);
            }

            System.out.println("\n--- Listado de Centros ---");
            ArrayList<Centro> centros = fachada.consultaCentros();
            for (Centro c : centros) {
                System.out.println(c);
            }

            System.out.println("\n--- Asistencias de EMP001 ---");
            ArrayList<Asistencia> asistencias = fachada.consultaAsistenciasPorEmpleado("EMP001");
            for (Asistencia a : asistencias) {
                System.out.println(a);
            }

            System.out.println("\n--- Ausencias de EMP003 ---");
            ArrayList<Ausencia> ausencias = fachada.consultaAusenciasPorEmpleado("EMP003");
            for (Ausencia a : ausencias) {
                System.out.println(a);
            }

            System.out.println("\n--- Vacaciones de EMP002 ---");
            ArrayList<Vacacion> vacaciones = fachada.consultaVacacionesPorEmpleado("EMP002");
            for (Vacacion v : vacaciones) {
                System.out.println(v);
            }

            System.out.println("\n--- Prueba de login (admin/admin123) ---");
            Usuario loginUser = fachada.obtenPorUsuario("admin");
            if (loginUser != null && loginUser.getContrasena().equals("admin123")) {
                System.out.println("✓ Login exitoso para admin");
            } else {
                System.out.println("✗ Falló login para admin");
            }

            System.out.println("\n=== PRUEBA DE FUNCIONAMIENTO FINALIZADA CORRECTAMENTE ===");

        } catch (FachadaException e) {
            System.err.println("ERROR EN PRUEBA: " + e.getMessage());
            e.printStackTrace();
        }
    }
}