package objetosNegocio;// ObjetosNegocio.Usuario.java
/**
 * Clase abstracta que representa un usuario del sistema.
 * Sirve como base para objetosNegocio.Empleado mediante herencia.
 */
public abstract class Usuario {

    private String id;
    private String nombre;
    private String apellido;
    private String usuario;
    private String contrasena;

    /**
     * Constructor de ObjetosNegocio.Usuario
     */
    public Usuario(String id, String nombre, String apellido, String usuario, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /**
     * Devuelve el tipo de usuario.
     */
    public abstract String getTipoUsuario();

    // ==================== GETTERS Y SETTERS ====================

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}