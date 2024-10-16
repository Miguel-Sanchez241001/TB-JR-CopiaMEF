package Util;
// StringUtiles.java
import model.Cabecera;
import model.MefTrama;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StringUtiles {

    // Método LPAD: agrega caracteres a la izquierda hasta llegar al tamaño deseado
    public static String lpad(String input, int length, char padChar) {
        // Si el input es nulo, lo tratamos como una cadena vacía
        if (input == null) {
            input = "";
        }

        // Si la longitud del input es mayor o igual a la longitud requerida, devolver el input tal como está
        if (input.length() >= length) {
            return input;
        }

        // Crear el StringBuilder para el relleno
        StringBuilder padded = new StringBuilder();

        // Añadir el carácter de relleno hasta alcanzar la longitud requerida
        while (padded.length() + input.length() < length) {
            padded.append(padChar);
        }

        // Añadir el input al final del relleno
        padded.append(input);

        // Devolver la cadena con el relleno
        return padded.toString();
    }


    // Método RPAD: agrega caracteres a la derecha hasta llegar al tamaño deseado
    public static String rpad(String input, int length, char padChar) {

        if (input == null) {
            input = "";
        }
        if (input.length() >= length) {
            return input;
        }
        StringBuilder padded = new StringBuilder(input);
        while (padded.length() < length) {
            padded.append(padChar);
        }
        return padded.toString();
    }

    // Método para convertir fecha yyyy-MM-dd a yyyyMMdd
    public static String formatDateToAAAAMMDD(String date) throws Exception {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");
        Date parsedDate = inputFormat.parse(date);
        return outputFormat.format(parsedDate);
    }

    // Método para convertir fecha yyyy-MM-dd a yyyyMMddHHmmss
    public static String formatDateToAAAAMMDDHHMISS(String date) throws Exception {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date parsedDate = inputFormat.parse(date);
        return outputFormat.format(parsedDate);
    }

    // Método para generar un número random utilizando UUID
    public static String generateRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    // Método para generar un número aleatorio de longitud n
    public static String generateRandomNumber(int length) {
        StringBuilder result = new StringBuilder();
        while (result.length() < length) {
            result.append((int) (Math.random() * 10)); // Genera un número entre 0 y 9
        }
        return result.toString();
    }


    public static StringBuilder GenerarTrama(List<MefTrama> objetos) throws Exception {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbPrincipal = new StringBuilder();
        Double total = 0.0;

        for (MefTrama trama : objetos) {
            total=total+ Double.parseDouble(trama.getMontoTotalGirado());
            String line = trama.generarTrama();
            sb.append(line);

        }
        Cabecera cabecera = Cabecera.builder()
                .nLote(generateRandomNumber(3))
                .fecha(formatDateToAAAAMMDDHHMISS(FechaActual()))
                .prefijo("H")
                .registros(String.valueOf(objetos.size()))
                .sumaImporte(String.valueOf(total).replace(".",""))
                .build();
        sbPrincipal.append(cabecera.generarTrama() );
        sbPrincipal.append(sb.toString());
        return  sbPrincipal ;
    }


    public static String FechaActual() throws Exception {
        LocalDateTime fechaActual = LocalDateTime.now();

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formatter);

          return fechaFormateada;
    }
}
