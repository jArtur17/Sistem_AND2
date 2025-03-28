package Caja;

public class Caja {

    int id_caja, id_detallefinanciero; String concepto; int valor;

    public Caja(int id_caja, int id_detallefinanciero, String concepto, int valor) {
        this.id_caja = id_caja;
        this.id_detallefinanciero = id_detallefinanciero;
        this.concepto = concepto;
        this.valor = valor;
    }

    public int getId_caja() {
        return id_caja;
    }

    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    public int getId_detallefinanciero() {
        return id_detallefinanciero;
    }

    public void setId_detallefinanciero(int id_detallefinanciero) {
        this.id_detallefinanciero = id_detallefinanciero;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
