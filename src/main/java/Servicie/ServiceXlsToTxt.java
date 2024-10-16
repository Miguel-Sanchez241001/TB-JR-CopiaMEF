package Servicie;

import Util.StringUtiles;
import enumTipo.Entidades;
import enumTipo.TipoDocumento;
import enumTipo.TipoTarjeta;
import model.MefTrama;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Util.XlsUtils.getCellValue;
import static Util.XlsUtils.isRowEmpty;

public class ServiceXlsToTxt {


    public static List<MefTrama> ObtenerDatOfXls(File archivoXLS){
        List<MefTrama> objectList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(archivoXLS);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Obtener la primera hoja del archivo Excel
            Sheet sheet = workbook.getSheetAt(0);
            boolean skipFirstRow = true;

            // Iterar sobre las filas de la hoja, comenzando después de la primera fila
            for (Row row : sheet) {
                if (skipFirstRow) {
                    skipFirstRow = false; // Omitir la primera fila (cabecera)
                    continue;
                }

                // Verificar si la fila está vacía
                if (isRowEmpty(row)) {
                    break;  // Detener el bucle si la fila está vacía
                }
//                MefTrama trama1 = new MefTrama("G",
//                        StringUtiles.generateRandomNumber(2),
//                        "001", Entidades.RELACIONES.getRuc(), Entidades.RELACIONES.getCuenta(),
//                        StringUtiles.formatDateToAAAAMMDD("2024-10-04"),
//                        StringUtiles.formatDateToAAAAMMDD("2024-10-05"),
//                        TipoTarjeta.VIATICOS, "  s/. ", "100000",
//                        TipoDocumento.DNI, "15743653", "Linea Credito");

                // Leer los datos de la fila y crear un objeto
                MefTrama obj = new MefTrama();

                obj.setRucUnidadEjecutora(getCellValue(row, 0));  // Leer el valor de la primera celda
                obj.setCuentaBancariaUnidadEjecutora(getCellValue(row, 1));
                obj.setFechaInicioHabilitacion(getCellValue(row, 2));
                obj.setFechaFinHabilitacion(getCellValue(row, 3));
                obj.setTipoTarjeta(TipoTarjeta.fromCodigo( getCellValue(row, 4)));
                obj.setMontoTotalGirado(getCellValue(row, 5));
                obj.setTipoDocumento(TipoDocumento.fromCodigo( getCellValue(row, 6)));
                obj.setNumeroDocumento(  getCellValue(row, 7));

                 objectList.add(obj);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objectList;
    }
}
