package Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión con la base de datos MySQL.
 * @author Arturo
 */
public class Conexion {

    /**
     * Establece una conexión con la base de datos.
     *
     * @return un objeto Connection si la conexión es exitosa, o null si ocurre un error.
     */
    public Connection getConnection() {
        Connection con = null;

        try {
            // Intenta establecer la conexión con la base de datos MySQL
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/drogueria", "root", "root");
        } catch (SQLException e) {
            // Muestra un mensaje de error si la conexión falla
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e.toString());
        }

        return con;
    }
}