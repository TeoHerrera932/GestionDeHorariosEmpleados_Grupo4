/**
 * Excepción lanzada cuando no se encuentra un registro de salida pendiente.
 */
public class RegistroNoEncontradoException extends RegistroException {

    public RegistroNoEncontradoException(String message) {
        super(message);
    }
}
