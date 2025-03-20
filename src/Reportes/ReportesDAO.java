package Reportes;

import Conexion.Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportesDAO {

    private Conexion conexion = new Conexion();

    public ResultSet Diarias() {
        Connection con = conexion.getConnection();
        String query = "SELECT fecha, SUM(total) as venta_diaria " +
                "FROM ordenes " +
                "WHERE estado = 'Paid' " +
                "GROUP BY fecha " +
                "ORDER BY fecha DESC";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar ventas diarias: " + e.getMessage());
            return null;
        }
    }

    public ResultSet Semanales() {
        Connection con = conexion.getConnection();
        String query = "SELECT " +
                "YEARWEEK(fecha, 1) as semana, " +
                "MIN(fecha) as inicio_semana, " +
                "MAX(fecha) as fin_semana, " +
                "SUM(total) as venta_semanal " +
                "FROM ordenes " +
                "WHERE estado = 'Paid' " +
                "GROUP BY YEARWEEK(fecha, 1) " +
                "ORDER BY semana DESC";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar ventas semanales: " + e.getMessage());
            return null;
        }
    }

    public ResultSet Mensuales() {
        Connection con = conexion.getConnection();
        String query = "SELECT " +
                "YEAR(fecha) as año, " +
                "MONTH(fecha) as mes, " +
                "SUM(total) as venta_mensual " +
                "FROM ordenes " +
                "WHERE estado = 'Paid' " +
                "GROUP BY YEAR(fecha), MONTH(fecha) " +
                "ORDER BY año DESC, mes DESC";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar ventas mensuales: " + e.getMessage());
            return null;
        }
    }

}
