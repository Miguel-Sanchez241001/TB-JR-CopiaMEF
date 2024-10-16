package model;

import Util.StringUtiles;
import enumTipo.TipoDocumento;
import enumTipo.TipoTarjeta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static Util.StringUtiles.formatDateToAAAAMMDD;

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
    
    public String generarTrama() throws Exception {
        String line =
                        "G" + // Sin cambios
                        StringUtiles.lpad(this.getSecuencia(), 28, '0') + // Aplicar lpad a Secuencia
                        "001" + // Sin cambios
                        StringUtiles.lpad(this.getRucUnidadEjecutora(), 11, '0') + // RUC
                        StringUtiles.lpad(this.getCuentaBancariaUnidadEjecutora().replace("-",""), 20, '0') + // Cuenta Bancaria
                        StringUtiles.lpad(formatDateToAAAAMMDD(this.getFechaInicioHabilitacion()), 8, '0') + // Fecha Inicio
                        StringUtiles.lpad(formatDateToAAAAMMDD(this.getFechaFinHabilitacion()), 8, '0') + // Fecha Fin
                        StringUtiles.lpad(this.getTipoTarjeta().getCodigo(), 2, '0') + // Tipo Tarjeta
                        StringUtiles.lpad("S/.", 6, ' ') + // Moneda
                        StringUtiles.lpad(this.getMontoTotalGirado().replace(".",""), 15, '0') + // Monto Total
                        StringUtiles.lpad(this.getTipoDocumento().getCodigo(), 2, '0') + // Tipo Documento
                        StringUtiles.lpad(this.getNumeroDocumento(), 20, '0') + // Número Documento
                        StringUtiles.rpad("LINEA DE CREDITO", 92, ' '); // Glosa

        return line+"\n";
    }

}
