package Pedidos;

import Conexion.Conexion;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidosDAO {
    Conexion cf = new Conexion();

    //1) Obtener clientes. cf: objeto de la conexion.
    public List<Pedidos> Obtener() {
        List<Pedidos> pedidos = new ArrayList<>();
        Connection con = cf.getConnection();

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

}
