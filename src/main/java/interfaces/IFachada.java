package interfaces;

import java.util.ArrayList;
import excepciones.FachadaException;
import objetosNegocio.*;
import objetosServicio.Fecha;

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

    // Horarios
    void agregaHorario(Horario horario) throws FachadaException;
    ArrayList<Horario> consultaHorarios() throws FachadaException;
    Horario obtenHorario(Horario horario) throws FachadaException;
    void eliminaHorario(Horario horario) throws FachadaException;


    // Centros
    void agregaCentro(Centro centro) throws FachadaException;
    ArrayList<Centro> consultaCentros() throws FachadaException;
    Centro obtenCentro(Centro centro) throws FachadaException;
    void eliminaCentro(Centro centro) throws FachadaException;

    // Asistencias
    void registraAsistencia(Asistencia asistencia) throws FachadaException;
    ArrayList consultaAsistenciasPorEmpleado(String codigoEmpleado) throws FachadaException;
    // Asistencias – consultas adicionales
    ArrayList<Asistencia> consultaAsistencias() throws FachadaException;
    ArrayList<Asistencia> consultaAsistenciasPorFecha(Fecha fecha) throws FachadaException;

    // Ausencias
    void registraAusencia(Ausencia ausencia) throws FachadaException;
    ArrayList consultaAusenciasPorEmpleado(String codigoEmpleado) throws FachadaException;
    // Ausencias – consultas adicionales
    ArrayList<Ausencia> consultaAusencias() throws FachadaException;
    ArrayList<Ausencia> consultaAusenciasPorMes(int mes, int anio) throws FachadaException;

    // Vacaciones
    void registraVacacion(Vacacion vacacion) throws FachadaException;
    ArrayList consultaVacacionesPorEmpleado(String codigoEmpleado) throws FachadaException;
    // Vacaciones – consultas adicionales
    ArrayList<Vacacion> consultaVacaciones() throws FachadaException;
    ArrayList<Vacacion> consultaVacacionesPorMes(int mes, int anio) throws FachadaException;

    // Cargos
    void agregaCargo(Cargo cargo) throws FachadaException;
    ArrayList consultaCargos() throws FachadaException;
    void eliminaCargo(Cargo cargo) throws FachadaException;
}