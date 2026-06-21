package control;

import java.util.ArrayList;
import objetosNegocio.Empleado;
import objetosNegocio.Usuario;

public class Conversiones {

    public ArrayList listaTablaEmpleados(ArrayList listaEmpleados) {
        ArrayList<Object[]> datos = new ArrayList<>();
        if (listaEmpleados == null) return datos;

        for (Object obj : listaEmpleados) {
            Empleado e = (Empleado) obj;
            Object[] fila = {
                    e.getCodigoEmpleado(),
                    e.getNombres(),
                    e.getApellidos(),
                    e.getFechaNacimiento() != null ? e.getFechaNacimiento().toString() : "",
                    e.getGenero(),
                    e.getEstadoCivil(),
                    e.getDireccion(),
                    e.getCorreo(),
                    e.getCargo(),
                    e.getCentroDeTrabajo(),
                    e.getFechaIngreso() != null ? e.getFechaIngreso().toString() : "",
                    e.getFechaBaja() != null ? e.getFechaBaja().toString() : "",
                    e.getUsuario(),
                    e.getCedula(),
                    e.getHorario(),
                    e.getCelular()
            };
            datos.add(fila);
        }
        return datos;
    }

    // Por si necesitas tabla de usuarios
    public ArrayList listaTablaUsuarios(ArrayList listaUsuarios) {
        ArrayList<Object[]> datos = new ArrayList<>();
        if (listaUsuarios == null) return datos;

        for (Object obj : listaUsuarios) {
            Usuario u = (Usuario) obj;
            Object[] fila = {
                    u.getCargo(),
                    u.getUsuario(),
                    "********"  // No mostrar contraseña real
            };
            datos.add(fila);
        }
        return datos;
    }
}