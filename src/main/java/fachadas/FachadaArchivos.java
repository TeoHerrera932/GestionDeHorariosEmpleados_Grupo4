package fachadas;

import java.util.ArrayList;
import excepciones.FachadaException;
import excepciones.PersistenciaException;
import interfaces.IFachada;
import objetosNegocio.*;
import objetosServicio.Fecha;
import persistencia.*;

public class FachadaArchivos implements IFachada {

    private Empleados catalogoEmpleados;
    private Usuarios catalogoUsuarios;
    private Asistencias catalogoAsistencias;
    private Ausencias catalogoAusencias;
    private Vacaciones catalogoVacaciones;
    private Cargos catalogoCargos;
    private Horarios catalogoHorarios;
    private Centros catalogoCentros;

    public FachadaArchivos() {
        catalogoEmpleados = new Empleados("empleados.dat");
        catalogoUsuarios = new Usuarios("usuarios.dat");
        catalogoAsistencias = new Asistencias("asistencias.dat");
        catalogoAusencias = new Ausencias("ausencias.dat");
        catalogoVacaciones = new Vacaciones("vacaciones.dat");
        catalogoCargos = new Cargos("cargos.dat");
        catalogoHorarios = new Horarios("horarios.dat");
        catalogoCentros = new Centros("centros.dat");
    }

    // ====================== EMPLEADOS ======================
    @Override
    public Empleado obten(Empleado empleado) throws FachadaException {
        try {
            return catalogoEmpleados.obten(empleado);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede obtener el empleado", pe);
        }
    }

    @Override
    public void agrega(Empleado empleado) throws FachadaException {
        try {
            Empleado existe = catalogoEmpleados.obten(empleado);
            if (existe != null) {
                throw new FachadaException("Empleado repetido");
            }
        } catch (PersistenciaException ignored) {}
        try {
            catalogoEmpleados.agrega(empleado);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede agregar el empleado", pe);
        }
    }

    @Override
    public void actualiza(Empleado empleado) throws FachadaException {
        try {
            catalogoEmpleados.actualiza(empleado);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede actualizar el empleado", pe);
        }
    }

    @Override
    public void elimina(Empleado empleado) throws FachadaException {
        try {
            catalogoEmpleados.elimina(empleado);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede eliminar el empleado", pe);
        }
    }

    @Override
    public ArrayList<Empleado> consultaEmpleados() throws FachadaException {
        try {
            return catalogoEmpleados.lista();
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede obtener la lista de empleados", pe);
        }
    }

    // ====================== USUARIOS ======================
    @Override
    public Usuario obten(Usuario usuario) throws FachadaException {
        try {
            return catalogoUsuarios.obten(usuario);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede obtener el usuario", pe);
        }
    }

    @Override
    public void agrega(Usuario usuario) throws FachadaException {
        try {
            Usuario existe = catalogoUsuarios.obten(usuario);
            if (existe != null) {
                throw new FachadaException("Usuario repetido");
            }
        } catch (PersistenciaException ignored) {}
        try {
            catalogoUsuarios.agrega(usuario);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede agregar el usuario", pe);
        }
    }

    @Override
    public void actualiza(Usuario usuario) throws FachadaException {
        try {
            catalogoUsuarios.actualiza(usuario);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede actualizar el usuario", pe);
        }
    }

    @Override
    public void elimina(Usuario usuario) throws FachadaException {
        try {
            catalogoUsuarios.elimina(usuario);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede eliminar el usuario", pe);
        }
    }

    @Override
    public ArrayList<Usuario> consultaUsuarios() throws FachadaException {
        try {
            return catalogoUsuarios.lista();
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede obtener la lista de usuarios", pe);
        }
    }

    @Override
    public Usuario obtenPorUsuario(String nombreUsuario) throws FachadaException {
        try {
            return catalogoUsuarios.obtenPorUsuario(nombreUsuario);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede obtener el usuario por nombre", pe);
        }
    }

    // ====================== ASISTENCIAS ======================
    @Override
    public void registraAsistencia(Asistencia a) throws FachadaException {
        try {
            catalogoAsistencias.agrega(a);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error registro asistencia", e);
        }
    }

    @Override
    public ArrayList<Asistencia> consultaAsistenciasPorEmpleado(String codigo) throws FachadaException {
        try {
            return catalogoAsistencias.listaPorEmpleado(codigo);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error consulta asistencias por empleado", e);
        }
    }

    @Override
    public ArrayList<Asistencia> consultaAsistencias() throws FachadaException {
        try {
            return catalogoAsistencias.lista();
        } catch (PersistenciaException e) {
            throw new FachadaException("Error listar asistencias", e);
        }
    }

    @Override
    public ArrayList<Asistencia> consultaAsistenciasPorFecha(Fecha fecha) throws FachadaException {
        try {
            // Usamos stream para filtrar, pero podríamos hacer un método en Asistencias
            return catalogoAsistencias.lista().stream()
                    .filter(a -> a.getFecha().equals(fecha))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error filtrar asistencias por fecha", e);
        }
    }
    @Override
    public Asistencia obtenerAsistenciaPendiente(String codigoEmpleado) throws FachadaException {
        try {
            // Obtener todas las asistencias del empleado
            ArrayList<Asistencia> lista = catalogoAsistencias.listaPorEmpleado(codigoEmpleado);
            // Obtener fecha actual
            java.util.Calendar cal = java.util.Calendar.getInstance();
            Fecha hoy = new Fecha(
                    cal.get(java.util.Calendar.DAY_OF_MONTH),
                    cal.get(java.util.Calendar.MONTH) + 1,
                    cal.get(java.util.Calendar.YEAR)
            );
            // Buscar la primera pendiente del día actual
            return lista.stream()
                    .filter(a -> a.getEstado().equals("PENDIENTE"))
                    .filter(a -> a.getFecha().equals(hoy))
                    .findFirst()
                    .orElse(null);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error al obtener asistencia pendiente", e);
        }
    }

    @Override
    public void actualizarAsistencia(Asistencia asistencia) throws FachadaException {
        try {
            catalogoAsistencias.actualiza(asistencia);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error al actualizar asistencia", e);
        }
    }

    // ====================== AUSENCIAS ======================
    @Override
    public void registraAusencia(Ausencia a) throws FachadaException {
        try {
            catalogoAusencias.agrega(a);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error registro ausencia", e);
        }
    }

    @Override
    public ArrayList<Ausencia> consultaAusenciasPorEmpleado(String codigo) throws FachadaException {
        try {
            return catalogoAusencias.listaPorEmpleado(codigo);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error consulta ausencias por empleado", e);
        }
    }

    @Override
    public ArrayList<Ausencia> consultaAusencias() throws FachadaException {
        try {
            return catalogoAusencias.lista();
        } catch (PersistenciaException e) {
            throw new FachadaException("Error listar ausencias", e);
        }
    }

    @Override
    public ArrayList<Ausencia> consultaAusenciasPorMes(int mes, int anio) throws FachadaException {
        try {
            return catalogoAusencias.lista().stream()
                    .filter(a -> a.getFecha().getMes() == mes && a.getFecha().getAnio() == anio)
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error filtrar ausencias por mes", e);
        }
    }

    // ====================== VACACIONES ======================
    @Override
    public void registraVacacion(Vacacion v) throws FachadaException {
        try {
            catalogoVacaciones.agrega(v);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error registro vacación", e);
        }
    }

    @Override
    public ArrayList<Vacacion> consultaVacacionesPorEmpleado(String codigo) throws FachadaException {
        try {
            return catalogoVacaciones.listaPorEmpleado(codigo);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error consulta vacaciones por empleado", e);
        }
    }

    @Override
    public ArrayList<Vacacion> consultaVacaciones() throws FachadaException {
        try {
            return catalogoVacaciones.lista();
        } catch (PersistenciaException e) {
            throw new FachadaException("Error listar vacaciones", e);
        }
    }

    @Override
    public ArrayList<Vacacion> consultaVacacionesPorMes(int mes, int anio) throws FachadaException {
        try {
            return catalogoVacaciones.lista().stream()
                    .filter(v -> v.getFechaInicio().getMes() == mes && v.getFechaInicio().getAnio() == anio)
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error filtrar vacaciones por mes", e);
        }
    }

    // ====================== CARGOS ======================
    @Override
    public void agregaCargo(Cargo cargo) throws FachadaException {
        try {
            Cargo existe = catalogoCargos.obten(cargo);
            if (existe != null) {
                throw new FachadaException("Ya existe un cargo con ese código");
            }
        } catch (PersistenciaException ignored) {}
        try {
            catalogoCargos.agrega(cargo);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede agregar el cargo", pe);
        }
    }

    @Override
    public ArrayList<Cargo> consultaCargos() throws FachadaException {
        try {
            return catalogoCargos.lista();
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede obtener la lista de cargos", pe);
        }
    }
    @Override
    public void eliminaCargo(Cargo cargo) throws FachadaException {
        try {
            catalogoCargos.elimina(cargo);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede eliminar el cargo", pe);
        }
    }

    // ====================== HORARIOS ======================
    @Override
    public void agregaHorario(Horario h) throws FachadaException {
        try {
            catalogoHorarios.agrega(h);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error agregar horario", e);
        }
    }

    @Override
    public ArrayList<Horario> consultaHorarios() throws FachadaException {
        try {
            return catalogoHorarios.lista();
        } catch (PersistenciaException e) {
            throw new FachadaException("Error listar horarios", e);
        }
    }

    @Override
    public Horario obtenHorario(Horario h) throws FachadaException {
        try {
            return catalogoHorarios.obten(h);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error obtener horario", e);
        }
    }
    @Override
    public void eliminaHorario(Horario horario) throws FachadaException {
        try {
            catalogoHorarios.elimina(horario);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede eliminar el horario", pe);
        }
    }

    // ====================== CENTROS ======================
    @Override
    public void agregaCentro(Centro c) throws FachadaException {
        try {
            catalogoCentros.agrega(c);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error agregar centro", e);
        }
    }

    @Override
    public ArrayList<Centro> consultaCentros() throws FachadaException {
        try {
            return catalogoCentros.lista();
        } catch (PersistenciaException e) {
            throw new FachadaException("Error listar centros", e);
        }
    }

    @Override
    public Centro obtenCentro(Centro c) throws FachadaException {
        try {
            return catalogoCentros.obten(c);
        } catch (PersistenciaException e) {
            throw new FachadaException("Error obtener centro", e);
        }
    }
    @Override
    public void eliminaCentro(Centro centro) throws FachadaException {
        try {
            catalogoCentros.elimina(centro);
        } catch (PersistenciaException pe) {
            throw new FachadaException("No se puede eliminar el centro", pe);
        }
    }
}