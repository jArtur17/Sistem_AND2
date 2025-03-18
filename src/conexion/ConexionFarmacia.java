package conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionFarmacia {

    public Connection getConnection() {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/drogueria", "root", "root");
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos"+e.toString());
        }

        return con;

    }
}
