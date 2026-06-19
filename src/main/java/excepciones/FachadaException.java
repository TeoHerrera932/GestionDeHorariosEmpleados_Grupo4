package excepciones;

public class FachadaException extends Exception {
    public FachadaException() {
    }
    public FachadaException(String msj) {
        super(msj);
    }
    public FachadaException(String msj, Throwable causa) {
        super(msj, causa);
    }
    public FachadaException(Throwable causa) {
        super(causa);
    }
}