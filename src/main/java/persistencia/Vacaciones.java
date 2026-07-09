package persistencia;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objetosServicio.Fecha;
import objetosNegocio.Vacacion;
import excepciones.PersistenciaException;

public class Vacaciones extends AccesoAleatorio {

    public Vacaciones(String nomArchivo) {
        super(nomArchivo, 80);
    }

    private Vacacion leeVacacion() throws IOException {
        Vacacion v = new Vacacion();
        v.setCodigoEmpleado(leeString(10));
        v.setFechaInicio(leeFecha());
        v.setFechaFin(leeFecha());
        v.setFechaReincorporacion(leeFecha());
        v.setEstado(leeString(15));
        return v;
    }

    private void escribeVacacion(Vacacion v) throws IOException {
        escribeString(v.getCodigoEmpleado(), 10);
        escribeFecha(v.getFechaInicio());
        escribeFecha(v.getFechaFin());
        escribeFecha(v.getFechaReincorporacion());
        escribeString(v.getEstado(), 15);
    }

    public void agrega(Vacacion vacacion) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            raf.seek(raf.length());
            escribeVacacion(vacacion);
        } catch (FileNotFoundException e) {
            throw new PersistenciaException("Archivo de vacaciones inexistente", e);
        } catch (IOException e) {
            throw new PersistenciaException("Error al registrar vacaciones", e);
        }
    }

    public ArrayList<Vacacion> listaPorEmpleado(String codigoEmpleado) throws PersistenciaException {
        ArrayList<Vacacion> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                Vacacion v = leeVacacion();
                if (v.getCodigoEmpleado().equals(codigoEmpleado)) {
                    lista.add(v);
                }
            }
        } catch (FileNotFoundException e) {
            return lista;
        } catch (EOFException e) {
            return lista;
        } catch (IOException e) {
            throw new PersistenciaException("Error al leer vacaciones por empleado", e);
        }
    }

    // ================ NUEVO MÉTODO lista() ================
    public ArrayList<Vacacion> lista() throws PersistenciaException {
        ArrayList<Vacacion> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                lista.add(leeVacacion());
            }
        } catch (FileNotFoundException e) {
            return lista;
        } catch (EOFException e) {
            return lista;
        } catch (IOException e) {
            throw new PersistenciaException("Error al listar vacaciones", e);
        }
    }
}