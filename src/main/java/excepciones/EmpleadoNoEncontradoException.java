package excepciones;

/**
 * Excepción lanzada cuando no se encuentra un empleado.
 */
public class EmpleadoNoEncontradoException extends RegistroException {

    public EmpleadoNoEncontradoException(String message) {
        super(message);
    }
}
