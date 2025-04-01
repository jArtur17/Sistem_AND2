package Reportes;

import Conexion.Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO (Data Access Object) para la generación de reportes de ventas.
 * @author Nicolle
 */
public class ReportesDAO {

    private Conexion conexion = new Conexion();

    /**
     * Obtiene las ventas diarias desde la base de datos.
     * @return Un ResultSet con las ventas diarias, o null si ocurre un error.
     */
    public ResultSet Diarias() {
        Connection con = conexion.getConnection();
        String query = "SELECT fecha_hora, SUM(total) as venta_diaria " +
                "FROM pedidos " +
                "GROUP BY fecha_hora " +
                "ORDER BY fecha_hora DESC";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar ventas diarias: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene las ventas semanales desde la base de datos.
     * @return Un ResultSet con las ventas semanales, o null si ocurre un error.
     */
    public ResultSet Semanales() {
        Connection con = conexion.getConnection();
        String query = "SELECT " +
                "YEARWEEK(fecha_hora, 1) as semana, " +
                "MIN(fecha_hora) as inicio_semana, " +
                "MAX(fecha_hora) as fin_semana, " +
                "SUM(total) as venta_semanal " +
                "FROM pedidos " +
                "GROUP BY YEARWEEK(fecha_hora, 1) " +
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

    /**
     * Obtiene las ventas mensuales desde la base de datos.
     * @return Un ResultSet con las ventas mensuales, o null si ocurre un error.
     */
    public ResultSet Mensuales() {
        Connection con = conexion.getConnection();
        String query = "SELECT " +
                "YEAR(fecha_hora) as año, " +
                "MONTH(fecha_hora) as mes, " +
                "SUM(total) as venta_mensual " +
                "FROM pedidos " +
                "GROUP BY YEAR(fecha_hora), MONTH(fecha_hora) " +
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