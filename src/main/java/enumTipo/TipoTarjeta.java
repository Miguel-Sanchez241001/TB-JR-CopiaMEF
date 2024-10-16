package enumTipo;

public enum TipoTarjeta {
    ENCARGO_INTERNO("01"),
    VIATICOS("02");

    private final String codigo;

    TipoTarjeta(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
    public static TipoTarjeta fromCodigo(String codigo) {
        for (TipoTarjeta tipo : TipoTarjeta.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        // Si no se encuentra un tipo con el código, se puede lanzar una excepción o devolver null
        throw new IllegalArgumentException("Código no válido: " + codigo);
    }
}
