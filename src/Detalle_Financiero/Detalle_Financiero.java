package Detalle_Financiero;


import java.time.LocalDateTime;

public class Detalle_Financiero {

    int id_detallefinanciero; String tipo_pago; int ingreso, egreso; String descripcion; LocalDateTime fecha_hora;

    public Detalle_Financiero(int id_detallefinanciero, String tipo_pago, int ingreso, int egreso, String descripcion, LocalDateTime fecha_hora) {
        this.id_detallefinanciero = id_detallefinanciero;
        this.tipo_pago = tipo_pago;
        this.ingreso = ingreso;
        this.egreso = egreso;
        this.descripcion = descripcion;
        this.fecha_hora = fecha_hora;
    }

    public int getId_detallefinanciero() {
        return id_detallefinanciero;
    }

    public void setId_detallefinanciero(int id_detallefinanciero) {
        this.id_detallefinanciero = id_detallefinanciero;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public int getIngreso() {
        return ingreso;
    }

    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }

    public int getEgreso() {
        return egreso;
    }

    public void setEgreso(int egreso) {
        this.egreso = egreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
}
