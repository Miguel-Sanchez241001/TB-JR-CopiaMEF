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
}
