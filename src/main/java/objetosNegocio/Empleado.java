package objetosNegocio;

import objetosServicio.Fecha;

public class Empleado {

    private String codigoEmpleado;
    private String nombres;
    private String apellidos;
    private Fecha fechaNacimiento;
    private String genero;
    private String estadoCivil;
    private String direccion;
    private String correo;
    private String cargo;
    private String centroDeTrabajo;
    private Fecha fechaIngreso;
    private Fecha fechaBaja;
    private String usuario;
    private String cedula;
    private String horario;
    private String celular;

    public Empleado() {
    }

    // Constructor principal (sin CI)
    public Empleado(String codigoEmpleado, String nombres, String apellidos,
                    Fecha fechaNacimiento, String genero, String estadoCivil,
                    String direccion, String correo, String cargo,
                    String centroDeTrabajo, Fecha fechaIngreso, Fecha fechaBaja,
                    String usuario, String cedula, String horario, String celular) {

        this.codigoEmpleado = codigoEmpleado;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.estadoCivil = estadoCivil;
        this.direccion = direccion;
        this.correo = correo;
        this.cargo = cargo;
        this.centroDeTrabajo = centroDeTrabajo;
        this.fechaIngreso = fechaIngreso;
        this.fechaBaja = fechaBaja;
        this.usuario = usuario;
        this.cedula = cedula;
        this.horario = horario;
        this.celular = celular;
    }

    public Empleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public Fecha getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Fecha fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getCentroDeTrabajo() { return centroDeTrabajo; }
    public void setCentroDeTrabajo(String centroDeTrabajo) { this.centroDeTrabajo = centroDeTrabajo; }

    public Fecha getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Fecha fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public Fecha getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(Fecha fechaBaja) { this.fechaBaja = fechaBaja; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    @Override
    public String toString() {
        return codigoEmpleado + " - " + nombres + " " + apellidos + " | " + cargo;
    }
}