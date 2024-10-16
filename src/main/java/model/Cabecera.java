package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static Util.StringUtiles.lpad;

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

    public String generarTrama(){
        String Linea = getPrefijo()
                +   lpad(getNLote(),10,'0')
                + getFecha()
                +   lpad(getRegistros(),8,'0')
                +   lpad(getSumaImporte(),15,'0') ;
        return Linea+"\n";
    }
}
