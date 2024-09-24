package Util;
// StringUtiles.java
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUtiles {

    // Método LPAD: agrega caracteres a la izquierda hasta llegar al tamaño deseado
    public static String lpad(String input, int length, char padChar) {
        if (input.length() >= length) {
            return input;
        }
        StringBuilder padded = new StringBuilder();
        while (padded.length() + input.length() < length) {
            padded.append(padChar);
        }
        padded.append(input);
        return padded.toString();
    }

    // Método RPAD: agrega caracteres a la derecha hasta llegar al tamaño deseado
    public static String rpad(String input, int length, char padChar) {
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
}
