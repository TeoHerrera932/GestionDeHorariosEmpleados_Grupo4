package persistencia;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objetosServicio.Fecha;
import objetosNegocio.Empleado;
import objetosNegocio.Cargo;
import excepciones.PersistenciaException;

public class Empleados extends AccesoAleatorio {

    public Empleados(String nomArchivo) {
        super(nomArchivo, 395);
    }

    // ========== LECTURA (mismo orden que la tabla) ==========
    private Empleado leeEmpleado() throws IOException {
        Empleado empleado = new Empleado();

        empleado.setCodigoEmpleado(leeString(10));   // 1. Código
        empleado.setCedula(leeString(15));           // 2. Cédula
        empleado.setNombres(leeString(40));          // 3. Nombres
        empleado.setApellidos(leeString(40));        // 4. Apellidos
        empleado.setGenero(leeString(10));           // 5. Género
        empleado.setEstadoCivil(leeString(20));      // 6. Estado Civil
        empleado.setDireccion(leeString(60));        // 7. Dirección
        empleado.setCorreo(leeString(40));           // 8. Correo

        // 9. Cargo (objeto)
        Cargo cargo = new Cargo();
        cargo.setNombreCargo(leeString(30));         // 9a. Nombre del cargo
        cargo.setCentroTrabajo(leeString(40));       // 10. Centro Trabajo
        empleado.setCargo(cargo);

        empleado.setFechaIngreso(leeFecha());        // 11. Fecha Ingreso
        empleado.setFechaBaja(leeFecha());           // 12. Fecha Baja
        empleado.setUsuario(leeString(20));          // 13. Usuario
        empleado.setHorario(leeString(20));          // 14. Horario
        empleado.setCelular(leeString(15));          // 15. Celular

        // Nota: FechaNacimiento no está en la tabla, pero se guarda igual.
        // Si quieres mostrarlo, añádelo como columna extra.
        // Lo dejamos al final para no romper el orden.
        empleado.setFechaNacimiento(leeFecha());

        return empleado;
    }

    // ========== ESCRITURA (mismo orden) ==========
    private void escribeEmpleado(Empleado empleado) throws IOException {
        escribeString(empleado.getCodigoEmpleado(), 10);
        escribeString(empleado.getCedula(), 15);
        escribeString(empleado.getNombres(), 40);
        escribeString(empleado.getApellidos(), 40);
        escribeString(empleado.getGenero(), 10);
        escribeString(empleado.getEstadoCivil(), 20);
        escribeString(empleado.getDireccion(), 60);
        escribeString(empleado.getCorreo(), 40);

        // Cargo
        Cargo cargo = empleado.getCargo();
        escribeString(cargo != null ? cargo.getNombreCargo() : "", 30);
        escribeString(cargo != null ? cargo.getCentroTrabajo() : "", 40);

        escribeFecha(empleado.getFechaIngreso());
        escribeFecha(empleado.getFechaBaja());
        escribeString(empleado.getUsuario(), 20);
        escribeString(empleado.getHorario(), 20);
        escribeString(empleado.getCelular(), 15);

        // FechaNacimiento (opcional)
        escribeFecha(empleado.getFechaNacimiento());
    }

    // ========== MÉTODOS PÚBLICOS ==========
    public Empleado obten(Empleado empleado) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                Empleado leido = leeEmpleado();
                if (leido.getCodigoEmpleado().equals(empleado.getCodigoEmpleado())) {
                    return leido;
                }
            }
        } catch (EOFException e) {
            return null;
        } catch (FileNotFoundException e) {
            throw new PersistenciaException("Archivo inexistente", e);
        } catch (IOException e) {
            throw new PersistenciaException("Error al leer empleado", e);
        }
    }

    public void agrega(Empleado empleado) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            raf.seek(raf.length());
            escribeEmpleado(empleado);
        } catch (IOException e) {
            throw new PersistenciaException("Error al agregar empleado", e);
        }
    }

    public void actualiza(Empleado empleado) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            while (true) {
                long pos = raf.getFilePointer();
                Empleado leido = leeEmpleado();
                if (leido.getCodigoEmpleado().equals(empleado.getCodigoEmpleado())) {
                    raf.seek(pos);
                    escribeEmpleado(empleado);
                    return;
                }
            }
        } catch (EOFException e) {
            throw new PersistenciaException("Empleado no encontrado", e);
        } catch (IOException e) {
            throw new PersistenciaException("Error al actualizar empleado", e);
        }
    }

    public void elimina(Empleado empleado) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            while (true) {
                long pos = raf.getFilePointer();
                Empleado leido = leeEmpleado();
                if (leido.getCodigoEmpleado().equals(empleado.getCodigoEmpleado())) {
                    raf.seek(pos);
                    borraRegistro();
                    empaca();
                    return;
                }
            }
        } catch (EOFException e) {
            throw new PersistenciaException("Empleado no encontrado", e);
        } catch (IOException e) {
            throw new PersistenciaException("Error al eliminar empleado", e);
        }
    }

    public ArrayList<Empleado> lista() throws PersistenciaException {
        ArrayList<Empleado> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                lista.add(leeEmpleado());
            }
        } catch (FileNotFoundException e) {
            return lista;
        } catch (EOFException e) {
            return lista;
        } catch (IOException e) {
            throw new PersistenciaException("Error al listar empleados", e);
        }
    }
}