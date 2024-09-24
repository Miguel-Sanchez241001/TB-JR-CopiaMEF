import Util.StringUtiles;
import enumTipo.TipoDocumento;
import enumTipo.TipoTarjeta;
import model.Cabecera;
import model.MefTrama;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EntryPoint {
    public static void main(String[] args) throws Exception {
        List<MefTrama> tramaList = new ArrayList<>();

        Cabecera cabecera = Cabecera.builder()
                .nLote(StringUtiles.generateRandomNumber(3))
                .fecha(StringUtiles.formatDateToAAAAMMDDHHMISS("2024-03-04"))
                .prefijo("H")
                .registros("5")
                .sumaImporte("1400")
                .build();

        MefTrama trama1 = new MefTrama("G",
                StringUtiles.generateRandomNumber(2),
                "001", "20307167442", "0000002424233",
                StringUtiles.formatDateToAAAAMMDD("2024-03-04"),
                StringUtiles.formatDateToAAAAMMDD("2024-03-24"),
                TipoTarjeta.ENCARGO_INTERNO, "  s/. ", "800000",
                TipoDocumento.DNI, "43494327", "Linea Credito");

        MefTrama trama2 = new MefTrama("G",
                StringUtiles.generateRandomNumber(2),
                "001", "20307167442", "0000002424233",
                StringUtiles.formatDateToAAAAMMDD("2024-03-04"),
                StringUtiles.formatDateToAAAAMMDD("2024-03-29"),
                TipoTarjeta.VIATICOS, "  s/. ", "500000",
                TipoDocumento.DNI, "43633502", "Linea Credito");

        MefTrama trama3 = new MefTrama("G",
                StringUtiles.generateRandomNumber(2),
                "001", "20307167442", "0000002424233",
                StringUtiles.formatDateToAAAAMMDD("2024-03-04"),
                StringUtiles.formatDateToAAAAMMDD("2024-03-28"),
                TipoTarjeta.ENCARGO_INTERNO, "  s/. ", "600000",
                TipoDocumento.DNI, "44077588", "Linea Credito");

        MefTrama trama4 = new MefTrama("G",
                StringUtiles.generateRandomNumber(2),
                "001", "20131380101", "0012323222222",
                StringUtiles.formatDateToAAAAMMDD("2024-03-04"),
                StringUtiles.formatDateToAAAAMMDD("2024-03-11"),
                TipoTarjeta.ENCARGO_INTERNO, "  s/. ", "750000",
                TipoDocumento.DNI, "16727214", "Linea Credito");

        MefTrama trama5 = new MefTrama("G",
                StringUtiles.generateRandomNumber(2),
                "001", "20307167442", "0000002424233",
                StringUtiles.formatDateToAAAAMMDD("2024-03-04"),
                StringUtiles.formatDateToAAAAMMDD("2024-03-31"),
                TipoTarjeta.ENCARGO_INTERNO, "  s/. ", "1500000",
                TipoDocumento.DNI, "43304227", "Linea Credito");
        MefTrama trama6 = new MefTrama("G",
                StringUtiles.generateRandomNumber(2),
                "001", "20131370645", "0000002232333",
                StringUtiles.formatDateToAAAAMMDD("2024-03-04"),
                StringUtiles.formatDateToAAAAMMDD("2024-03-31"),
                TipoTarjeta.ENCARGO_INTERNO, "  s/. ", "974100",
                TipoDocumento.DNI, "32483106", "Linea Credito");
        // Añadir los objetos a la lista
        tramaList.add(trama1);
        tramaList.add(trama2);
        tramaList.add(trama3);
        tramaList.add(trama4);
        tramaList.add(trama5);
        tramaList.add(trama6);

        File file = new File("data/tramas.txt");

        // Asegurar que el directorio padre existe
        file.getParentFile().mkdirs();

        // Usar try-with-resources para manejar el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (MefTrama trama : tramaList) {
                String line =
                        trama.getPrefijo() + // Sin cambios
                                StringUtiles.lpad(trama.getSecuencia(), 28, '0') + // Aplicar lpad a Secuencia
                                trama.getTipo() + // Sin cambios
                                StringUtiles.lpad(trama.getRucUnidadEjecutora(), 11, '0') + // RUC
                                StringUtiles.lpad(trama.getCuentaBancariaUnidadEjecutora(), 20, '0') + // Cuenta Bancaria
                                StringUtiles.lpad(trama.getFechaInicioHabilitacion(), 8, '0') + // Fecha Inicio
                                StringUtiles.lpad(trama.getFechaFinHabilitacion(), 8, '0') + // Fecha Fin
                                StringUtiles.lpad(trama.getTipoTarjeta().getCodigo(), 2, '0') + // Tipo Tarjeta
                                StringUtiles.lpad(trama.getMoneda(), 6, '0') + // Moneda
                                StringUtiles.lpad(trama.getMontoTotalGirado(), 15, '0') + // Monto Total
                                StringUtiles.lpad(trama.getTipoDocumento().getCodigo(), 2, '0') + // Tipo Documento
                                StringUtiles.lpad(trama.getNumeroDocumento(), 20, '0') + // Número Documento
                                StringUtiles.rpad(trama.getGlosa(), 92, ' '); // Glosa


                // Escribir la línea al archivo
                writer.write(line);
                writer.newLine(); // Añadir un salto de línea

                // Imprimir la línea en pantalla
                System.out.println(line);
            }

            System.out.println("Archivo tramas.txt generado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
