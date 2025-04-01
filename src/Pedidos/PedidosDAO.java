package Pedidos;

import Conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar operaciones relacionadas con los pedidos en la base de datos.
 */
public class PedidosDAO {
    Conexion cf = new Conexion();
    Connection con = cf.getConnection();

    /**
     * Obtiene la lista de productos y detalles asociados a un pedido específico.
     *
     * @param idPedido Identificador del pedido.
     * @return Lista de cadenas con la información del pedido y sus productos.
     */
    public List<String> obtenerProductosPorPedido(int idPedido) {
        List<String> detalles = new ArrayList<>();
        String query = "SELECT c.nombre AS cliente, c.direccion, c.cedula, " +
                "p.nombre AS producto, dp.cantidad, dp.tipo_cantidad, dp.subtotal, " +
                "ped.metodo_pago, ped.total " +
                "FROM detalle_pedido dp " +
                "JOIN producto p ON dp.id_producto = p.id_producto " +
                "JOIN pedidos ped ON dp.id_pedido = ped.id_pedido " +
                "JOIN cliente c ON ped.id_cliente = c.id_cliente " +
                "WHERE dp.id_pedido = ?";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            String nombreCliente = ""; // Guardamos el nombre del cliente solo una vez
            while (rs.next()) {
                if (nombreCliente.isEmpty()) {
                    nombreCliente = rs.getString("cliente");
                    String direccion = rs.getString("direccion");
                    String cedula = rs.getString("cedula");
                    String metodoPago = rs.getString("metodo_pago");
                    detalles.add("");
                    detalles.add("Cliente: " + nombreCliente);
                    detalles.add(cedula);
                    detalles.add("Direccion: " + direccion);
                    detalles.add("Método de pago: " + metodoPago);
                    detalles.add("");
                    detalles.add("");
                    detalles.add("Productos: ");
                }

                String producto = "   * " + rs.getInt("cantidad") + " / " + rs.getString("tipo_cantidad") +
                        " de " + rs.getString("producto") +
                        " - subtotal: $" + rs.getInt("subtotal");
                detalles.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String totalQuery = "SELECT total FROM pedidos WHERE id_pedido = ?";
        try (PreparedStatement totalStmt = con.prepareStatement(totalQuery)) {
            totalStmt.setInt(1, idPedido);
            ResultSet totalRs = totalStmt.executeQuery();
            if (totalRs.next()) {
                detalles.add("");
                detalles.add(""); // Línea separadora
                detalles.add("Total de la compra: $" + totalRs.getInt("total")); // Agregar total
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
}
