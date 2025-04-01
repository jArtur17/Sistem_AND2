package Producto;
import java.sql.Date;

/**
 * La clase `Producto` representa la información de un producto.
 *
 * @author Lasso
 */
public class Producto {
    /** El ID único del producto. */
    int id_producto;
    /** La cantidad de stock disponible del producto. */
    int stock;
    /** El stock mínimo requerido para el producto. */
    int stock_minimo;
    /** El precio unitario del producto. */
    int precio_unitario;
    /** El nombre del producto. */
    String nombre;
    /** La categoría del producto. */
    String categoria;
    /** Las indicaciones del producto. */
    String indicaciones;
    /** El almacén donde se encuentra el producto. */
    String almacen;
    /** El lote del producto. */
    String lote;
    /** La fecha de vencimiento del producto. */
    Date fecha_vencimiento;

    /**
     * Constructor para crear un objeto `Producto` con todos los detalles.
     *
     * @param id_producto El ID único del producto.
     * @param nombre El nombre del producto.
     * @param categoria La categoría del producto.
     * @param stock La cantidad de stock disponible del producto.
     * @param stock_minimo El stock mínimo requerido para el producto.
     * @param precio_unitario El precio unitario del producto.
     * @param fecha_vencimiento La fecha de vencimiento del producto.
     * @param indicaciones Las indicaciones del producto.
     * @param almacen El almacén donde se encuentra el producto.
     * @param lote El lote del producto.
     */
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
     * Constructor vacío para crear un objeto `Producto` sin detalles iniciales.
     */
    public Producto() {
    }

    /**
     * Obtiene el ID del producto.
     *
     * @return El ID del producto.
     */
    public int getId_producto() {
        return id_producto;
    }

    /**
     * Establece el ID del producto.
     *
     * @param id_producto El nuevo ID del producto.
     */
    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    /**
     * Obtiene la cantidad de stock disponible del producto.
     *
     * @return La cantidad de stock disponible del producto.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece la cantidad de stock disponible del producto.
     *
     * @param stock La nueva cantidad de stock disponible del producto.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Obtiene el stock mínimo requerido para el producto.
     *
     * @return El stock mínimo requerido para el producto.
     */
    public int getStock_minimo() {
        return stock_minimo;
    }

    /**
     * Establece el stock mínimo requerido para el producto.
     *
     * @param stock_minimo El nuevo stock mínimo requerido para el producto.
     */
    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    /**
     * Obtiene el precio unitario del producto.
     *
     * @return El precio unitario del producto.
     */
    public int getPrecio_unitario() {
        return precio_unitario;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precio_unitario El nuevo precio unitario del producto.
     */
    public void setPrecio_unitario(int precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre El nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la categoría del producto.
     *
     * @return La categoría del producto.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del producto.
     *
     * @param categoria La nueva categoría del producto.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene las indicaciones del producto.
     *
     * @return Las indicaciones del producto.
     */
    public String getIndicaciones() {
        return indicaciones;
    }

    /**
     * Establece las indicaciones del producto.
     *
     * @param indicaciones Las nuevas indicaciones del producto.
     */
    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    /**
     * Obtiene el almacén donde se encuentra el producto.
     *
     * @return El almacén donde se encuentra el producto.
     */
    public String getAlmacen() {
        return almacen;
    }

    /**
     * Establece el almacén donde se encuentra el producto.
     *
     * @param almacen El nuevo almacén donde se encuentra el producto.
     */
    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    /**
     * Obtiene el lote del producto.
     *
     * @return El lote del producto.
     */
    public String getLote() {
        return lote;
    }

    /**
     * Establece el lote del producto.
     *
     * @param lote El nuevo lote del producto.
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * Obtiene la fecha de vencimiento del producto.
     *
     * @return La fecha de vencimiento del producto.
     */
    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    /**
     * Establece la fecha de vencimiento del producto.
     *
     * @param fecha_vencimiento La nueva fecha de vencimiento del producto.
     */
    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }
}