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
        super(nomArchivo, 100); // Ajusta si es necesario
    }

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

    public void agrega(Asistencia asistencia) throws PersistenciaException {
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
            archivo.seek(archivo.length());
            escribeAsistencia(asistencia);
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo de asistencias inexistente");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al registrar asistencia");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar archivo");
            }
        }
    }

    public ArrayList listaPorEmpleado(String codigoEmpleado) throws PersistenciaException {
        ArrayList lista = new ArrayList();
        Asistencia asistencia;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            return lista; // Archivo aún no existe
        }
        try {
            while (true) {
                asistencia = leeAsistencia();
                if (asistencia.getCodigoEmpleado().equals(codigoEmpleado)) {
                    lista.add(asistencia);
                }
            }
        } catch (EOFException eofe) {
            return lista;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al leer asistencias");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ioe) {}
        }
    }

    public ArrayList lista() throws PersistenciaException {
        ArrayList lista = new ArrayList();
        Asistencia asistencia;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            return lista;
        }
        try {
            while (true) {
                asistencia = leeAsistencia();
                lista.add(asistencia);
            }
        } catch (EOFException eofe) {
            return lista;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al leer asistencias");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ioe) {}
        }
    }
}