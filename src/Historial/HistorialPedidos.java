package Historial;
import Conexion.Conexion;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class HistorialPedidos {
    private JButton button1;
    private JTable tablahistorial;
    private JPanel Panel;
    Conexion conR = new Conexion();

    public HistorialPedidos() {
        //llamar los pedidos en la tabla
        Historialordenes();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        tablahistorial.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                System.out.println("Evento TableModelEvent detectado.");//e = objeto del tablemodelevent
                if (e.getType() == TableModelEvent.UPDATE) { //verifica si es un tipo actualización en este caso si la el pedido pasó a enviado, getTye obtiene que tipo de evento
                    int fila = e.getFirstRow(); //fila, almacena el num de fila. e= obtener la fila modificada
                    int columna = e.getColumn();//guarda el num de la columna. obtiene la colum actualizada

                    if (columna == 4) { // Columna de estado
                        DefaultTableModel model = (DefaultTableModel) tablahistorial.getModel();
                        String nuevoEstado = model.getValueAt(fila, columna).toString();
                        int idPedido = Integer.parseInt(model.getValueAt(fila, 0).toString());

                        actualizarEstado(idPedido, nuevoEstado);

                    }

                }
            }
        });
    }

    public void Historialordenes() {
        DefaultTableModel orden = new DefaultTableModel();
        orden.addColumn("id_pedido");
        orden.addColumn("id_cliente");
        orden.addColumn("fecha_hora");
        orden.addColumn("estado");
        orden.addColumn("metodo_pago");
        orden.addColumn("total");
        tablahistorial.setModel(orden);
        tablahistorial.getColumnModel().getColumn(3).setCellEditor(new EstadoCellEditor());

        Connection con = conR.getConnection();

        String[] Arreglo = new String[6];

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_pedido, id_cliente, fecha_hora, estado, metodo_pago, total FROM pedidos");

            while (rs.next()) {
                Arreglo[0] = rs.getString(1);
                Arreglo[1] = rs.getString(2);
                Arreglo[2] = rs.getString(3);
                Arreglo[3] = rs.getString(4);
                Arreglo[4] = rs.getString(5);
                Arreglo[5] = rs.getString(6);


                orden.addRow(Arreglo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class EstadoCellEditor extends DefaultCellEditor {
        public EstadoCellEditor() {
            super(new JComboBox<>(new String[]{"Entregado", "Enviado"}));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JComboBox<String> comboBox = (JComboBox<String>) getComponent();
            comboBox.setSelectedItem(value);
            return comboBox;
        }
    }

    private void actualizarEstado(int idPedido, String nuevoEstado) {
        Connection con = conR.getConnection();

        String sql = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Estado actualizado correctamente en la base de datos.");
                JOptionPane.showMessageDialog(null, "Estado actualizado correctamente.");
                Historialordenes(); // Recargar datos de la tabla
            } else {
                System.out.println("No se pudo actualizar el estado en la base de datos.");
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Historial Pedidos");
        frame.setContentPane(new HistorialPedidos().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,700);
        frame.setResizable(false);
        frame.setVisible(true);
    }




}

