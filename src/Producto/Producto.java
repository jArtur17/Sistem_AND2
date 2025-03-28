package Producto;
import java.sql.Date;

/**
 * @author Lasso
 * @version 1.0
 */

public class Producto {
    int id_producto, stock, stock_minimo, precio_unitario; String nombre, categoria, indicaciones, almacen, lote;
    Date fecha_vencimiento;

    public Producto(int id_producto, String nombre, String categoria,  int stock, int stock_minimo, int precio_unitario, Date fecha_vencimiento,  String indicaciones, String almacen, String lote) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.stock_minimo = stock_minimo;
        this.precio_unitario = precio_unitario;
        this.fecha_vencimiento = fecha_vencimiento;
        this.indicaciones = indicaciones;
        this.almacen = almacen;
        this.lote = lote;

    }
    /**
     * <pre>
     * get id_producto = 1;</pre>
     */
    public int getId_producto() {
        return id_producto;
    }

    /**
     * Sets the id_producto value.
     *
     * @param id_producto the id
     */
    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    /**
     * Returns the stock of the current user.
     *
     * @return the stock of the current user
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock value.
     *
     * @param stock the stock value
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns the minimo value of the stock.
     *
     * @return the minimo value of
     */
    public int getStock_minimo() {
        return stock_minimo;
    }

    /**
     * Sets the minimo value for the stock.
     *
     *
     */
    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    /**
     * Returns the number of precio unitario.
     *
     * @return the number of precio
     */
    public int getPrecio_unitario() {
        return precio_unitario;
    }

    /**
     * Sets the precio_unitario value.
     *
     * @param precio_unitario
     */
    public void setPrecio_unitario(int precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    /**
     * Gets the nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Returns the categoria of this user.
     *
     * @return the categoria of this user
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Sets the categoria of the user.
     *
     * @param categoria the categoria of
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Returns the list of indicaciones.
     *
     * @return the list of indicaciones
     */
    public String getIndicaciones() {
        return indicaciones;
    }

    /**
     * Sets the list of indicaciones.
     *
     * @param indicaciones the list of
     */
    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    /**
     * Gets the almacen.
     *
     * @return the almacen
     */
    public String getAlmacen() {
        return almacen;
    }

    /**
     * Sets the almacen for the current user.
     *
     * @param almacen the
     */
    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    /**
     * Returns the lote of the current user.
     *
     * @return the lote of the
     */
    public String getLote() {
        return lote;
    }

    /**
     * Sets the lote of the current user.
     *
     * @param lote the lote
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * /*
     * (non-Javadoc)
     *
     *
     */
    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    /**
     * Set the fecha vencimiento.
     *
     *
     */
    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }
}

