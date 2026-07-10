package control;

import javax.swing.*;
import java.util.ArrayList;

import excepciones.FachadaException;
import objetosServicio.*;
import objetosNegocio.*;
import interfaces.IFachada;
import fachadas.FachadaArchivos;
import interfazUsuario.*;
import interfazUsuario.DlgEmpleado;

public class Control {

    // ================== ATRIBUTOS ==================
    private IFachada fachada;
    private Conversiones conversiones;

    // Vectores con los nombres de las columnas de las tablas
    private ArrayList<String> nombresColumnasTablaEmpleados;
    private ArrayList<String> nombresColumnasTablaUsuarios;

    // ================== CONSTRUCTOR ==================
    public Control() {
        fachada = new FachadaArchivos();
        conversiones = new Conversiones();

        // Inicializar listas de nombres de columnas
        nombresColumnasTablaEmpleados = new ArrayList<>();
        nombresColumnasTablaUsuarios = new ArrayList<>();

        // Llenar columnas de Empleados
        nombresColumnasTablaEmpleados.add("Código");
        nombresColumnasTablaEmpleados.add("Nombres");
        nombresColumnasTablaEmpleados.add("Apellidos");
        nombresColumnasTablaEmpleados.add("Fecha Nacimiento");
        nombresColumnasTablaEmpleados.add("Género");
        nombresColumnasTablaEmpleados.add("Estado Civil");
        nombresColumnasTablaEmpleados.add("Dirección");
        nombresColumnasTablaEmpleados.add("Correo");
        nombresColumnasTablaEmpleados.add("Cargo");
        nombresColumnasTablaEmpleados.add("Centro de Trabajo");
        nombresColumnasTablaEmpleados.add("Fecha Ingreso");
        nombresColumnasTablaEmpleados.add("Fecha Baja");
        nombresColumnasTablaEmpleados.add("Usuario");
        nombresColumnasTablaEmpleados.add("Cédula");
        nombresColumnasTablaEmpleados.add("Horario");
        nombresColumnasTablaEmpleados.add("Celular");

        // Llenar columnas de Usuarios
        nombresColumnasTablaUsuarios.add("Cargo");
        nombresColumnasTablaUsuarios.add("Usuario");
        nombresColumnasTablaUsuarios.add("Contraseña");
    }

    // ================== MÉTODOS PARA EMPLEADOS ==================
    public void agregaEmpleado(JFrame frame) {
        StringBuffer respuesta = new StringBuffer("");
        String codigo = JOptionPane.showInputDialog(frame, "Código de Empleado:",
                "Agregar Empleado", JOptionPane.QUESTION_MESSAGE);
        if (codigo == null) return;

        Empleado empleado = new Empleado(codigo);

        try {
            Empleado existe = fachada.obten(empleado);
            if (existe != null) {
                new DlgEmpleado(frame, this, existe, UtileriasGUI.DESPLEGAR, respuesta);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        new DlgEmpleado(frame, this, empleado, UtileriasGUI.AGREGAR, respuesta);

        if (respuesta.toString().contains(UtileriasGUI.CANCELAR)) return;

        try {
            fachada.agrega(empleado);
            JOptionPane.showMessageDialog(frame, "Empleado agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizaEmpleado(JFrame frame) {
        StringBuffer respuesta = new StringBuffer("");
        String codigo = JOptionPane.showInputDialog(frame, "Código del empleado:",
                "Actualizar Empleado", JOptionPane.QUESTION_MESSAGE);
        if (codigo == null) return;

        Empleado empleado = new Empleado(codigo);

        try {
            empleado = fachada.obten(empleado);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empleado == null) {
            JOptionPane.showMessageDialog(frame, "El empleado no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new DlgEmpleado(frame, this, empleado, UtileriasGUI.ACTUALIZAR, respuesta);

        if (respuesta.toString().contains(UtileriasGUI.CANCELAR)) return;

        try {
            fachada.actualiza(empleado);
            JOptionPane.showMessageDialog(frame, "Empleado actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminaEmpleado(JFrame frame) {
        String codigo = JOptionPane.showInputDialog(frame, "Código del empleado",
                "Eliminar Empleado", JOptionPane.QUESTION_MESSAGE);
        if (codigo == null) return;

        Empleado empleado = new Empleado(codigo);

        try {
            empleado = fachada.obten(empleado);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empleado == null) {
            JOptionPane.showMessageDialog(frame, "El empleado no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame,
                "¿Está seguro de eliminar al empleado " + empleado.getNombres() + " " + empleado.getApellidos() + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                fachada.elimina(empleado);
                JOptionPane.showMessageDialog(frame, "Empleado eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public Asistencia obtenerAsistenciaPendiente(String codigoEmpleado) throws FachadaException {
        return fachada.obtenerAsistenciaPendiente(codigoEmpleado);
    }

    public void actualizarAsistencia(Asistencia asistencia) throws FachadaException {
        fachada.actualizarAsistencia(asistencia);
    }

    // ================== MÉTODOS PARA USUARIOS ==================
    public Usuario login(String nombreUsuario, String contrasena) {
        try {
            Usuario u = fachada.obtenPorUsuario(nombreUsuario);
            if (u != null && u.getContrasena().equals(contrasena)) return u;
        } catch (Exception ignored) {}
        return null;
    }

    // ================== HORARIOS ==================
    public ArrayList<Horario> consultaHorarios() throws FachadaException {
        return fachada.consultaHorarios();
    }

    public void guardaHorario(Horario h) throws FachadaException {
        fachada.agregaHorario(h);
    }

    // ================== CENTROS ==================
    public ArrayList<Centro> consultaCentros() throws FachadaException {
        return fachada.consultaCentros();
    }

    public void guardaCentro(Centro c) throws FachadaException {
        fachada.agregaCentro(c);
    }

    // ================== ASISTENCIAS ==================
    public void registrarAsistencia(Asistencia asistencia) throws FachadaException {
        fachada.registraAsistencia(asistencia);  // Necesita que registraAsistencia acepte Asistencia
    }

    public ArrayList<Asistencia> consultaAsistenciasPorEmpleado(String codigo) throws FachadaException {
        return fachada.consultaAsistenciasPorEmpleado(codigo);
    }

    public ArrayList<Asistencia> consultaTodasAsistencias() throws FachadaException {
        return fachada.consultaAsistencias();
    }

    // ================== VACACIONES ==================
    public void registraVacacion(Vacacion v) throws FachadaException {
        fachada.registraVacacion(v);
    }

    public ArrayList<Vacacion> consultaVacacionesPorEmpleado(String codigo) throws FachadaException {
        return fachada.consultaVacacionesPorEmpleado(codigo);
    }

    public ArrayList<Vacacion> consultaVacacionesPorMes(int mes, int anio) throws FachadaException {
        return fachada.consultaVacacionesPorMes(mes, anio);
    }

    // ================== AUSENCIAS ==================
    public void registraAusencia(Ausencia a) throws FachadaException {
        fachada.registraAusencia(a);
    }

    public ArrayList<Ausencia> consultaAusenciasPorEmpleado(String codigo) throws FachadaException {
        return fachada.consultaAusenciasPorEmpleado(codigo);
    }

    public ArrayList<Ausencia> consultaAusenciasPorMes(int mes, int anio) throws FachadaException {
        return fachada.consultaAusenciasPorMes(mes, anio);
    }

    // ================== GENERACIÓN DE USUARIO SUGERIDO ==================
    public String generarUsuarioSugerido(String nombres, String apellidos) throws FachadaException {
        String base = nombres.substring(0, 2) + apellidos.substring(0, 2);
        ArrayList<Usuario> usuarios = fachada.consultaUsuarios();
        int maxNum = usuarios.stream()
                .map(Usuario::getUsuario)
                .filter(u -> u.startsWith(base))
                .map(u -> {
                    try {
                        return Integer.parseInt(u.substring(base.length()));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .orElse(0);
        return base + (maxNum + 1);
    }

    // ================== CARGOS ==================
    public ArrayList<Cargo> consultaCargos() throws FachadaException {
        return fachada.consultaCargos();
    }

    // ================== GETTER ==================
    public IFachada getFachada() {
        return fachada;
    }

    // ================== GETTERS PARA COLUMNAS (opcional) ==================
    public ArrayList<String> getNombresColumnasTablaEmpleados() {
        return nombresColumnasTablaEmpleados;
    }

    public ArrayList<String> getNombresColumnasTablaUsuarios() {
        return nombresColumnasTablaUsuarios;
    }
    public void eliminaCargo(Cargo cargo) throws FachadaException {
        fachada.eliminaCargo(cargo);
    }

    public void eliminaCentro(Centro centro) throws FachadaException {
        fachada.eliminaCentro(centro);
    }

    public void eliminaHorario(Horario horario) throws FachadaException {
        fachada.eliminaHorario(horario);
    }
    public String obtenerCodigoEmpleadoPorUsuario(String nombreUsuario) throws FachadaException {
        ArrayList<Empleado> empleados = fachada.consultaEmpleados();
        for (Empleado e : empleados) {
            if (e.getUsuario() != null && e.getUsuario().equals(nombreUsuario)) {
                return e.getCodigoEmpleado();
            }
        }
        return null;
    }
}