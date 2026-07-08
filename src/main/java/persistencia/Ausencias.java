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
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
            archivo.seek(archivo.length());
            escribeAusencia(ausencia);
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo de ausencias inexistente");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al registrar ausencia");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ignored) {}
        }
    }

    public ArrayList listaPorEmpleado(String codigoEmpleado) throws PersistenciaException {
        ArrayList lista = new ArrayList();
        Ausencia a;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            return lista;
        }
        try {
            while (true) {
                a = leeAusencia();
                if (a.getCodigoEmpleado().equals(codigoEmpleado)) {
                    lista.add(a);
                }
            }
        } catch (EOFException eofe) {
            return lista;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al leer ausencias");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ignored) {}
        }
    }
}