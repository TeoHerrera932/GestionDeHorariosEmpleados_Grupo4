package persistencia;

import java.io.IOException;
import java.io.RandomAccessFile;
import objetosServicio.Fecha;

public abstract class AccesoAleatorio {

    protected RandomAccessFile archivo;
    protected String nomArchivo;
    protected int tamRegistro;

    public AccesoAleatorio(String nomArchivo, int tamRegistro) {
        this.nomArchivo = nomArchivo;
        this.tamRegistro = tamRegistro;
    }

    // ====================== MÉTODOS PARA STRINGS ======================

    protected String leeString(int tam) throws IOException {
        char[] cadena = new char[tam];
        for (int i = 0; i < tam; i++) {
            cadena[i] = archivo.readChar();
        }
        return new String(cadena).trim();
    }

    protected void escribeString(String cadena, int tam) throws IOException {
        if (cadena == null) {
            cadena = "";
        }
        StringBuffer buffer = new StringBuffer(cadena);
        buffer.setLength(tam);
        archivo.writeChars(buffer.toString());
    }

    // ====================== MÉTODOS PARA FECHA ======================

    protected Fecha leeFecha() throws IOException {
        int dia = archivo.readInt();
        int mes = archivo.readInt();
        int anio = archivo.readInt();
        if (dia == 0 && mes == 0 && anio == 0) {
            return null;
        }
        return new Fecha(dia, mes, anio);
    }

    protected void escribeFecha(Fecha fecha) throws IOException {
        if (fecha == null) {
            archivo.writeInt(0);
            archivo.writeInt(0);
            archivo.writeInt(0);
        } else {
            archivo.writeInt(fecha.getDia());
            archivo.writeInt(fecha.getMes());
            archivo.writeInt(fecha.getAnio());
        }
    }

    // ====================== MÉTODOS PARA BORRADO LÓGICO ======================

    protected void borraRegistro() throws IOException {
        archivo.writeChar('B');  // Marca como borrado
    }

    protected void empaca() throws IOException {
        // Este método normalmente reorganiza el archivo eliminando registros marcados como borrados
        // Por simplicidad en proyectos académicos, a veces se deja vacío o se implementa básico
    }

    // Getter del tamaño del registro
    public int getTamRegistro() {
        return tamRegistro;
    }
}