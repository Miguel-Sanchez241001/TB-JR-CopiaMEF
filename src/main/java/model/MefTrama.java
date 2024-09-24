package model;

import enumTipo.TipoDocumento;
import enumTipo.TipoTarjeta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
