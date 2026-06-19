package objetosServicio;

public class Fecha {
    private int dia;
    private int mes;
    private int anio;

    public Fecha() {}

    /**
     * Constructor con parámetros
     */
    public Fecha(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }

    @Override
    public String toString() {
        return dia + "/" + mes + "/" + anio;
    }
}