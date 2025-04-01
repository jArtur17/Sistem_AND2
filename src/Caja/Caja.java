package Caja;

/**
 * Representa un registro de caja con sus atributos.
 * @author Nicolle
 */
public class Caja {

    int id_caja, id_detallefinanciero; String concepto; int valor;

    /**
     * Constructor de la clase Caja.
     * @param id_caja El ID de la caja.
     * @param id_detallefinanciero El ID del detalle financiero asociado.
     * @param concepto El concepto del registro de caja.
     * @param valor El valor del registro de caja.
     */
    public Caja(int id_caja, int id_detallefinanciero, String concepto, int valor) {
        this.id_caja = id_caja;
        this.id_detallefinanciero = id_detallefinanciero;
        this.concepto = concepto;
        this.valor = valor;
    }

    /**
     * Obtiene el ID de la caja.
     * @return El ID de la caja.
     */
    public int getId_caja() {
        return id_caja;
    }

    /**
     * Establece el ID de la caja.
     * @param id_caja El ID de la caja.
     */
    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    /**
     * Obtiene el ID del detalle financiero asociado.
     * @return El ID del detalle financiero asociado.
     */
    public int getId_detallefinanciero() {
        return id_detallefinanciero;
    }

    /**
     * Establece el ID del detalle financiero asociado.
     * @param id_detallefinanciero El ID del detalle financiero asociado.
     */
    public void setId_detallefinanciero(int id_detallefinanciero) {
        this.id_detallefinanciero = id_detallefinanciero;
    }

    /**
     * Obtiene el concepto del registro de caja.
     * @return El concepto del registro de caja.
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Establece el concepto del registro de caja.
     * @param concepto El concepto del registro de caja.
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * Obtiene el valor del registro de caja.
     * @return El valor del registro de caja.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Establece el valor del registro de caja.
     * @param valor El valor del registro de caja.
     */
    public void setValor(int valor) {
        this.valor = valor;
    }
}