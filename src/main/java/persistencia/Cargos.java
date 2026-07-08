package persistencia;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objetosNegocio.Cargo;
import excepciones.PersistenciaException;

public class Cargos extends AccesoAleatorio {

    public Cargos(String nomArchivo) {
        super(nomArchivo, 90); // 2(codigo) + 40(nombre) + 30(centro) + 15(tipoHorario) + margen
    }

    private Cargo leeCargo() throws IOException {
        Cargo cargo = new Cargo();
        cargo.setCodigoCargo(leeString(2));
        cargo.setNombreCargo(leeString(40));
        cargo.setCentroTrabajo(leeString(30));
        cargo.setTipoHorario(leeString(15));
        return cargo;
    }

    private void escribeCargo(Cargo cargo) throws IOException {
        escribeString(cargo.getCodigoCargo(), 2);
        escribeString(cargo.getNombreCargo(), 40);
        escribeString(cargo.getCentroTrabajo(), 30);
        escribeString(cargo.getTipoHorario(), 15);
    }

    public void agrega(Cargo cargo) throws PersistenciaException {
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
            archivo.seek(archivo.length());
            escribeCargo(cargo);
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo de cargos inexistente");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al guardar cargo");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ioe) {}
        }
    }

    public Cargo obten(Cargo cargo) throws PersistenciaException {
        Cargo cargoLeido;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                cargoLeido = leeCargo();
                if (cargo.getCodigoCargo().equals(cargoLeido.getCodigoCargo())) {
                    return cargoLeido;
                }
            }
        } catch (EOFException eofe) {
            return null;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al leer cargos");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {}
        }
    }

    public ArrayList lista() throws PersistenciaException {
        ArrayList lista = new ArrayList();
        Cargo cargo;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            return lista;
        }
        try {
            while (true) {
                cargo = leeCargo();
                lista.add(cargo);
            }
        } catch (EOFException eofe) {
            return lista;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al leer cargos");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {}
        }
    }
}