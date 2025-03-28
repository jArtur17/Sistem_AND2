package Pedidos;

public class Pedidos {
    int id_pedido, id_cliente;
    String fecha_hora, estado, metodo_pago;
    Double total;

    public Pedidos(int id_pedido, int id_cliente, String fecha_hora, String estado, String metodo_pago, Double total) {
        this.id_pedido = id_pedido;
        this.id_cliente = id_cliente;
        this.fecha_hora = fecha_hora;
        this.estado = estado;
        this.metodo_pago = metodo_pago;
        this.total = total;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
