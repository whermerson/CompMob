package br.com.softdicas.compmob;


public class ClienteClass {

    private String identificacaoCliente;
    private String tipoDeElemento;
    private int qtdElementos;
    private int consumoPerCapta;
    private String uid;
    private String profileUrl;


    public ClienteClass(String identificacaoCliente, int qtdElementos, int consumoPerCapta, String tipoDeElemento, String uid, String profileUrl) {
        this.identificacaoCliente = identificacaoCliente;
        this.qtdElementos = qtdElementos;
        this.consumoPerCapta = consumoPerCapta;
        this.tipoDeElemento = tipoDeElemento;
        this.uid = uid;
        this.profileUrl = profileUrl;
    }

    public String getidentificacaoCliente() {
        return identificacaoCliente;
    }

    public int getqtdElementos() {
        return qtdElementos;
    }

    public int getconsumoPerCapta() {
        return consumoPerCapta;
    }

    public String gettipoDeElemento() {
        return tipoDeElemento;
    }

    public String getuid() {
        return uid;
    }

    public String getprofileUrl() {
        return profileUrl;
    }

    public void setqtdElementos(int qtdMoradores) { this.qtdElementos = qtdMoradores; }

    public void setconsumoPerCapta(int consumoPerCapta) { this.consumoPerCapta = consumoPerCapta; }

    public void setidentificacaoCliente(String identificacaoCliente) {
        this.identificacaoCliente = identificacaoCliente;
    }
    public void settipoDeElemento(String tipoDeElemento) {
        this.tipoDeElemento = tipoDeElemento;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public void setprofileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

}
