package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cabecera {
    private String prefijo;
    private String nLote;
    private String fecha;
    private String registros;
    private String sumaImporte;

}
