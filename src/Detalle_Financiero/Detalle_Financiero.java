package Detalle_Financiero;

import java.time.LocalDateTime;

/**
 * Clase que representa un detalle financiero con información sobre ingresos, egresos y tipo de pago.
 *
 * @author Nicolle
 */
public class Detalle_Financiero {

    int id_detallefinanciero;
    String tipo_pago;
    int ingreso, egreso;
    String descripcion;
    LocalDateTime fecha_hora;

    /**
     * Constructor de la clase Detalle_Financiero.
     *
     * @param id_detallefinanciero Identificador del detalle financiero.
     * @param tipo_pago Tipo de pago utilizado.
     * @param ingreso Cantidad de ingreso.
     * @param egreso Cantidad de egreso.
     * @param descripcion Descripción del movimiento financiero.
     * @param fecha_hora Fecha y hora del registro.
     */
    public Detalle_Financiero(int id_detallefinanciero, String tipo_pago, int ingreso, int egreso, String descripcion, LocalDateTime fecha_hora) {
        this.id_detallefinanciero = id_detallefinanciero;
        this.tipo_pago = tipo_pago;
        this.ingreso = ingreso;
        this.egreso = egreso;
        this.descripcion = descripcion;
        this.fecha_hora = fecha_hora;
    }

    /**
     * Obtiene el identificador del detalle financiero.
     *
     * @return ID del detalle financiero.
     */
    public int getId_detallefinanciero() {
        return id_detallefinanciero;
    }

    /**
     * Establece el identificador del detalle financiero.
     *
     * @param id_detallefinanciero Nuevo ID del detalle financiero.
     */
    public void setId_detallefinanciero(int id_detallefinanciero) {
        this.id_detallefinanciero = id_detallefinanciero;
    }

    /**
     * Obtiene el tipo de pago.
     *
     * @return Tipo de pago.
     */
    public String getTipo_pago() {
        return tipo_pago;
    }

    /**
     * Establece el tipo de pago.
     *
     * @param tipo_pago Nuevo tipo de pago.
     */
    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    /**
     * Obtiene la cantidad de ingreso.
     *
     * @return Monto de ingreso.
     */
    public int getIngreso() {
        return ingreso;
    }

    /**
     * Establece la cantidad de ingreso.
     *
     * @param ingreso Nuevo monto de ingreso.
     */
    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }

    /**
     * Obtiene la cantidad de egreso.
     *
     * @return Monto de egreso.
     */
    public int getEgreso() {
        return egreso;
    }

    /**
     * Establece la cantidad de egreso.
     *
     * @param egreso Nuevo monto de egreso.
     */
    public void setEgreso(int egreso) {
        this.egreso = egreso;
    }

    /**
     * Obtiene la descripción del movimiento financiero.
     *
     * @return Descripción del movimiento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del movimiento financiero.
     *
     * @param descripcion Nueva descripción del movimiento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha y hora del registro financiero.
     *
     * @return Fecha y hora del registro.
     */
    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    /**
     * Establece la fecha y hora del registro financiero.
     *
     * @param fecha_hora Nueva fecha y hora del registro.
     */
    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
}
