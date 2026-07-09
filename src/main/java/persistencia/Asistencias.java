package persistencia;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objetosServicio.Fecha;
import objetosNegocio.Asistencia;
import excepciones.PersistenciaException;

public class Asistencias extends AccesoAleatorio {

    public Asistencias(String nomArchivo) {
        super(nomArchivo, 100);
    }

    // ================== LECTURA / ESCRITURA ==================
    private Asistencia leeAsistencia() throws IOException {
        Asistencia a = new Asistencia();
        a.setCodigoEmpleado(leeString(10));
        a.setFecha(leeFecha());
        a.setHoraIngreso(leeString(8));
        a.setHoraSalida(leeString(8));
        a.setEstado(leeString(15));
        return a;
    }

    private void escribeAsistencia(Asistencia a) throws IOException {
        escribeString(a.getCodigoEmpleado(), 10);
        escribeFecha(a.getFecha());
        escribeString(a.getHoraIngreso(), 8);
        escribeString(a.getHoraSalida() != null ? a.getHoraSalida() : "", 8);
        escribeString(a.getEstado(), 15);
    }

    // ================== AGREGAR ==================
    public void agrega(Asistencia asistencia) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            raf.seek(raf.length());
            escribeAsistencia(asistencia);
        } catch (FileNotFoundException e) {
            throw new PersistenciaException("Archivo de asistencias inexistente", e);
        } catch (IOException e) {
            throw new PersistenciaException("Error al registrar asistencia", e);
        }
    }

    // ================== LISTAR POR EMPLEADO ==================
    public ArrayList<Asistencia> listaPorEmpleado(String codigoEmpleado) throws PersistenciaException {
        ArrayList<Asistencia> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                Asistencia a = leeAsistencia();
                if (a.getCodigoEmpleado().equals(codigoEmpleado)) {
                    lista.add(a);
                }
            }
        } catch (FileNotFoundException e) {
            // Archivo aún no existe → lista vacía
            return lista;
        } catch (EOFException e) {
            // Fin del archivo alcanzado
            return lista;
        } catch (IOException e) {
            throw new PersistenciaException("Error al leer asistencias por empleado", e);
        }
    }

    // ================== LISTAR TODAS (MODIFICADO) ==================
    public ArrayList<Asistencia> lista() throws PersistenciaException {
        ArrayList<Asistencia> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                lista.add(leeAsistencia());
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe → lista vacía
            return lista;
        } catch (EOFException e) {
            // Fin del archivo → retornar lo leído
            return lista;
        } catch (IOException e) {
            throw new PersistenciaException("Error al leer asistencias", e);
        }
    }
}