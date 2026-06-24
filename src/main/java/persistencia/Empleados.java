package persistencia;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objetosServicio.Fecha;
import objetosNegocio.Empleado;
import excepciones.PersistenciaException;

public class Empleados extends AccesoAleatorio {

    public Empleados(String nomArchivo) {
        super(nomArchivo, 365); // Tamaño ajustado sin CI
    }

    private Empleado leeEmpleado() throws IOException {
        Empleado empleado = new Empleado();
        empleado.setCodigoEmpleado(leeString(10));
        empleado.setNombres(leeString(40));
        empleado.setApellidos(leeString(40));
        empleado.setFechaNacimiento(leeFecha());
        empleado.setGenero(leeString(10));
        empleado.setEstadoCivil(leeString(20));
        empleado.setDireccion(leeString(60));
        empleado.setCorreo(leeString(40));
        empleado.setCargo(leeString(20));
        empleado.setCentroDeTrabajo(leeString(40));
        empleado.setFechaIngreso(leeFecha());
        empleado.setFechaBaja(leeFecha());
        empleado.setUsuario(leeString(20));
        empleado.setCedula(leeString(15));
        empleado.setHorario(leeString(20));
        empleado.setCelular(leeString(15));
        return empleado;
    }

    private void escribeEmpleado(Empleado empleado) throws IOException {
        escribeString(empleado.getCodigoEmpleado(), 10);
        escribeString(empleado.getNombres(), 40);
        escribeString(empleado.getApellidos(), 40);
        escribeFecha(empleado.getFechaNacimiento());
        escribeString(empleado.getGenero(), 10);
        escribeString(empleado.getEstadoCivil(), 20);
        escribeString(empleado.getDireccion(), 60);
        escribeString(empleado.getCorreo(), 40);
        escribeString(empleado.getCargo(), 20);
        escribeString(empleado.getCentroDeTrabajo(), 40);
        escribeFecha(empleado.getFechaIngreso());
        escribeFecha(empleado.getFechaBaja());
        escribeString(empleado.getUsuario(), 20);
        escribeString(empleado.getCedula(), 15);
        escribeString(empleado.getHorario(), 20);
        escribeString(empleado.getCelular(), 15);
    }


    public Empleado obten(Empleado empleado) throws PersistenciaException {
        Empleado empleadoLeido;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                empleadoLeido = leeEmpleado();
                if (empleado.equals(empleadoLeido)) {
                    return empleadoLeido;
                }
            }
        } catch (EOFException eofe) {
            return null;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    public void agrega(Empleado empleado) throws PersistenciaException {
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
            archivo.seek(archivo.length());
            escribeEmpleado(empleado);
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    public void actualiza(Empleado empleado) throws PersistenciaException {
        Empleado empleadoLeido;
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                empleadoLeido = leeEmpleado();
                if (empleado.equals(empleadoLeido)) {
                    archivo.seek(archivo.getFilePointer() - tamRegistro);
                    escribeEmpleado(empleado);
                    break;
                }
            }
        } catch (EOFException eofe) {
            throw new PersistenciaException("El empleado no existe");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    public void elimina(Empleado empleado) throws PersistenciaException {
        Empleado empleadoLeido;
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                empleadoLeido = leeEmpleado();
                if (empleado.equals(empleadoLeido)) {
                    archivo.seek(archivo.getFilePointer() - tamRegistro);
                    borraRegistro();
                    empaca();
                    break;
                }
            }
        } catch (EOFException eofe) {
            throw new PersistenciaException("El empleado no existe");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    public ArrayList lista() throws PersistenciaException {
        ArrayList lista = new ArrayList();
        Empleado empleado;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                empleado = leeEmpleado();
                lista.add(empleado);
            }
        } catch (EOFException eofe) {
            return lista;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }
}