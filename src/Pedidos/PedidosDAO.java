package Pedidos;

import Conexion.Conexion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arturo Bañol
 * @version 1.0
 */
public class PedidosDAO {
    Conexion cf = new Conexion();
    Connection con = cf.getConnection();
    GenerarPDF g = new GenerarPDF();


    /*
    //1) Obtener clientes. cf: objeto de la conexion.
    public List<Pedidos> Obtener() {
        List<Pedidos> pedidos = new ArrayList<>();


        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pedidos");

            while (rs.next())
            {
                Pedidos pedido = new Pedidos(rs.getInt("id_pedido"), rs.getInt("id_cliente"), rs.getString("fecha_hora"),
                        rs.getString("estado"), rs.getString("metodo_pago"), rs.getDouble("total"));

                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    //2) Buscar pedidos por ID
    public List<Pedidos> Buscar(int id)
    {
        List<Pedidos> pedidos = new ArrayList<>();
        Connection con = cf.getConnection();

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pedidos WHERE id_pedido = '"+id+"'");

            while (rs.next())
            {
                Pedidos pedido = new Pedidos(rs.getInt("id_pedido"),rs.getInt("id_cliente"),rs.getString("fecha_hora"),
                        rs.getString("estado"), rs.getString("metodo_pago"), rs.getDouble("total"));

                pedidos.add(pedido);

            }

            if (pedidos.isEmpty())
                JOptionPane.showMessageDialog(null, "Pedido no encontrado");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return pedidos;
    }

    //3) Agregar pedidos. (Pedidos pedidos) = nuevo registro en pedidos
    public boolean Agregar(Pedidos pedidos)
    {
        Connection con = cf.getConnection();
        String query = "INSERT INTO Pedidos (id_cliente, fecha_hora, estado, metodo_pago, totasl) VALUES (?, ?, ?, ?, ?)";
        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, pedidos.getId_cliente());
            pst.setString(2, pedidos.getFecha_hora());
            pst.setString(3, pedidos.getEstado());
            pst.setString(4, pedidos.getMetodo_pago());
            pst.setDouble(4, pedidos.getTotal());


            int resultado = pst.executeUpdate();
            return resultado > 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //4) Actualizar pedios, tener en cuenta que no todos los campos van a ser actualizables.
    public void Actualizar(int id_cliente, String fecha_hora, String estado, String metodo_pago, Double total)
    {
        Connection con = cf.getConnection();
        String query = "UPDATE pedidos SET id_cliente = ?, fecha_hora = ?, estado = ?, metodo_pago = ?, total = ? WHERE id_pedido = ? ";

        try {
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1,id_cliente);
            stmt.setString(2,fecha_hora);
            stmt.setString(3,estado);
            stmt.setString(4, metodo_pago);
            stmt.setDouble(5, total);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null,"Pedido actualizado!");
            }
            else
                JOptionPane.showMessageDialog(null,"El pedido no existe");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //5) Eliminar registro del pedido por ID
    public List<Pedidos> Eliminar(int id) {
        Connection con = cf.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Pedidos WHERE id_pedido = ?");
            stmt.setInt(1, id);

            int filas = stmt.executeUpdate();

            if (filas > 0)
            {
                //int r = JOptionPane.showConfirmDialog(null, "¿Estás seguro? \n " +
                        //"se eliminará el pedido permanentemente", "Confirmacion", JOptionPane.YES_NO_OPTION);
                JOptionPane.showMessageDialog(null, "Pedido eliminado exitosamente");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encontró");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
     */

    /**
     * Returns a list of productos
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

                    //detalles.add("----------------------------------"); // Separador

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
                detalles.add("");// Línea separadora
                detalles.add("Total de la compra: $" + totalRs.getInt("total")); // Agregar total
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }


}


