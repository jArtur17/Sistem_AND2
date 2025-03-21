package Proyecto.Detalle_Financiero;

import Conexion.Conexion;

import javax.swing.*;
import java.sql.*;

public class Detalle_FinancieroDAO
{
    private Conexion conexion = new Conexion();


    public int Agregar(Detalle_Financiero detalle_financiero) {
        Connection con = conexion.getConnection();
        int nuevoIdFinanciero = -1;

        try {
            // Obtener el siguiente id_detallefinanciero
            String getMaxIdQuery = "SELECT COALESCE(MAX(id_detallefinanciero), 0) + 1 FROM detalle_financiero";
            PreparedStatement getMaxIdStmt = con.prepareStatement(getMaxIdQuery);
            ResultSet rs = getMaxIdStmt.executeQuery();

            if (rs.next()) {
                nuevoIdFinanciero = rs.getInt(1);
            }

            // Insertar el nuevo registro (corregido el nombre de la columna)
            String query = "INSERT INTO detalle_financiero(id_detallefinanciero, id_venta, tipo_pago, ingreso, egreso, descripcion, fecha_hora) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, nuevoIdFinanciero);
            pst.setInt(2, nuevoIdFinanciero); // id_venta = id_financiero (hasta definirlo bien)
            pst.setString(3, detalle_financiero.getTipo_pago());
            pst.setInt(4, detalle_financiero.getIngreso());
            pst.setInt(5, detalle_financiero.getEgreso());
            pst.setString(6, detalle_financiero.getDescripcion());
            pst.setTimestamp(7, Timestamp.valueOf(detalle_financiero.getFecha_hora()));

            int result = pst.executeUpdate();

            rs.close();
            getMaxIdStmt.close();
            pst.close();
            con.close();

            return (result > 0) ? nuevoIdFinanciero : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }



    public void Actualizar(Detalle_Financiero detalle_financiero){
        Connection con = conexion.getConnection();

        String query = "UPDATE detalle_financiero SET id_venta = ?, tipo_pago = ?, ingreso = ?,  egreso = ?, descripcion = ?,  fecha_hora = ?  WHERE id_detallefinanciero = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, detalle_financiero.getId_venta());
            stmt.setString(2, detalle_financiero.getTipo_pago());
            stmt.setInt(3, detalle_financiero.getIngreso());
            stmt.setInt(4, detalle_financiero.getEgreso());
            stmt.setString(5, detalle_financiero.getDescripcion());
            stmt.setTimestamp(6, Timestamp.valueOf(detalle_financiero.getFecha_hora()));
            stmt.setInt(7,detalle_financiero.getId_detallefinanciero());


            int result = stmt.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Actualizado Movimiento ");
            else
                JOptionPane.showMessageDialog(null,"No se actualizo el Movimiento");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int obtenerUltimoIdInsertado() {
        int ultimoId = -1; // Valor predeterminado en caso de error
        String query = "SELECT id_detallefinanciero FROM detalle_Financiero ORDER BY id_detallefinanciero DESC LIMIT 1";

        try (Connection con = conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                ultimoId = rs.getInt("id_detallefinanciero");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ultimoId;
    }


    public void Eliminar(int id_detallefinanciero)
    {
        Connection con = conexion.getConnection();
        String query = "DELETE FROM detalle_financiero WHERE id_detallefinanciero = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id_detallefinanciero);


            int result = pst.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Eliminado");
            else
                JOptionPane.showMessageDialog(null,"No se elimino");

        }

        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"No se elimino");

        }
    }

}

