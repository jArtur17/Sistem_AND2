package Reportes;

import Conexion.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Nicolle
 * @version 1.0
 */

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

    /**
     * Set the GUI to be used to show the reportes.
     *
     * @param parentFrame
     */
    public ReportesGUI(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        // Cambiar color y fuente de los labels
        for (Component c : main.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        aplicarEstilos();

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

    /**
     * AplicarEstilos.
     */
    public void aplicarEstilos() {
        main.setBackground(Color.DARK_GRAY);

        volverButton.setBackground(new Color(0, 51, 102));
        diariasButton.setBackground(new Color(0, 51, 102));
        semanalesButton.setBackground(new Color(0, 51, 102));
        mensualesButton.setBackground(new Color(0, 51, 102));

        diariasButton.setForeground(Color.WHITE);
        semanalesButton.setForeground(Color.WHITE);
        mensualesButton.setForeground(Color.WHITE);
        volverButton.setForeground(Color.WHITE);

        JTableHeader header = table1.getTableHeader();
        header.setBackground(new Color(0, 51, 102));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table1.getColumnCount(); i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    /**
     * Show the data
     */
    public void showdata() {
        NonEditableTableModel modelo = new NonEditableTableModel();
        modelo.addColumn("fecha_hora");
        modelo.addColumn("Ventas Diarias");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportesDAO.Diarias();

            if (rs != null) {
                while (rs.next()) {
                    String fecha_hora = rs.getString("fecha_hora");
                    int ventaDiaria = rs.getInt("venta_diaria");

                    modelo.addRow(new Object[]{
                            fecha_hora,
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

    /**
     * Reportes semanales.
     */
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

    /**
     * Reportes monthly data.
     */
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


    /**
     * Run the report.
     */
    public void runReport() {

        frame = new JFrame("Reportes");
        frame.setContentPane(this.main);
        frame.pack();

        FondoPanel fondoPanel = new FondoPanel();
        main.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(main, BorderLayout.CENTER);

        URL iconoURL = getClass().getClassLoader().getResource("imagenes/img_14.png");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }

        frame.setContentPane(fondoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,650);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/img_15.png");
            if (imagenURL != null) {
                this.imagenFondo = new ImageIcon(imagenURL).getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}



