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
        super(nomArchivo, 80); // Ajusta según tus necesidades
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
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
            archivo.seek(archivo.length());
            escribeVacacion(vacacion);
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo de vacaciones inexistente");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al registrar vacaciones");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ignored) {}
        }
    }

    public ArrayList listaPorEmpleado(String codigoEmpleado) throws PersistenciaException {
        ArrayList lista = new ArrayList();
        Vacacion v;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            return lista;
        }
        try {
            while (true) {
                v = leeVacacion();
                if (v.getCodigoEmpleado().equals(codigoEmpleado)) {
                    lista.add(v);
                }
            }
        } catch (EOFException eofe) {
            return lista;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al leer vacaciones");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ignored) {}
        }
    }
}