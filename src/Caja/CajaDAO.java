package Caja;

import Conexion.Conexion;
import java.sql.*;

public class CajaDAO {

    private Conexion conexion = new Conexion();

    public int ObtenerSaldoActual() {
        int saldo = 0;
        String query = "SELECT SUM(valor) FROM caja";

        try (Connection con = conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                saldo = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saldo;
    }

    public int ObtenerIdCajaPorIdDetalleFinanciero(int id_detallefinanciero) {
        int id_caja = -1;
        String query = "SELECT id_caja FROM caja WHERE id_detallefinanciero = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id_detallefinanciero);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id_caja = rs.getInt("id_caja");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_caja;
    }



    public void RegistrarMovimiento(String concepto, int valorMovimiento, int id_detallefinanciero) {
        Connection con = conexion.getConnection();
        try {
            String query = "INSERT INTO caja (concepto, valor, id_detallefinanciero) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, concepto);
            ps.setInt(2, valorMovimiento);
            ps.setInt(3, id_detallefinanciero); // Asociamos el movimiento con el id_financiero
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void EliminarMovimientoPorIdDetalleFinanciero(int id_detallefinanciero) {
        String query = "DELETE FROM caja WHERE id_detallefinanciero = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id_detallefinanciero);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ActualizarMovimiento(int id_caja, String concepto, int valor, int id_detallefinanciero) {
        String query = "UPDATE caja SET concepto = ?, valor = ?, id_detallefinanciero = ? WHERE id_caja = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, concepto);
            stmt.setInt(2, valor);
            stmt.setInt(3, id_detallefinanciero);
            stmt.setInt(4, id_caja);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

