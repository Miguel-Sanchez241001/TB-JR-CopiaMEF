package enumTipo;

public enum TipoDocumento {
    DNI("01"),
    CARNET_EXTRANJERIA("04");

    private final String codigo;

    TipoDocumento(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
    public static TipoDocumento fromCodigo(String codigo) {
        for (TipoDocumento tipo : TipoDocumento.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        // Si no se encuentra un tipo con el código, se puede lanzar una excepción o devolver null
        throw new IllegalArgumentException("Código no válido: " + codigo);
    }
}
