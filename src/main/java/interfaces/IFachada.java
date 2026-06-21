package interfaces;

import java.util.ArrayList;
import excepciones.FachadaException;
import objetosNegocio.Empleado;
import objetosNegocio.Usuario;

public interface IFachada {

    // ====================== EMPLEADO ======================
    public Empleado obten(Empleado empleado) throws FachadaException;
    public void agrega(Empleado empleado) throws FachadaException;
    public void actualiza(Empleado empleado) throws FachadaException;
    public void elimina(Empleado empleado) throws FachadaException;
    public ArrayList consultaEmpleados() throws FachadaException;

    // ====================== USUARIO ======================
    public Usuario obten(Usuario usuario) throws FachadaException;
    public void agrega(Usuario usuario) throws FachadaException;
    public void actualiza(Usuario usuario) throws FachadaException;
    public void elimina(Usuario usuario) throws FachadaException;
    public ArrayList consultaUsuarios() throws FachadaException;

    // Método especial para Login
    public Usuario obtenPorUsuario(String nombreUsuario) throws FachadaException;
}