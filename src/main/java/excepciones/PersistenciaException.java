package excepciones;

public class PersistenciaException extends Exception {
    public PersistenciaException() {
    }
    public PersistenciaException(String msj) {
        super(msj);
    }
    public PersistenciaException(String msj, Throwable causa) {
        super(msj, causa);
    }
    public PersistenciaException(Throwable causa) {
        super(causa);
    }
}