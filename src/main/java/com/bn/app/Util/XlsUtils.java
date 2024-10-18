package com.bn.app.Util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;

public class XlsUtils {

    // Método para obtener el valor de una celda como String
    public static String getCellValue(Row row, int cellIndex) {
        // Obtenemos la celda en base al índice
        Cell cell = row.getCell(cellIndex);

        // Inicializamos variables para almacenar el valor de la celda y el tipo de la celda
        String cellValue = "";
        String cellTypeDescription = "";

        // Si la celda está vacía, retornamos una cadena vacía
        if (cell == null) {
            cellValue = "";
            cellTypeDescription = "CELDA VACÍA";
        } else {
            // Verificamos el tipo de la celda y guardamos el valor correspondiente
            switch (cell.getCellType()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    cellTypeDescription = "STRING";
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue().toString(); // Para celdas de tipo fecha
                        cellTypeDescription = "FECHA";
                    } else {
                        BigDecimal bd = BigDecimal.valueOf(cell.getNumericCellValue());
                        cellValue = bd.toPlainString();  // Convertir a una cadena sin notación científica
                        cellTypeDescription = "NUMERIC";
                    }
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    cellTypeDescription = "BOOLEAN";
                    break;
                case FORMULA:
                    cellValue = cell.getCellFormula();
                    cellTypeDescription = "FORMULA";
                    break;
                default:
                    cellValue = "";
                    cellTypeDescription = "TIPO DESCONOCIDO";
                    break;
            }
        }
        // Devuelve el valor de la celda
        return cellValue;
    }


    // Método para verificar si una fila está vacía
    public static boolean isRowEmpty(Row row) {
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}

