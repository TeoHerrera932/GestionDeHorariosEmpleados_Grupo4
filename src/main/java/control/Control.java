package control;

import javax.swing.*;
import java.util.ArrayList;
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
        Empleado empleado, bEmpleado = null;
        StringBuffer respuesta = new StringBuffer("");
        DlgEmpleado dlgEmpleado;
        //Captura el código del empleado
        String codigo = JOptionPane.showInputDialog(frame,"Código de Empleado:",
                "Agregar Empleado", JOptionPane.QUESTION_MESSAGE);
        if (codigo == null) return;

        empleado = new Empleado(codigo);
        try{
            bEmpleado = fachada.obten(empleado);
        }catch(Exception e){
            JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

        if (bEmpleado != null){
            dlgEmpleado = new DlgEmpleado(frame,"El empleado ya está registrado",
                    true, bEmpleado, UtileriasGUI.DESPLEGAR, respuesta);
            return;
        }

        dlgEmpleado = new DlgEmpleado(frame, "Captura Datos del Empleado", true, empleado,
                UtileriasGUI.AGREGAR, respuesta);

        if (respuesta.substring(0).equals(UtileriasGUI.CANCELAR)) return;

        try{
            fachada.agrega(empleado);
            JOptionPane.showMessageDialog(frame,"Empleado agregado correctamente");
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizaEmpleado(JFrame frame){
        Empleado empleado;
        StringBuffer respuesta = new StringBuffer("");
        DlgEmpleado dlgEmpleado;

        String codigo = JOptionPane.showInputDialog(frame, "Código del empleado:",
                "Actualizar Empleado", JOptionPane.QUESTION_MESSAGE);
        if (codigo == null) return;

        empleado = new Empleado(codigo);
        try{
            empleado = fachada.obten(empleado);
        }catch(Exception e){
            JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empleado == null){
            JOptionPane.showMessageDialog(frame,"El empleado no existe","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        dlgEmpleado = new DlgEmpleado(frame,"Edita Datos del Empleado", true, empleado,
                UtileriasGUI.ACTUALIZAR, respuesta);

        if (respuesta.substring(0).equals(UtileriasGUI.CANCELAR)) return;

        try{
            fachada.actualiza(empleado);
            JOptionPane.showMessageDialog(frame,"Empleado actualizado correctamente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminaEmpleado(JFrame frame){
        Empleado empleado;
        StringBuffer respuesta = new StringBuffer();
        DlgEmpleado dlgEmpleado;

        String codigo = JOptionPane.showInputDialog(frame, "Código del empleado",
                "Eliminar Empleado", JOptionPane.QUESTION_MESSAGE);
        if (codigo == null) return;

        empleado = new Empleado(codigo);
        try{
            empleado = fachada.obten(empleado);
        }catch(Exception e){
            JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empleado == null){
            JOptionPane.showMessageDialog(frame,"El empleado no existe","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        dlgEmpleado = new DlgEmpleado(frame, "Empleado a eliminar", true, empleado,
                UtileriasGUI.ELIMINAR, respuesta);

        if (respuesta.substring(0).equals(UtileriasGUI.CANCELAR)) return;

        try{
            fachada.elimina(empleado);
            JOptionPane.showMessageDialog(frame,"Empleado eliminado correctamente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
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
    public IFachada getFachada() {
        return fachada;
    }
}