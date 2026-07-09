package persistencia;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objetosNegocio.Centro;
import excepciones.PersistenciaException;

public class Centros extends AccesoAleatorio {

    public Centros(String nomArchivo) {
        super(nomArchivo, 60);
    }

    private Centro leerCentro() throws IOException {
        Centro c = new Centro();
        c.setCodigo(leeString(10));
        c.setDireccion(leeString(50));
        return c;
    }

    private void escribirCentro(Centro c) throws IOException {
        escribeString(c.getCodigo(), 10);
        escribeString(c.getDireccion(), 50);
    }

    public void agrega(Centro c) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            raf.seek(raf.length());
            escribirCentro(c);
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar centro", e);
        }
    }

    public Centro obten(Centro c) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                Centro leido = leerCentro();
                if (leido.equals(c)) return leido;
            }
        } catch (EOFException e) {
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new PersistenciaException("Error al obtener centro", e);
        }
    }

    // ================ NUEVO MÉTODO lista() ================
    public ArrayList<Centro> lista() throws PersistenciaException {
        ArrayList<Centro> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                lista.add(leerCentro());
            }
        } catch (FileNotFoundException e) {
            return lista;
        } catch (EOFException e) {
            return lista;
        } catch (IOException e) {
            throw new PersistenciaException("Error al listar centros", e);
        }
    }
    public void elimina(Centro centro) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            while (true) {
                long pos = raf.getFilePointer();
                Centro leido = leerCentro();
                if (leido.equals(centro)) {
                    raf.seek(pos);
                    borraRegistro();
                    empaca();
                    return;
                }
            }
        } catch (EOFException e) {
            throw new PersistenciaException("Centro no encontrado");
        } catch (FileNotFoundException e) {
            throw new PersistenciaException("Archivo de centros inexistente");
        } catch (IOException e) {
            throw new PersistenciaException("Error al eliminar centro", e);
        }
    }
}