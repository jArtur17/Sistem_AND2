package Proyecto.Producto;

import connectionFA.ConnectionFA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ProductoGUI {
    private JPanel main;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton registrarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton BackButton;
    private JTable table1;
    private JTextField textField7;
    private JTextField textField9;
    private JTextField textField8;
    private JTextField textField10;
    private JFrame frame;
    private JFrame parentFrame;
    private ProductoDAO productoDAO = new ProductoDAO();
    private ConnectionFA connectionFA = new ConnectionFA();

    int rows = 0;

    public ProductoGUI(JFrame parentFrame)
    {
        textField1.setEditable(false);
        textField1.setVisible(false);

        this.parentFrame = parentFrame;

        Dimension backButtonSize = new Dimension(86, 23);
        BackButton.setPreferredSize(backButtonSize);
        BackButton.setMinimumSize(backButtonSize);
        BackButton.setMaximumSize(backButtonSize);

        obtainInvent();

            registrarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verificar si algún campo está vacío
                    if (textField2.getText().trim().isEmpty() ||
                            textField3.getText().trim().isEmpty() ||
                            textField4.getText().trim().isEmpty() ||
                            textField5.getText().trim().isEmpty() ||
                            textField6.getText().trim().isEmpty() ||
                            textField7.getText().trim().isEmpty() ||
                            textField8.getText().trim().isEmpty() ||
                            textField9.getText().trim().isEmpty() ||
                            textField10.getText().trim().isEmpty()) {

                        JOptionPane.showMessageDialog(null, "Complete todos los campos");
                        return;
                    }

                    // Obtener valores de los campos
                    String nombre = textField2.getText();
                    String categoria = textField3.getText();
                    int stock = Integer.parseInt(textField4.getText());
                    int stock_minimo = Integer.parseInt(textField5.getText());
                    Date fecha_vencimiento = Date.valueOf(textField6.getText());
                    String indicaciones = textField9.getText();
                    String almacen = textField8.getText();
                    String lote = textField10.getText();

                    String precio_t = (textField5.getText());

                    if (!precio_t.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo de precio solo debe contener números");
                        return;
                    }

                    int precio_unitario = Integer.parseInt(precio_t);

                    Producto producto = new Producto(0,  nombre, categoria, stock, stock_minimo, precio_unitario, fecha_vencimiento, indicaciones, almacen, lote);
                    productoDAO.agregar(producto);

                    clear();
                    obtainInvent();
                }
            });




        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si algún campo obligatorio está vacío
                if (textField2.getText().trim().isEmpty() ||
                        textField3.getText().trim().isEmpty() ||
                        textField4.getText().trim().isEmpty() ||
                        textField5.getText().trim().isEmpty() ||
                        textField6.getText().trim().isEmpty() ||
                        textField7.getText().trim().isEmpty() ||
                        textField8.getText().trim().isEmpty() ||
                        textField9.getText().trim().isEmpty() ||
                        textField10.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    // Obtener valores de los campos
                    int id_producto = Integer.parseInt(textField1.getText());
                    String nombre = textField2.getText();
                    String categoria = textField3.getText();
                    int stock = Integer.parseInt(textField4.getText());
                    int stock_minimo = Integer.parseInt(textField7.getText());
                    Date fecha_vencimiento = Date.valueOf(textField6.getText());
                    String indicaciones = textField9.getText();
                    String almacen = textField8.getText();
                    String lote = textField10.getText();

                    String precio_t = (textField5.getText());

                    if (!precio_t.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo de precio solo debe contener números");
                        return;
                    }

                    int precio_unitario = Integer.parseInt(precio_t);


                    Producto producto = new Producto(id_producto, nombre, categoria, stock, stock_minimo, precio_unitario, fecha_vencimiento, indicaciones, almacen, lote);
                    productoDAO.actualizar(producto);

                    clear();
                    obtainInvent();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error al convertir un campo numérico. Verifique los datos ingresados.");
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() ||
                        textField3.getText().trim().isEmpty() ||
                        textField4.getText().trim().isEmpty() ||
                        textField5.getText().trim().isEmpty() ||
                        textField6.getText().trim().isEmpty() ||
                        textField7.getText().trim().isEmpty() ||
                        textField8.getText().trim().isEmpty() ||
                        textField9.getText().trim().isEmpty() ||
                        textField10.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete all fields");
                }else{
                    int id_producto = Integer.parseInt(textField1.getText());
                    productoDAO.eliminar(id_producto);

                    clear();
                    obtainInvent();
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

                if(selectedRows >= 0)
                {
                    textField1.setText((String) table1.getValueAt(selectedRows,0));
                    textField2.setText((String) table1.getValueAt(selectedRows,1));
                    textField3.setText((String) table1.getValueAt(selectedRows,2));
                    textField4.setText((String) table1.getValueAt(selectedRows,3));
                    textField7.setText((String) table1.getValueAt(selectedRows,4));
                    textField5.setText((String) table1.getValueAt(selectedRows,5));
                    textField6.setText((String) table1.getValueAt(selectedRows,6));
                    textField9.setText((String) table1.getValueAt(selectedRows,7));
                    textField8.setText((String) table1.getValueAt(selectedRows,8));
                    textField10.setText((String) table1.getValueAt(selectedRows,9));


                    rows = selectedRows;
                }


            }
        });
    }

    public void obtainInvent()
    {
        NonEditableTableModel modeloa = new NonEditableTableModel();
        table1.setDefaultEditor(Object.class, null);


        modeloa.addColumn("id_product");
        modeloa.addColumn("Nombre");
        modeloa.addColumn("Categoria");
        modeloa.addColumn("Stock");
        modeloa.addColumn("Stock_Minimo");
        modeloa.addColumn("Precio_Unitario");
        modeloa.addColumn("Fecha Vencimiento");
        modeloa.addColumn("Indicaciones");
        modeloa.addColumn("Almacen");
        modeloa.addColumn("Lote");

        table1.setModel(modeloa);

        String[] dato = new String[10];

        Connection con = connectionFA.getConnection();

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM producto");


            while (rs.next())
            {

                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);
                dato[6] = rs.getString(7);
                dato[7] = rs.getString(8);
                dato[8] = rs.getString(9);
                dato[9] = rs.getString(10);

                modeloa.addRow(dato);
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
        textField7.setText("");
        textField8.setText("");
        textField9.setText("");
        textField10.setText("");
    }
    public  void runProducto(){


        frame = new JFrame("Data Base Game");
        frame.setContentPane(this.main);
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600,600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}

