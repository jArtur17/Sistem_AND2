package Historial;
import Conexion.Conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HistorialPedidos {
    private JButton button1;
    private JTable tablahistorial;
    private JPanel Panel;

    public HistorialPedidos() {
        //llamar los pedidos en la tabla
        Historialordenes();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void Historialordenes() {
        Conexion conR = new Conexion();
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

