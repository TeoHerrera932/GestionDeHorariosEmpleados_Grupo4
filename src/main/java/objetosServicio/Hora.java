package objetosServicio;

public class Hora {

    private int hora;
    private int minuto;

    public Hora() {
        this(0, 0);
    }

    public Hora(int hora, int minuto) {
        setHora(hora);
        setMinuto(minuto);
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = (hora >= 0 && hora <= 23) ? hora : 0;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = (minuto >= 0 && minuto <= 59) ? minuto : 0;
    }

    public String toString() {
        return String.format("%02d:%02d", hora, minuto);
    }

    // Método útil para comparar horas
    public boolean esMayorQue(Hora otra) {
        if (this.hora != otra.hora) return this.hora > otra.hora;
        return this.minuto > otra.minuto;
    }

    public static Hora ahora() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        return new Hora(cal.get(java.util.Calendar.HOUR_OF_DAY),
                cal.get(java.util.Calendar.MINUTE));
    }
}