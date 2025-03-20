package Farmacia.Cliente;

import Conexion.Conexion;
import Farmacia.Cliente.ClienteDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteGUI {
    private JPanel main;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTable table1;
    private JButton registrarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton BackButton;
    private JFrame frame;
    private JFrame parentFrame;
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Conexion cf = new Conexion();

    int rows = 0;

    public ClienteGUI(JFrame parentFrame)
    {
        textField1.setEditable(false);
        textField1.setVisible(false);
        showdata();

        this.parentFrame = parentFrame;

        Dimension backButtonSize = new Dimension(86, 23);
        BackButton.setPreferredSize(backButtonSize);
        BackButton.setMinimumSize(backButtonSize);
        BackButton.setMaximumSize(backButtonSize);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                } else {
                    String cedula = textField2.getText();
                    String nombre = textField3.getText();
                    String telefono = textField5.getText();
                    String correo = textField4.getText();
                    String direccion = textField6.getText();

                    if (!telefono.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The phone field should only contain numbers");
                        return;
                    }

                    Cliente cliente = new Cliente(0, cedula, nombre, telefono, correo, direccion);
                    clienteDAO.agregar(cliente);
                    clear();
                    showdata();
                }
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                }else{
                    String cedula = textField2.getText();
                    String nombre = textField3.getText();
                    String telefono = textField5.getText();
                    String correo = textField4.getText();
                    String direccion = textField6.getText();


                    int id_cliente = Integer.parseInt(textField1.getText());

                    if (!telefono.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo de teléfono solo debe contener números");
                        return;
                    }

                    Cliente cliente = new Cliente(id_cliente, cedula, nombre, telefono, correo, direccion);
                    clienteDAO.actualizar(cliente);
                    clear();
                    showdata();
                }

            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Completa los campos");
                }else{
                    int id_cliente = Integer.parseInt(textField1.getText());
                    clienteDAO.eliminar(id_cliente);

                    clear();
                    showdata();
                }

            }
        });

        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parentFrame != null){
                    parentFrame.setVisible(true);
                }
                frame.dispose();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);

                int selectedRows = table1.getSelectedRow();

                if(selectedRows>=0)
                {
                    textField1.setText((String)table1.getValueAt(selectedRows,0));
                    textField2.setText((String)table1.getValueAt(selectedRows,1));
                    textField3.setText((String)table1.getValueAt(selectedRows,2));
                    textField4.setText((String)table1.getValueAt(selectedRows,4));
                    textField5.setText((String)table1.getValueAt(selectedRows,3));
                    textField6.setText((String)table1.getValueAt(selectedRows,5));


                    rows = selectedRows;
                }
            }
        });
    }

    public void showdata()
    {
        NonEditableTableModel modelo = new NonEditableTableModel();

        modelo.addColumn("Id_Cliente");
        modelo.addColumn("Cedula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Numero");
        modelo.addColumn("Coreeo");
        modelo.addColumn("Direccion");

        table1.setModel(modelo);

        String[] dato = new String[6];
        Connection con = cf.getConnection();

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cliente");

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


    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }


    public void clear()
    {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
    }

    public void runCliente(){


        frame = new JFrame("Data Base Game");
        frame.setContentPane(this.main);
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600,600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}



