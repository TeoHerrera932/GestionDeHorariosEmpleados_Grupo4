package control;

import java.util.ArrayList;
import java.util.stream.Collectors;

import objetosNegocio.Empleado;
import objetosNegocio.Usuario;

public class Conversiones {

    public ArrayList<Object[]> listaTablaEmpleados(ArrayList<Empleado> listaEmpleados) {
        if (listaEmpleados == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(listaEmpleados.stream()
                .map(e -> new Object[]{
                        e.getCodigoEmpleado(),
                        e.getNombres(),
                        e.getApellidos(),
                        e.getFechaNacimiento() != null ? e.getFechaNacimiento().toString() : "",
                        e.getGenero(),
                        e.getEstadoCivil(),
                        e.getDireccion(),
                        e.getCorreo(),
                        e.getCargo() != null ? e.getCargo().getNombreCargo() : "(Sin cargo)",
                        e.getCargo() != null ? e.getCargo().getCentroTrabajo() : "(Sin centro)",
                        e.getFechaIngreso() != null ? e.getFechaIngreso().toString() : "",
                        e.getFechaBaja() != null ? e.getFechaBaja().toString() : "",
                        e.getUsuario(),
                        e.getCedula(),
                        e.getHorario(),
                        e.getCelular()
                })
                .collect(Collectors.toList()));
    }

    public ArrayList<Object[]> listaTablaUsuarios(ArrayList<Usuario> listaUsuarios) {
        if (listaUsuarios == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(listaUsuarios.stream()
                .map(u -> new Object[]{
                        u.getCargo(),
                        u.getUsuario(),
                        "********"
                })
                .collect(Collectors.toList()));
    }
}