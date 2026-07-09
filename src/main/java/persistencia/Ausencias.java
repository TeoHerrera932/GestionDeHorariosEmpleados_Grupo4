package persistencia;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objetosServicio.Fecha;
import objetosNegocio.Ausencia;
import excepciones.PersistenciaException;

public class Ausencias extends AccesoAleatorio {

    public Ausencias(String nomArchivo) {
        super(nomArchivo, 70);
    }

    private Ausencia leeAusencia() throws IOException {
        Ausencia a = new Ausencia();
        a.setCodigoEmpleado(leeString(10));
        a.setFecha(leeFecha());
        a.setMotivo(leeString(40));
        a.setEstado(leeString(15));
        return a;
    }

    private void escribeAusencia(Ausencia a) throws IOException {
        escribeString(a.getCodigoEmpleado(), 10);
        escribeFecha(a.getFecha());
        escribeString(a.getMotivo(), 40);
        escribeString(a.getEstado(), 15);
    }

    public void agrega(Ausencia ausencia) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            raf.seek(raf.length());
            escribeAusencia(ausencia);
        } catch (FileNotFoundException e) {
            throw new PersistenciaException("Archivo de ausencias inexistente", e);
        } catch (IOException e) {
            throw new PersistenciaException("Error al registrar ausencia", e);
        }
    }

    public ArrayList<Ausencia> listaPorEmpleado(String codigoEmpleado) throws PersistenciaException {
        ArrayList<Ausencia> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                Ausencia a = leeAusencia();
                if (a.getCodigoEmpleado().equals(codigoEmpleado)) {
                    lista.add(a);
                }
            }
        } catch (FileNotFoundException e) {
            return lista;
        } catch (EOFException e) {
            return lista;
        } catch (IOException e) {
            throw new PersistenciaException("Error al leer ausencias por empleado", e);
        }
    }

    // ================ NUEVO MÉTODO lista() ================
    public ArrayList<Ausencia> lista() throws PersistenciaException {
        ArrayList<Ausencia> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                lista.add(leeAusencia());
            }
        } catch (FileNotFoundException e) {
            return lista;
        } catch (EOFException e) {
            return lista;
        } catch (IOException e) {
            throw new PersistenciaException("Error al listar ausencias", e);
        }
    }
}