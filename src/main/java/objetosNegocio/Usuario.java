package objetosNegocio;

public class Usuario {

    private String cargo;      // admin, soporte, empleado
    private String usuario;
    private String contrasena;

    public Usuario() {
    }

    // Constructor principal
    public Usuario(String cargo, String usuario, String contrasena) {
        this.cargo = cargo;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    // Constructor con solo nombre de usuario (útil para búsquedas y login)
    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return usuario + " (" + cargo + ")";
    }
}