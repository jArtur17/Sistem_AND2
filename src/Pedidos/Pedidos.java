package Pedidos;

/**
 * Clase que representa un pedido realizado por un cliente.
 */
public class Pedidos {
    int id_pedido, id_cliente;
    String fecha_hora, estado, metodo_pago;
    Double total;

    /**
     * Constructor de la clase Pedidos.
     *
     * @param id_pedido   Identificador único del pedido.
     * @param id_cliente  Identificador del cliente que realizó el pedido.
     * @param fecha_hora  Fecha y hora en que se realizó el pedido.
     * @param estado      Estado actual del pedido.
     * @param metodo_pago Método de pago utilizado.
     * @param total       Total del pedido.
     */
    public Pedidos(int id_pedido, int id_cliente, String fecha_hora, String estado, String metodo_pago, Double total) {
        this.id_pedido = id_pedido;
        this.id_cliente = id_cliente;
        this.fecha_hora = fecha_hora;
        this.estado = estado;
        this.metodo_pago = metodo_pago;
        this.total = total;
    }

    /**
     * Obtiene el identificador del pedido.
     *
     * @return ID del pedido.
     */
    public int getId_pedido() {
        return id_pedido;
    }

    /**
     * Establece el identificador del pedido.
     *
     * @param id_pedido Nuevo ID del pedido.
     */
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    /**
     * Obtiene el identificador del cliente.
     *
     * @return ID del cliente.
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * Establece el identificador del cliente.
     *
     * @param id_cliente Nuevo ID del cliente.
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * Obtiene la fecha y hora del pedido.
     *
     * @return Fecha y hora del pedido.
     */
    public String getFecha_hora() {
        return fecha_hora;
    }

    /**
     * Establece la fecha y hora del pedido.
     *
     * @param fecha_hora Nueva fecha y hora del pedido.
     */
    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    /**
     * Obtiene el estado del pedido.
     *
     * @return Estado del pedido.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del pedido.
     *
     * @param estado Nuevo estado del pedido.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el método de pago del pedido.
     *
     * @return Método de pago.
     */
    public String getMetodo_pago() {
        return metodo_pago;
    }

    /**
     * Establece el método de pago del pedido.
     *
     * @param metodo_pago Nuevo método de pago.
     */
    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    /**
     * Obtiene el total del pedido.
     *
     * @return Total del pedido.
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Establece el total del pedido.
     *
     * @param total Nuevo total del pedido.
     */
    public void setTotal(Double total) {
        this.total = total;
    }
}
