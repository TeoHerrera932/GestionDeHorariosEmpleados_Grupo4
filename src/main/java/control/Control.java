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

    //Acceso a los objetos del negocio
    IFachada fachada;
    Conversiones conversiones;

    //Vectores con los nombres de las columnas de las tablas
    ArrayList nombresColumnasTablaEmpleados = new ArrayList();
    ArrayList nombresColumnasTablaUsuarios = new ArrayList();

    public Control(){
        //Crea un objeto tipo fachada
        fachada = new FachadaArchivos();
        conversiones = new Conversiones();

        //Llena el arraylist con los nombres de las columnas de la tabla de Empleados
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

        //Llena el arraylist con los nombres de las columnas de la tabla de Usuarios
        nombresColumnasTablaUsuarios.add("Cargo");
        nombresColumnasTablaUsuarios.add("Usuario");
        nombresColumnasTablaUsuarios.add("Contraseña");
    }

    // ====================== MÉTODOS PARA EMPLEADOS ======================

    public void agregaEmpleado(JFrame frame){
        
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

    public void actualizaEmpleado(JFrame frame){
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

    public void eliminaEmpleado(JFrame frame){
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

    

    // ====================== MÉTODOS PARA USUARIOS ======================

    public void agregaUsuario(JFrame frame){
        // Similar a agregaEmpleado... (puedes implementarlo después)
        JOptionPane.showMessageDialog(frame, "Funcionalidad de agregar usuario en desarrollo");
    }

    public Usuario login(String nombreUsuario, String contrasena) {
        try {
            Usuario usuario = fachada.obtenPorUsuario(nombreUsuario);
            if (usuario != null && usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        } catch (Exception e) {
            // Manejo silencioso o log
        }
        return null;
    }
    public void registrarAsistencia(String codigoEmpleado, String horaIngreso, String horaSalida) {
        try {
            // Crear fecha actual
            java.util.Calendar cal = java.util.Calendar.getInstance();
            Fecha fechaActual = new Fecha(
                    cal.get(java.util.Calendar.DAY_OF_MONTH),
                    cal.get(java.util.Calendar.MONTH) + 1,
                    cal.get(java.util.Calendar.YEAR)
            );

            Asistencia asistencia = new Asistencia(codigoEmpleado, fechaActual, horaIngreso);

            if (horaSalida != null && !horaSalida.trim().isEmpty()) {
                asistencia.setHoraSalida(horaSalida);
            }

            fachada.registraAsistencia(asistencia);

            JOptionPane.showMessageDialog(null,
                    "✅ Asistencia registrada correctamente\n\n" +
                            "Empleado: " + codigoEmpleado + "\n" +
                            "Fecha: " + fechaActual + "\n" +
                            "Hora Ingreso: " + horaIngreso +
                            (horaSalida != null && !horaSalida.isEmpty() ? "\nHora Salida: " + horaSalida : ""),
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al registrar asistencia:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error inesperado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public IFachada getFachada() {
        return fachada;
    }
}