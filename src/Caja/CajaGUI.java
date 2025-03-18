package Caja;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Conexion.Conexion;
import Detalle_Financiero.Detalle_FinancieroGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;

public class CajaGUI {
    private JPanel main;
    private JButton volverButton;
    private JTable table1;
    private JTextField textField1;

    private JFrame frame;
    private JFrame parentFrame;

    private CajaDAO cajaDAO = new CajaDAO();
    private Conexion conexion = new Conexion();


    public CajaGUI(JFrame parentFrame) {

        this.parentFrame = parentFrame;

        textField1.setEditable(false);
        showdata();

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parentFrame != null) {
                    parentFrame.setVisible(true);
                }
                frame.dispose();
            }
        });

    }


    public void showdata() {

        NonEditableTableModel modelo = new NonEditableTableModel();

        modelo.addColumn("ID Caja");
        modelo.addColumn("ID Detalle Financiero");
        modelo.addColumn("Concepto");
        modelo.addColumn("Valor");

        table1.setModel(modelo);

        Connection con = conexion.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_caja, id_detallefinanciero, concepto, valor FROM caja");

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_caja"),
                        rs.getInt("id_detallefinanciero"),
                        rs.getString("concepto"),
                        rs.getInt("valor"),

                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        actualizarSaldoActual();
    }



    public void actualizarSaldoActual() {
        textField1.setText("" + cajaDAO.ObtenerSaldoActual());
    }

    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }



    public void runCaja() {
        frame = new JFrame("Gestión de Caja");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(570, 450);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}