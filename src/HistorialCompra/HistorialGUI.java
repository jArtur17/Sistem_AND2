import javax.swing.JTable;
import ConexionFarmacia.Conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

private Conexion connectionFA = new Conexion();
private JTable tablaHistorial;
private JTable table1;
private JFrame frame;
private JFrame parentFrame;


    public void showdata()
    {
        Conexion connectionFA = new Conexion();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id_pedido");
        modelo.addColumn("id_cliente");
        modelo.addColumn("fecha_hora");
        modelo.addColumn("estado");
        modelo.addColumn("metodo_pago");
        modelo.addColumn("total");

        tablaHistorial.setModel(modelo);


        String[] dato = new String[6];
        Connection con = connectionFA.getConnection();

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pedidos");

            while (rs.next())
            {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);

                modelo.addRow(dato);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void main() {
        frame = new JFrame("Historial");
        //frame.setContentPane(this.main);
        //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600,600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    public  void runHistorialCompra(){



    }
