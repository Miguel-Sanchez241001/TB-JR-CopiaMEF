package model;

import Util.StringUtiles;
import enumTipo.TipoDocumento;
import enumTipo.TipoTarjeta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Random;
import static Util.StringUtiles.formatDateToAAAAMMDD;
import static Util.StringUtiles.lpad;
import static Util.StringUtiles.rpad;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MefTrama {
    private String prefijo;
    private String secuencia;
    private String tipo;

    private String rucUnidadEjecutora; // Ruc de la Unidad Ejecutora
    private String cuentaBancariaUnidadEjecutora; // Cuenta Bancaria de la Unidad Ejecutora
    private String fechaInicioHabilitacion; // Fecha Inicio de Habilitación (AAAAMMDD)
    private String fechaFinHabilitacion; // Fecha Fin de Habilitación (AAAAMMDD)
    private TipoTarjeta tipoTarjeta; // Tipo de Tarjeta
    private String moneda; // Moneda
    private String montoTotalGirado; // Monto Total Girado
    private TipoDocumento tipoDocumento; // Tipo de Documento
    private String numeroDocumento; // Numero de Documento
    private String glosa; // Glosa


    private String generateSecuencia(){
        Random random = new Random();

        // Unidad Ejecutora: Número aleatorio de 3 dígitos
        String Unidad =  lpad(String.valueOf(random.nextInt(900) + 100), 6, '0');

        // Expediente SIAF: Número aleatorio de 10 dígitos
        String Expediente =  lpad(String.valueOf(random.nextInt(1000000000) + 1), 10, '0');

        // Girado: Número aleatorio entre 1 y 3 dígitos
        String girado =  lpad(String.valueOf(random.nextInt(3) + 1), 4, '0');
         // Secuencia Girado: Número aleatorio entre 1 y 3
        String Secuenciagirado =  lpad(String.valueOf(random.nextInt(3) + 1), 4, '0');
        return String.valueOf(LocalDate.now().getYear())+Unidad+Expediente+girado+Secuenciagirado;
    }


    public String generarTrama() throws Exception {
        String line =
                        "G" + // Sin cambios
                         lpad(generateSecuencia(), 28, '0') + // Aplicar lpad a Secuencia
                        "001" + // Sin cambios
                         lpad(this.getRucUnidadEjecutora(), 11, '0') + // RUC
                         lpad(this.getCuentaBancariaUnidadEjecutora().replace("-",""), 20, '0') + // Cuenta Bancaria
                         lpad(formatDateToAAAAMMDD(this.getFechaInicioHabilitacion()), 8, '0') + // Fecha Inicio
                         lpad(formatDateToAAAAMMDD(this.getFechaFinHabilitacion()), 8, '0') + // Fecha Fin
                         lpad(this.getTipoTarjeta().getCodigo(), 2, '0') + // Tipo Tarjeta
                         lpad("S/.", 6, ' ') + // Moneda
                         lpad(this.getMontoTotalGirado().replace(".",""), 15, '0') + // Monto Total
                         lpad(this.getTipoDocumento().getCodigo(), 2, '0') + // Tipo Documento
                         lpad(this.getNumeroDocumento(), 20, '0') + // Número Documento
                         rpad("LINEA DE CREDITO", 92, ' '); // Glosa

        return line+"\n";
    }

}
