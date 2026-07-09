package persistencia;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objetosNegocio.Horario;
import excepciones.PersistenciaException;

public class Horarios extends AccesoAleatorio {

    public Horarios(String nomArchivo) {
        super(nomArchivo, 70);
    }

    private Horario leerHorario() throws IOException {
        Horario h = new Horario();
        h.setCodigo(leeString(10));
        h.setNombre(leeString(20));
        h.setHoraInicio(leeString(8));
        h.setHoraFin(leeString(8));
        return h;
    }

    private void escribirHorario(Horario h) throws IOException {
        escribeString(h.getCodigo(), 10);
        escribeString(h.getNombre(), 20);
        escribeString(h.getHoraInicio(), 8);
        escribeString(h.getHoraFin(), 8);
    }

    public void agrega(Horario h) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            raf.seek(raf.length());
            escribirHorario(h);
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar horario", e);
        }
    }

    public Horario obten(Horario h) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                Horario leido = leerHorario();
                if (leido.equals(h)) return leido;
            }
        } catch (EOFException e) {
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new PersistenciaException("Error al obtener horario", e);
        }
    }

    // ================ NUEVO MÉTODO lista() ================
    public ArrayList<Horario> lista() throws PersistenciaException {
        ArrayList<Horario> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "r")) {
            archivo = raf;
            while (true) {
                lista.add(leerHorario());
            }
        } catch (FileNotFoundException e) {
            return lista;
        } catch (EOFException e) {
            return lista;
        } catch (IOException e) {
            throw new PersistenciaException("Error al listar horarios", e);
        }
    }
    public void elimina(Horario horario) throws PersistenciaException {
        try (RandomAccessFile raf = new RandomAccessFile(nomArchivo, "rw")) {
            archivo = raf;
            while (true) {
                long pos = raf.getFilePointer();
                Horario leido = leerHorario();
                if (leido.equals(horario)) {
                    raf.seek(pos);
                    borraRegistro();
                    empaca();
                    return;
                }
            }
        } catch (EOFException e) {
            throw new PersistenciaException("Horario no encontrado");
        } catch (FileNotFoundException e) {
            throw new PersistenciaException("Archivo de horarios inexistente");
        } catch (IOException e) {
            throw new PersistenciaException("Error al eliminar horario", e);
        }
    }
}