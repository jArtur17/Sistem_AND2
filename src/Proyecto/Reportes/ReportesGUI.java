package Proyecto.Reportes;

import Conexion.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportesGUI {
    private JPanel main;
    private JButton volverButton;
    private JTable table1;
    private JButton diariasButton;
    private JButton semanalesButton;
    private JButton mensualesButton;

    private ReportesDAO reportesDAO = new ReportesDAO();

    private JFrame frame;
    private JFrame parentFrame;

    private Conexion conexion = new Conexion();

    public ReportesGUI(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        diariasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportesDAO.Diarias();
                showdata();

            }
        });

        semanalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportesDAO.Semanales();
                ReportesSemanales();
            }
        });

        mensualesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportesDAO.Mensuales();
                ReportesMensuales();

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
    }
    public void showdata() {
        NonEditableTableModel modelo = new NonEditableTableModel();
        modelo.addColumn("Fecha");
        modelo.addColumn("Ventas Diarias");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportesDAO.Diarias();

            if (rs != null) {
                while (rs.next()) {
                    String fecha = rs.getString("fecha");
                    int ventaDiaria = rs.getInt("venta_diaria");

                    modelo.addRow(new Object[]{
                            fecha,
                            ventaDiaria
                    });
                }

                // Cierra el ResultSet después de usarlo
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when displaying data: " + e.getMessage());
        }
    }

    public void ReportesSemanales() {
        NonEditableTableModel modelo = new NonEditableTableModel();
        modelo.addColumn("Semana");
        modelo.addColumn("Inicio de Semana");
        modelo.addColumn("Fin de semana");
        modelo.addColumn("Venta Semanal");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportesDAO.Semanales();

            if (rs != null) {
                while (rs.next()) {
                    String semana = rs.getString("semana");
                    String inicioSemana = rs.getString("inicio_semana");
                    String finSemana = rs.getString("fin_semana");
                    int ventaSemanal = rs.getInt("venta_semanal");

                    modelo.addRow(new Object[]{
                            semana,
                            inicioSemana,
                            finSemana,
                            ventaSemanal
                    });
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when displaying weekly data: " + e.getMessage());
        }
    }

    public void ReportesMensuales() {
        NonEditableTableModel modelo = new NonEditableTableModel();
        modelo.addColumn("Año");
        modelo.addColumn("Mes");
        modelo.addColumn("Venta Mensual");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportesDAO.Mensuales();

            if (rs != null) {
                while (rs.next()) {
                    int año = rs.getInt("año");
                    int mes = rs.getInt("mes");
                    int ventaMensual = rs.getInt("venta_mensual");

                    modelo.addRow(new Object[]{
                            año,
                            mes,
                            ventaMensual
                    });
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error displaying monthly data: " + e.getMessage());
        }
    }


    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }




    public void runReport() {

        frame = new JFrame("Data Base Game");
        frame.setContentPane(this.main);
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(640,450);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}



