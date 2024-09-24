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
}
