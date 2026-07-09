package persistencia;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;//Necesario para que el codigo no salga en chino
//Mantiene los datos inmutables junto con UTF 8
import objetosServicio.Fecha;

public abstract class AccesoAleatorio {

    protected RandomAccessFile archivo;
    protected String nomArchivo;
    protected int tamRegistro;

    public AccesoAleatorio(String nomArchivo, int tamRegistro) {
        this.nomArchivo = nomArchivo;
        this.tamRegistro = tamRegistro;
    }

    // ====================== MÉTODOS PARA STRINGS modificacion con UTF-8 ======================

    protected String leeString(int tam) throws IOException {
        byte[] bytes = new byte[tam];
        archivo.readFully(bytes);
        int len = 0;
        while (len < bytes.length && bytes[len] != 0) len++;
        return new String(bytes, 0, len, StandardCharsets.UTF_8);
    }

    protected void escribeString(String cadena, int tam) throws IOException {
        if (cadena == null) cadena = "";
        byte[] bytes = cadena.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > tam) {
            byte[] trunc = new byte[tam];
            System.arraycopy(bytes, 0, trunc, 0, tam);
            archivo.write(trunc);
        } else {
            archivo.write(bytes);
            for (int i = bytes.length; i < tam; i++) {
                archivo.write(0);
            }
        }
    }

    // ====================== MÉTODOS PARA FECHA ======================

    protected Fecha leeFecha() throws IOException {
        int dia = archivo.readInt();
        int mes = archivo.readInt();
        int anio = archivo.readInt();
        return (dia == 0 && mes == 0 && anio == 0) ? null : new Fecha(dia, mes, anio);
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
        archivo.writeChar('B');
    }

    protected void empaca() throws IOException {
        // Opcional: reescribir archivo sin registros marcados como 'B'
    }

    public int getTamRegistro() {
        return tamRegistro;
    }
}