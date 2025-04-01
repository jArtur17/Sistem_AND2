package Cliente;

import Conexion.Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase `ClienteDAO` proporciona métodos para interactuar con la tabla `cliente` en la base de datos.
 *
 * @author Lasso
 */
public class ClienteDAO {
    /** Instancia de la clase `Conexion` para establecer la conexión con la base de datos. */
    private Conexion connectionFA = new Conexion();

    /**
     * Agrega un nuevo cliente a la base de datos.
     *
     * @param cliente El objeto `Cliente` que contiene los datos del cliente a agregar.
     */
    public void agregar(Cliente cliente) {
        Connection con = connectionFA.getConnection();
        String query = "INSERT INTO cliente(cedula, nombre, telefono, correo, direccion) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, cliente.getCedula());
            pst.setString(2, cliente.getNombre());
            pst.setString(3, cliente.getTelefono());
            pst.setString(4, cliente.getCorreo());
            pst.setString(5, cliente.getDireccion());

            int result = pst.executeUpdate();

            if (result > 0)
                JOptionPane.showMessageDialog(null, "Añadido con éxito");
            else
                JOptionPane.showMessageDialog(null, "No añadido");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "no añadido");
        }
    }

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     *
     * @param cliente El objeto `Cliente` que contiene los datos actualizados del cliente.
     */
    public void actualizar(Cliente cliente) {
        Connection con = connectionFA.getConnection();

        String query = "UPDATE `cliente` SET cedula = ?, nombre = ?, telefono = ?, correo = ?, direccion = ? WHERE id_cliente = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, cliente.getCedula());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getCorreo());
            stmt.setString(5, cliente.getDireccion());
            stmt.setInt(6, cliente.getId_cliente());

            int result = stmt.executeUpdate();

            if (result > 0)
                JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente");
            else
                JOptionPane.showMessageDialog(null, "Cliente no encontrado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un cliente de la base de datos por su ID.
     *
     * @param id El ID del cliente a eliminar.
     */
    public void eliminar(int id) {
        Connection con = connectionFA.getConnection();
        String query = "DELETE FROM cliente WHERE id_cliente = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int result = pst.executeUpdate();

            if (result > 0)
                JOptionPane.showMessageDialog(null, "Eliminado exitosamente");
            else
                JOptionPane.showMessageDialog(null, "No se elimino");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se elimino");
        }
    }

    /**
     * Verifica si existe un cliente con la cédula especificada en la base de datos.
     *
     * @param cedula La cédula del cliente a buscar.
     * @return `true` si el cliente existe, `false` en caso contrario.
     */
    public boolean existeCliente(String cedula) {
        Connection conexion = connectionFA.getConnection();
        String query = "SELECT * FROM cliente WHERE cedula = ?";
        try {
            PreparedStatement pst = conexion.prepareStatement(query);
            pst.setString(1, cedula);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifica si existe un cliente con el correo especificado en la base de datos.
     *
     * @param correo El correo del cliente a buscar.
     * @return `true` si el cliente existe, `false` en caso contrario.
     */
    public boolean existeCorreo(String correo) {
        Connection conexion = connectionFA.getConnection();
        String query = "SELECT * FROM cliente WHERE correo = ?";
        try {
            PreparedStatement pst = conexion.prepareStatement(query);
            pst.setString(1, correo);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}