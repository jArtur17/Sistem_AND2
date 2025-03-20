package Proyecto.Producto;
import java.sql.Date;

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
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public int getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(int precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }
}

