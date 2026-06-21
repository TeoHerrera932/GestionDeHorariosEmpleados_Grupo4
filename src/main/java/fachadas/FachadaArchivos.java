package fachadas;

import java.util.ArrayList;
import excepciones.FachadaException;
import excepciones.PersistenciaException;
import interfaces.IFachada;
import objetosNegocio.Empleado;
import objetosNegocio.Usuario;
import persistencia.Empleados;
import persistencia.Usuarios;

public class FachadaArchivos implements IFachada {

    private Empleados catalogoEmpleados;
    private Usuarios catalogoUsuarios;

    public FachadaArchivos() {
        catalogoEmpleados = new Empleados("empleados.dat");
        catalogoUsuarios = new Usuarios("usuarios.dat");
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
            Empleado empleadoBuscado = catalogoEmpleados.obten(empleado);
            if (empleadoBuscado != null) {
                throw new FachadaException("Empleado repetido");
            }
        } catch (PersistenciaException pe) {
            // Archivo no existe aún
        }
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
    public ArrayList consultaEmpleados() throws FachadaException {
        try {
            return new ArrayList(catalogoEmpleados.lista());
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
            Usuario usuarioBuscado = catalogoUsuarios.obten(usuario);
            if (usuarioBuscado != null) {
                throw new FachadaException("Usuario repetido");
            }
        } catch (PersistenciaException pe) {
            // Archivo no existe aún
        }
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
    public ArrayList consultaUsuarios() throws FachadaException {
        try {
            return new ArrayList(catalogoUsuarios.lista());
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
}