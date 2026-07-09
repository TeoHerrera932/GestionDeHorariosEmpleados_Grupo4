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
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            raf.seek(raf.length());
            escribeCargo(cargo);
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo de cargos inexistente", fnfe);
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al guardar cargo", ioe);
        }
    }

    public Cargo obten(Cargo cargo) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                Cargo leido = leeCargo();
                if (leido.getCodigoCargo().equals(cargo.getCodigoCargo())) {
                    return leido;
                }
            }
        } catch (EOFException eofe) {
            return null;
        } catch (FileNotFoundException fnfe) {
            return null;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al leer cargos", ioe);
        }
    }

    public ArrayList<Cargo> lista() throws PersistenciaException {
        ArrayList<Cargo> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                lista.add(leeCargo());
            }
        } catch (FileNotFoundException fnfe) {
            return lista;
        } catch (EOFException eofe) {
            return lista;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al leer cargos", ioe);
        }
    }

    // ========== MÉTODO ELIMINAR (CORREGIDO) ==========
    public void elimina(Cargo cargo) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            while (true) {
                long pos = raf.getFilePointer();
                Cargo leido = leeCargo();   // <--- CORREGIDO: antes era leerCargo()
                if (leido.getCodigoCargo().equals(cargo.getCodigoCargo())) {
                    raf.seek(pos);
                    borraRegistro();  // Marca como borrado
                    empaca();         // Reorganiza el archivo
                    return;
                }
            }
        } catch (EOFException eofe) {
            throw new PersistenciaException("Cargo no encontrado");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo de cargos inexistente", fnfe);
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al eliminar cargo", ioe);
        }
    }
}