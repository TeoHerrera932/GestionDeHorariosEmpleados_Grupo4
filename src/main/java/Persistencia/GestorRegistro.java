package persistencia;

import excepciones.EmpleadoNoEncontradoException;
import excepciones.PersistenciaException;
import excepciones.RegistroException;
import objetosNegocio.Empleado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Interfaz que define las operaciones del gestor de registros.
 */
public interface GestorRegistro {

    void agregarEmpleado(Empleado empleado);

    Empleado obtenerEmpleadoPorId(String id) throws EmpleadoNoEncontradoException;

    void registrarEntrada(Empleado empleado, LocalDate fecha, LocalTime horaEntrada) throws RegistroException;

    void registrarSalida(String empleadoId, LocalDate fecha, LocalTime horaSalida) throws PersistenciaException;

    void mostrarRegistrosEmpleado(String id) throws EmpleadoNoEncontradoException;

    ArrayList<RegistroHora> getRegistros();
}
