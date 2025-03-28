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

    /**
     * Returns the id of the Pedido.
     *obtiene el id del pedido
     * @return the id of the P
     */
    public int getId_pedido() {
        return id_pedido;
    }

    /**
     * Sets the id of the Pedido.
     * set del pedido
     * @param id_pedido the
     */
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    /**
     * Returns the id of the cliente.
     *obtiene el id del cliente
     * @return the id of the cliente
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * Sets the id of the cliente.
     * set del cliente
     * @param id_cliente The id
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * Returns the fecha hora.
     * obtener la fecha y hora
     * @return the fecha hora
     */
    public String getFecha_hora() {
        return fecha_hora;
    }

    /**
     * Set the fecha hora.
     * set de la fecha y hora
     * @param fecha_hora
     */
    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    /**
     * Returns the estado for the current user.
     * obtiene el estado del pedido
     * @return the estado for the
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the estado.
     * set el estado del pedido
     * @param estado the estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Gets the metodo pago.
     *Obtiene el metodo de pago
     * @return the metodo pago
     */
    public String getMetodo_pago() {
        return metodo_pago;
    }

    /**
     * Set the metodo pago.
     * set del metodo de pago
     * @param metodo_pago the met
     */
    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    /**
     * Returns the total value of this object.
     * obtiene el total del pedido
     * @return the total value of this object
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Sets the total value of the element.
     *set del total pedido
     * @param total the total value
     */
    public void setTotal(Double total) {
        this.total = total;
    }
}
