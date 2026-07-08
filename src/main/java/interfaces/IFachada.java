package interfaces;

import java.util.ArrayList;
import excepciones.FachadaException;
import objetosNegocio.Asistencia;
import objetosNegocio.Ausencia;
import objetosNegocio.Cargo;
import objetosNegocio.Empleado;
import objetosNegocio.Usuario;
import objetosNegocio.Vacacion;

public interface IFachada {

    // Empleados
    Empleado obten(Empleado empleado) throws FachadaException;
    void agrega(Empleado empleado) throws FachadaException;
    void actualiza(Empleado empleado) throws FachadaException;
    void elimina(Empleado empleado) throws FachadaException;
    ArrayList consultaEmpleados() throws FachadaException;

    // Usuarios
    Usuario obten(Usuario usuario) throws FachadaException;
    void agrega(Usuario usuario) throws FachadaException;
    void actualiza(Usuario usuario) throws FachadaException;
    void elimina(Usuario usuario) throws FachadaException;
    ArrayList consultaUsuarios() throws FachadaException;
    Usuario obtenPorUsuario(String nombreUsuario) throws FachadaException;

    // Asistencias
    void registraAsistencia(Asistencia asistencia) throws FachadaException;
    ArrayList consultaAsistenciasPorEmpleado(String codigoEmpleado) throws FachadaException;

    // Ausencias
    void registraAusencia(Ausencia ausencia) throws FachadaException;
    ArrayList consultaAusenciasPorEmpleado(String codigoEmpleado) throws FachadaException;

    // Vacaciones
    void registraVacacion(Vacacion vacacion) throws FachadaException;
    ArrayList consultaVacacionesPorEmpleado(String codigoEmpleado) throws FachadaException;

    // Cargos
    void agregaCargo(Cargo cargo) throws FachadaException;
    ArrayList consultaCargos() throws FachadaException;
}