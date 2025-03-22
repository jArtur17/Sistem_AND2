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

    public HistorialPedidos() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void Historialordenes() {
        Conexion conR = new Conexion();
        DefaultTableModel orden = new DefaultTableModel();
        orden.addColumn("id_orden");
        orden.addColumn("id_cliente");
        orden.addColumn("id_mesa");
        orden.addColumn("estado");
        orden.addColumn("total");
        tablahistorial.setModel(orden);
        tablahistorial.getColumnModel().getColumn(3).setCellEditor(new EstadoCellEditor());

        Connection con = conR.getConnection();

        String[] Arreglo = new String[5];

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_orden, id_cliente, id_mesa, estado, total FROM ordenes");

            while (rs.next()) {
                Arreglo[0] = rs.getString(1);
                Arreglo[1] = rs.getString(2);
                Arreglo[2] = rs.getString(3);
                Arreglo[3] = rs.getString(4);
                Arreglo[4] = rs.getString(5);


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




}

