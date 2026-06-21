package persistencia;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import objetosNegocio.Usuario;
import excepciones.PersistenciaException;

public class Usuarios extends AccesoAleatorio {

    public Usuarios(String nomArchivo) {
        super(nomArchivo, 80); // cargo(15) + usuario(20) + contrasena(30) + margen
    }

    private Usuario leeUsuario() throws IOException {
        Usuario usuario = new Usuario();
        usuario.setCargo(leeString(15));
        usuario.setUsuario(leeString(20));
        usuario.setContrasena(leeString(30));
        return usuario;
    }

    private void escribeUsuario(Usuario usuario) throws IOException {
        escribeString(usuario.getCargo(), 15);
        escribeString(usuario.getUsuario(), 20);
        escribeString(usuario.getContrasena(), 30);
    }

    public Usuario obten(Usuario usuario) throws PersistenciaException {
        Usuario usuarioLeido;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                usuarioLeido = leeUsuario();
                if (usuario.equals(usuarioLeido)) {
                    return usuarioLeido;
                }
            }
        } catch (EOFException eofe) {
            return null;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    public void agrega(Usuario usuario) throws PersistenciaException {
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
            archivo.seek(archivo.length());
            escribeUsuario(usuario);
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                if (archivo != null) archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    public void actualiza(Usuario usuario) throws PersistenciaException {
        Usuario usuarioLeido;
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                usuarioLeido = leeUsuario();
                if (usuario.equals(usuarioLeido)) {
                    archivo.seek(archivo.getFilePointer() - tamRegistro);
                    escribeUsuario(usuario);
                    break;
                }
            }
        } catch (EOFException eofe) {
            throw new PersistenciaException("El usuario no existe");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    public void elimina(Usuario usuario) throws PersistenciaException {
        Usuario usuarioLeido;
        try {
            archivo = new RandomAccessFile(nomArchivo, "rw");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                usuarioLeido = leeUsuario();
                if (usuario.equals(usuarioLeido)) {
                    archivo.seek(archivo.getFilePointer() - tamRegistro);
                    borraRegistro();
                    empaca();
                    break;
                }
            }
        } catch (EOFException eofe) {
            throw new PersistenciaException("El usuario no existe");
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    public ArrayList lista() throws PersistenciaException {
        ArrayList lista = new ArrayList();
        Usuario usuario;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                usuario = leeUsuario();
                lista.add(usuario);
            }
        } catch (EOFException eofe) {
            return lista;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }

    // Método útil para login
    public Usuario obtenPorUsuario(String nombreUsuario) throws PersistenciaException {
        Usuario usuarioLeido;
        try {
            archivo = new RandomAccessFile(nomArchivo, "r");
        } catch (FileNotFoundException fnfe) {
            throw new PersistenciaException("Archivo inexistente");
        }
        try {
            while (true) {
                usuarioLeido = leeUsuario();
                if (nombreUsuario.equals(usuarioLeido.getUsuario())) {
                    return usuarioLeido;
                }
            }
        } catch (EOFException eofe) {
            return null;
        } catch (IOException ioe) {
            throw new PersistenciaException("Error al acceder al archivo");
        } finally {
            try {
                archivo.close();
            } catch (IOException ioe) {
                throw new PersistenciaException("Error al cerrar el archivo");
            }
        }
    }
}