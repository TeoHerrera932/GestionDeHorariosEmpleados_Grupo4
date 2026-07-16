package objetosNegocio;

public class Centro {
    private String codigo;
    private String direccion;

    public Centro() {}

    public Centro(String codigo, String direccion) {
        this.codigo = codigo;
        this.direccion = direccion;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return codigo + " - " + direccion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Centro c)) return false;
        return codigo != null && codigo.equals(c.codigo);
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
}