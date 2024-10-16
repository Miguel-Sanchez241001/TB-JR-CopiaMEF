package enumTipo;

public enum Entidades {
    SUNAT("20131312955","00000003231"),
    MEf("20131370645","00000063231"),
    RELACIONES("20131380101","00200003231");

    private String ruc;
    private String cuenta;

    Entidades(String ruc, String cuenta) {
        this.ruc = ruc;
        this.cuenta = cuenta;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
