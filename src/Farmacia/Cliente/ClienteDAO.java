package Proyecto.Cliente;

import connectionFA.ConnectionFA;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {
        private ConnectionFA connectionFA = new ConnectionFA();


        public void agregar(Cliente cliente)
        {
            Connection con = connectionFA.getConnection();
            String query = "INSERT INTO cliente(cedula, nombre, telefono, correo, direccion) VALUES (?,?,?,?,?)";

            try
            {
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, cliente.getCedula());
                pst.setString(2, cliente.getNombre());
                pst.setString(3, cliente.getTelefono());
                pst.setString(4, cliente.getCorreo());
                pst.setString(5, cliente.getDireccion());


                int result = pst.executeUpdate();

                if (result>0)
                    JOptionPane.showMessageDialog(null,"Añadido con éxito");
                else
                    JOptionPane.showMessageDialog(null,"No añadido");

            }

            catch (SQLException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"no añadido");

            }
        }

        public void actualizar(Cliente cliente){
            Connection con = connectionFA.getConnection();

            String query = "UPDATE `cliente` SET cedula = ?, nombre = ?, telefono = ?, correo = ?, direccion = ? WHERE id_cliente = ?";


            try {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1,cliente.getCedula());
                stmt.setString(2,cliente.getNombre());
                stmt.setString(3,cliente.getTelefono());
                stmt.setString(4,cliente.getCorreo());
                stmt.setString(5,cliente.getDireccion());
                stmt.setInt(6,cliente.getId_cliente());


                int result = stmt.executeUpdate();

                if (result>0)
                    JOptionPane.showMessageDialog(null,"Cliente actualizado exitosamente");
                else
                    JOptionPane.showMessageDialog(null,"Cliente no encontrado");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        public void eliminar(int id)
        {
            Connection con = connectionFA.getConnection();
            String query = "DELETE FROM cliente WHERE id_cliente = ?";

            try
            {
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, id);

                int result = pst.executeUpdate();

                if (result>0)
                    JOptionPane.showMessageDialog(null,"Eliminado exitosamente");
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

