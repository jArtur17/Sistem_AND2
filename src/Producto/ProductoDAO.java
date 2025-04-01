package Producto;

import Conexion.Conexion;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase `ProductoDAO` proporciona métodos para interactuar con la tabla `producto` en la base de datos.
 *
 * @author Lasso
 */
public class ProductoDAO {
    /** Instancia de la clase `Conexion` para establecer la conexión con la base de datos. */
    private Conexion connectionFA = new Conexion();

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param producto El objeto `Producto` que contiene los datos del producto a agregar.
     */
    public void agregar(Producto producto) {
        Connection con = connectionFA.getConnection();
        String query = "INSERT INTO producto(nombre, categoria, stock, stock_minimo, precio_unitario, fecha_vencimiento, indicaciones, almacen, lote) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, producto.getNombre());
            pst.setString(2, producto.getCategoria());
            pst.setInt(3, producto.getStock());
            pst.setInt(4, producto.getStock_minimo());
            pst.setInt(5, producto.getPrecio_unitario());
            pst.setDate(6, producto.getFecha_vencimiento());
            pst.setString(7, producto.getIndicaciones());
            pst.setString(8, producto.getAlmacen());
            pst.setString(9, producto.getLote());

            int result = pst.executeUpdate();

            if (result > 0)
                JOptionPane.showMessageDialog(null, "Añadido con éxito");
            else
                JOptionPane.showMessageDialog(null, "No añadido");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No añadido");
            return;
        }
    }

    /**
     * Actualiza los datos de un producto existente en la base de datos.
     *
     * @param producto El objeto `Producto` que contiene los datos actualizados del producto.
     */
    public void actualizar(Producto producto) {
        Connection con = connectionFA.getConnection();

        String query = "UPDATE producto SET nombre = ?, categoria = ?,stock = ?, stock_minimo = ?, precio_unitario = ?,fecha_vencimiento = ?,indicaciones = ?, almacen = ?, lote = ? WHERE id_producto = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setInt(3, producto.getStock());
            stmt.setInt(4, producto.getStock_minimo());
            stmt.setInt(5, producto.getPrecio_unitario());
            stmt.setDate(6, producto.getFecha_vencimiento());
            stmt.setString(7, producto.getIndicaciones());
            stmt.setString(8, producto.getAlmacen());
            stmt.setString(9, producto.getLote());

            stmt.setInt(10, producto.getId_producto());

            int rows = stmt.executeUpdate();

            if (rows > 0)
                JOptionPane.showMessageDialog(null, "Actualización de productos exitosa");
            else
                JOptionPane.showMessageDialog(null, "No encontrado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un producto de la base de datos por su ID.
     *
     * @param id El ID del producto a eliminar.
     */
    public void eliminar(int id) {
        Connection con = connectionFA.getConnection();
        String query = "DELETE FROM producto WHERE id_producto = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int result = pst.executeUpdate();

            if (result > 0)
                JOptionPane.showMessageDialog(null, "Se elimino exitosamente");
            else
                JOptionPane.showMessageDialog(null, "No se elimino");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se elimino");
        }
    }

    /**
     * Verifica si existe un producto con el nombre especificado en la base de datos.
     *
     * @param nombre El nombre del producto a buscar.
     * @return `true` si el producto existe, `false` en caso contrario.
     */
    public boolean existeProducto(String nombre) {
        Connection conexion = connectionFA.getConnection();
        String query = "SELECT * FROM producto WHERE nombre = ?";
        try {
            PreparedStatement pst = conexion.prepareStatement(query);
            pst.setString(1, nombre);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}