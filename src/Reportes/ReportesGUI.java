package Reportes;

import Conexion.Conexion;
import Producto.ProductoGUI;

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
 * La clase `ReportesGUI` proporciona una interfaz gráfica para la generación de reportes.
 *
 * @author nicolle
 */
public class ReportesGUI {
    /** El panel principal que contiene todos los componentes de la GUI. */
    private JPanel main;
    /** Botón para volver al frame padre. */
    private JButton volverButton;
    /** Tabla para mostrar los datos de los reportes. */
    private JTable table1;
    /** Botones para generar reportes diarios, semanales, mensuales y de control de stock. */
    private JButton diariasButton;
    private JButton semanalesButton;
    private JButton mensualesButton;
    private JButton controlStockButton;
    private JButton actualizarButton;
    private JButton productosCaducadosButton;

    /** Objeto DAO para interactuar con la base de datos de reportes. */
    private ReportesDAO reportesDAO = new ReportesDAO();

    /** El frame principal de la GUI. */
    private JFrame frame;
    /** El frame padre de la GUI. */
    private JFrame parentFrame;

    /** Objeto para la conexión a la base de datos. */
    private Conexion conexion = new Conexion();

    /**
     * Constructor de `ReportesGUI`.
     *
     * @param parentFrame El frame padre de esta GUI.
     */
    public ReportesGUI(JFrame parentFrame) {
        actualizarButton.setVisible(false);
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
                actualizarButton.setVisible(false);
                reportesDAO.Diarias();
                showdata();

            }
        });

        semanalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarButton.setVisible(false);
                reportesDAO.Semanales();
                ReportesSemanales();
            }
        });

        mensualesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarButton.setVisible(false);
                reportesDAO.Mensuales();
                ReportesMensuales();

            }
        });

        productosCaducadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarButton.setVisible(true);
                reportesDAO.Caducados();
                ProdCaducados();
            }
        });


        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarButton.setVisible(false);
                if (parentFrame != null){
                    parentFrame.setVisible(true);
                }
                frame.dispose();
            }
        });

        controlStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarButton.setVisible(true);
                reportesDAO.StockMin();
                StockMinimo();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                ProductoGUI p = new ProductoGUI(frame);
                p.runProducto();
            }
        });
    }




    /**
     * Aplica estilos visuales a los componentes de la GUI.
     */
    public void aplicarEstilos() {
        main.setBackground(Color.DARK_GRAY);

        controlStockButton.setBackground(new Color(0, 51, 102));
        volverButton.setBackground(new Color(0, 51, 102));
        diariasButton.setBackground(new Color(0, 51, 102));
        semanalesButton.setBackground(new Color(0, 51, 102));
        mensualesButton.setBackground(new Color(0, 51, 102));
        actualizarButton.setBackground(new Color(0, 51, 102));
        productosCaducadosButton.setBackground(new Color(0, 51, 102));

        controlStockButton.setForeground(Color.WHITE);
        diariasButton.setForeground(Color.WHITE);
        semanalesButton.setForeground(Color.WHITE);
        mensualesButton.setForeground(Color.WHITE);
        actualizarButton.setForeground(Color.WHITE);
        volverButton.setForeground(Color.WHITE);
        productosCaducadosButton.setForeground(Color.WHITE);

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
     * Muestra los datos de ventas diarias en la tabla.
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

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when displaying data: " + e.getMessage());
        }
    }

    /**
     * Muestra los datos de stock mínimo en la tabla.
     */
    public void StockMinimo() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Categoria");
        modelo.addColumn("Stock");
        modelo.addColumn("Stock minimo");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportesDAO.StockMin();

            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("id_producto");
                    String nombre = rs.getString("nombre");
                    String categoria = rs.getString("categoria");
                    int stock = rs.getInt("stock");
                    int stock_minimo = rs.getInt("stock_minimo");

                    modelo.addRow(new Object[]{
                            id,
                            nombre,
                            categoria,
                            stock,
                            stock_minimo
                    });
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when displaying stock data: " + e.getMessage());
        }
    }

    public void ProdCaducados() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Categoria");
        modelo.addColumn("Fecha de Vencimiento");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportesDAO.Caducados();

            if (rs != null) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String categoria = rs.getString("categoria");
                    String fecha_vencimiento = rs.getString("fecha_vencimiento");

                    modelo.addRow(new Object[]{
                            nombre,
                            categoria,
                            fecha_vencimiento,
                    });
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when displaying productos data: " + e.getMessage());
        }
    }

    /**
     * Muestra los datos de ventas semanales en la tabla.
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
     * Muestra los datos de ventas mensuales en la tabla.
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

    /**
     * Clase interna para un modelo de tabla no editable.
     */
    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /**
     * Inicia la GUI de reportes.
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

    /**
     * Clase interna para el panel de fondo con una imagen.
     */
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