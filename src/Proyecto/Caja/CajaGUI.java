package Proyecto.Caja;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;
import Conexion.Conexion;

public class CajaGUI {
    private JPanel main;
    private JButton volverButton;
    private JTable table1;
    private JTextField textField1;

    private JFrame frame;
    private JFrame parentFrame;

    private Caja.CajaDAO cajaDAO = new Caja.CajaDAO();
    private Conexion conexion = new Conexion();

    public CajaGUI(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        textField1.setEditable(false);
        showdata();

        // ** Configurar estilo de la tabla **
        table1.setBackground(Color.WHITE); // Fondo de las celdas blanco
        table1.setForeground(Color.BLACK); // Texto negro
        table1.setGridColor(Color.GRAY); // Bordes de la tabla

        // ** Encabezado de la tabla personalizado **
        JTableHeader header = table1.getTableHeader();
        header.setBackground(new Color(0, 51, 102)); // Azul oscuro
        header.setForeground(Color.WHITE); // Letras blancas
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // ** Centrar texto en celdas de la tabla **
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table1.getColumnCount(); i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // ** Estilo del botón "Volver" **
        volverButton.setBackground(new Color(0, 51, 102)); // Azul oscuro
        volverButton.setForeground(Color.WHITE); // Texto blanco
        volverButton.setFocusPainted(false);
        volverButton.setBorderPainted(false);

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverButton.setBackground(new Color(51, 153, 255)); // Azul claro al hacer clic
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

        // ** Cargar imagen de fondo **
        FondoPanel fondoPanel = new FondoPanel();
        main.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(main, BorderLayout.CENTER);

        // ** Cargar icono desde resources/imagenes/ **
        URL iconoURL = getClass().getClassLoader().getResource("imagenes/img.png");
        if (iconoURL != null) {
            ImageIcon icono = new ImageIcon(iconoURL);
            frame.setIconImage(icono.getImage());
        } else {
            System.out.println("⚠ ERROR: No se encontró la imagen icono_medicina.png");
        }

        frame.setContentPane(fondoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    // ** Clase interna para dibujar el fondo con imagen y degradado **
    class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/img_1.png");
            if (imagenURL != null) {
                this.imagenFondo = new ImageIcon(imagenURL).getImage();
            } else {
                System.out.println("⚠ ERROR: No se encontró la imagen fondo_drogueria.jpg");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // ** Dibujar imagen de fondo **
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            } else {
                // ** Si no hay imagen, dibujar degradado azul **
                GradientPaint gp = new GradientPaint(0, 0, new Color(51, 153, 255), getWidth(), getHeight(), new Color(0, 51, 102));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}
