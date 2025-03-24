package Detalle_Financiero;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import Caja.CajaDAO;
import Caja.CajaGUI;
import Conexion.Conexion;
import Pedidos.PedidosGUI;

import java.time.LocalDateTime;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;


public class Detalle_FinancieroGUI {

    private JPanel main;
    private JButton volverButton;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTextField textField4;
    private JTable table1;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JTextField textField5;
    private JComboBox comboBox2;
    private JFrame frame;
    private JFrame parentFrame;
    ResultSet generatedKeys = null;

    private Detalle_FinancieroDAO detalleFinancieroDAO = new Detalle_FinancieroDAO();
    private CajaDAO cajaDAO = new CajaDAO();

    private Conexion conexion = new Conexion();
    PedidosGUI p = new PedidosGUI();
    CajaGUI cj = new CajaGUI();


    public Detalle_FinancieroGUI(JFrame parentFrame)
    {
        textField1.setEditable(false);
        textField1.setVisible(false);
        textField2.setEditable(false);
        textField2.setVisible(false);
        textField3.setVisible(false);
        textField5.setEditable(false);
        showdata();

        this.parentFrame = parentFrame;


        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField4.getText().trim().isEmpty() || comboBox2.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    String tipo_pago = (String) comboBox1.getSelectedItem();
                    int monto = Integer.parseInt(textField4.getText().trim());

                    String tipoOperacion = (String) comboBox2.getSelectedItem();
                    int ingreso = tipoOperacion.equals("Ingreso") ? monto : 0;
                    int egreso = tipoOperacion.equals("Egreso") ? monto : 0;

                    String descripcion = textField5.getText().trim();
                    if (descripcion.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar una descripción.");
                        return;
                    }

                    LocalDateTime fecha_hora = LocalDateTime.now();
                    if(comboBox2.getSelectedItem().toString().equals("Ingreso")){
                        int id_i = p.insertarDetalleFinanciero(tipo_pago, monto, 0, descripcion, String.valueOf(fecha_hora));
                        cj.EnviarDinero(id_i, monto, tipoOperacion);
                    }else{
                        int id = p.insertarDetalleFinanciero(tipo_pago, 0, monto, descripcion, String.valueOf(fecha_hora));
                        cj.EnviarDinero(id, monto, tipoOperacion);
                    }

                    //Detalle_Financiero detalle = new Detalle_Financiero(0, tipo_pago, ingreso, egreso, descripcion, fecha_hora);


                    // Llamar solo una vez
                    //int idFinancieroGenerado = detalleFinancieroDAO.Agregar(detalle);

                    //if (idFinancieroGenerado != -1) {
                        String concepto = tipoOperacion + " - " + descripcion;
                        //cajaDAO.RegistrarMovimiento(concepto, ingreso - egreso, idFinancieroGenerado);

                        clear();
                        showdata();
                        JOptionPane.showMessageDialog(null, "Registro agregado correctamente.");
                    //} else {
                       // JOptionPane.showMessageDialog(null, "Error al agregar el detalle financiero.");
                    //}

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El monto debe ser un número válido.");
                }
            }
        });


        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() ||
                       // textField4.getText().trim().isEmpty() || textField5.getText().trim().isEmpty()) {
                    //JOptionPane.showMessageDialog(null, "Complete todos los campos, incluyendo la descripción.");
                    //return;
                //}

                try {
                    int id_detallefinanciero = Integer.parseInt(textField2.getText().trim());
                    String tipo_pago = (String) comboBox1.getSelectedItem();
                    int monto = Integer.parseInt(textField4.getText().trim());
                    String tipoOperacion = (String) comboBox2.getSelectedItem();
                    String descripcion = textField5.getText().trim();

                    // Validar que la descripción no esté vacía
                    if (descripcion.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar una descripción para la transacción.");
                        return;
                    }

                    // Determinar si es ingreso o egreso
                    int ingreso = tipoOperacion.equals("Ingreso") ? monto : 0;
                    int egreso = tipoOperacion.equals("Egreso") ? monto : 0;


                    LocalDateTime fecha_hora = LocalDateTime.now();


                    Detalle_Financiero detalle = new Detalle_Financiero(id_detallefinanciero, tipo_pago, ingreso, egreso, descripcion, fecha_hora);
                    detalleFinancieroDAO.Actualizar(detalle);

                    // Obtener el ID del movimiento en caja basado en el ID del detalle financiero
                    int idCaja = cajaDAO.ObtenerIdCajaPorIdDetalleFinanciero(id_detallefinanciero);

                    //if (idCaja != -1) {
                        // Actualizar el movimiento en caja con la nueva información
                       // cajaDAO.ActualizarMovimiento(idCaja, tipoOperacion + " - " + descripcion, ingreso - egreso, id_detallefinanciero);
                    //} else {
                        //OptionPane.showMessageDialog(null, "No se encontró un movimiento en caja con ese ID de detalle financiero.");
                   // }

                    clear();
                    showdata();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El monto debe ser un número válido.");
                }
            }
        });


        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField5.setEditable(true);
            }
        });




        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este registro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        int id_detallefinanciero = Integer.parseInt(textField2.getText().trim());

                        // Eliminar el movimiento en caja asociado usando el ID del detalle financiero
                        cajaDAO.EliminarMovimientoPorIdDetalleFinanciero(id_detallefinanciero);


                        detalleFinancieroDAO.Eliminar(id_detallefinanciero);


                        clear();
                        showdata();


                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
                    }
                }
            }
        });



        volverButton.addActionListener(new ActionListener() {
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
                int fila = table1.getSelectedRow();
                if (fila >= 0) {
                    textField2.setText(table1.getValueAt(fila, 0).toString()); // Id-Financiero
                    //textField3.setText(table1.getValueAt(fila, 1).toString()); // Id-Venta
                    comboBox1.setSelectedItem(table1.getValueAt(fila, 1).toString()); // Tipo de Pago
                    comboBox2.setSelectedItem(table1.getValueAt(fila, 2).toString()); // Operación
                    textField4.setText(table1.getValueAt(fila, 3).toString()); // Monto

                    //  Asignar la descripción correctamente sin importar el tipo de operación
                    String descripcion = table1.getValueAt(fila, 4).toString();
                    textField5.setText(descripcion);

                    //  Habilitar el campo de descripción según el tipo de operación seleccionado
                    String tipoOperacion = comboBox2.getSelectedItem().toString();
                    textField5.setEditable(tipoOperacion.equals("Egreso") || tipoOperacion.equals("Ingreso"));


                    textField1.setText(table1.getValueAt(fila, 5).toString()); // Fecha-Hora
                }
            }
        });


    }

    public void showdata() {
        NonEditableTableModel modelo = new NonEditableTableModel();

        // Agregar columnas
        modelo.addColumn("Id-Financiero");
        modelo.addColumn("Tipo de pago");
        modelo.addColumn("Operación");
        modelo.addColumn("Monto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Fecha-Hora");


        table1.setModel(modelo);

        Connection con = conexion.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_detallefinanciero, tipo_pago, ingreso, egreso, descripcion, fecha_hora FROM detalle_financiero");

            while (rs.next()) {
                int ingreso = rs.getInt("ingreso");
                int egreso = rs.getInt("egreso");
                String descripcion = rs.getString("descripcion");
                String tipoOperacion = ingreso > 0 ? "Ingreso" : "Egreso";
                int monto = ingreso > 0 ? ingreso : egreso;

                modelo.addRow(new Object[]{
                        rs.getInt("id_detallefinanciero"),
                        rs.getString("tipo_pago"),
                        tipoOperacion,
                        monto,
                        descripcion,
                        rs.getString("fecha_hora"),


                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
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
    }

    public int insertarDetalleFinanciero(String tipopago, int ingreso, int egreso, String descripcion, String fechah) {
        Connection con = conexion.getConnection();
        PreparedStatement psDetalle = null;


        try {
            String sqlDetalle = "INSERT INTO detalle_financiero (tipo_pago, ingreso, egreso, descripcion, fecha_hora) VALUES (?, ?, ?, ?, ?)";
            psDetalle = con.prepareStatement(sqlDetalle, Statement.RETURN_GENERATED_KEYS);
            psDetalle.setString(1, tipopago);
            psDetalle.setInt(2, ingreso);
            psDetalle.setInt(3, egreso);
            psDetalle.setString(4, descripcion);
            psDetalle.setString(5, fechah);
            psDetalle.executeUpdate();

            generatedKeys = psDetalle.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Devuelve el id generado
            } else {
                throw new SQLException("No se pudo obtener el id generado.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (psDetalle != null) psDetalle.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }return -1;
    }

    public void runFinanciero() {

        frame = new JFrame("Data Base Game");
        frame.setContentPane(this.main);
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(570, 450);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}


