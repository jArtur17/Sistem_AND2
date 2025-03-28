package Caja;
/**
 * @author Nicolle
 * @version 1.0
 */
public class Caja {

    int id_caja, id_detallefinanciero; String concepto; int valor;

    public Caja(int id_caja, int id_detallefinanciero, String concepto, int valor) {
        this.id_caja = id_caja;
        this.id_detallefinanciero = id_detallefinanciero;
        this.concepto = concepto;
        this.valor = valor;
    }

    /**
     * Returns the id of the caja.
     *
     * @return the id of the c
     */
    public int getId_caja() {
        return id_caja;
    }

    /**
     * Sets the id of the caja.
     *
     * @param id_caja
     */
    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    /**
     * /*
     * (non-Javadoc)
     *
     *
     */
    public int getId_detallefinanciero() {
        return id_detallefinanciero;
    }

    /**
     * Set the id of the de-detallefinciero.
     *
     * @param id
     */
    /**
     * Sets the id of the de-detallefinciero.
     *
     *
     */
    public void setId_detallefinanciero(int id_detallefinanciero) {
        this.id_detallefinanciero = id_detallefinanciero;
    }

    /**
     * Returns the concepto of this object.
     *
     * @return the concepto of this object
     */
    /**
     * Returns the concepto of this object.
     *
     * @return the concepto of this object
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Set the concepto.
     *
     * @param concepto the concepto
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * Get the value of the valor.
     *
     * @return the value of the valor.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Sets the value of the value of the value.
     *
     * @param valor the value of
     */
    public void setValor(int valor) {
        this.valor = valor;
    }
}
