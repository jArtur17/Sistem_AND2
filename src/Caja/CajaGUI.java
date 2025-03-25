package Caja;

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
import Pedidos.PedidosGUI;

public class CajaGUI {
    private JPanel main;
    private JButton volverButton;
    private JTable table1;
    private JTextField textField1;
    private JTextField Ventasefectivotxt;
    int sum_total = 0;

    private JFrame frame;
    private JFrame parentFrame;

    private CajaDAO cajaDAO = new CajaDAO();
    private Conexion conexion = new Conexion();
    private PedidosGUI p = new PedidosGUI();

    public CajaGUI() {

    }

    public void EnviarDinero(int idDetalleFinanciero, int total, String concepto) {
        Connection con = conexion.getConnection();
        PreparedStatement psCaja = null;

        try {
            int saldoActual = obtenerSaldoActual(); // Obtener saldo antes de hacer la operación

            if (concepto.equalsIgnoreCase("Egreso") && total > saldoActual) {
                System.out.println("Saldo insuficiente para realizar el egreso.");
                return; // No permite continuar si no hay saldo suficiente
            }

            // Si es ingreso, sumamos; si es egreso, restamos
            sum_total = concepto.equalsIgnoreCase("Ingreso") ? sum_total + total : sum_total - total;

            // Actualizar la interfaz
            textField1.setText(String.valueOf(sum_total));

            String sqlCaja = "INSERT INTO caja (id_detallefinanciero, concepto, valor) VALUES (?, ?, ?)";
            psCaja = con.prepareStatement(sqlCaja);
            psCaja.setInt(1, idDetalleFinanciero);
            psCaja.setString(2, concepto);
            psCaja.setInt(3, total);
            psCaja.executeUpdate();

            System.out.println("Operación realizada: " + concepto + " de " + total);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (psCaja != null) psCaja.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    public int obtenerSaldoActual() {
        String sql = "SELECT SUM(CASE WHEN concepto = 'Ingreso' THEN valor ELSE -valor END) AS saldo_actual FROM caja";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("saldo_actual");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Si hay error, retorna 0
    }



    public void actualizarSaldo(int cantidad, boolean esIngreso) {
        int saldoActual = obtenerSaldoActual();
        int nuevoSaldo = esIngreso ? saldoActual + cantidad : saldoActual - cantidad;

        String sql = "INSERT INTO caja (valor) VALUES (?)"; // Guarda el nuevo saldo

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoSaldo);
            ps.executeUpdate();
            textField1.setText(String.valueOf(nuevoSaldo)); // Actualiza el JTextField
            JOptionPane.showMessageDialog(null, "La caja se ha actualizado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public CajaGUI(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        textField1.setEditable(false);
        //showdata();




        // ** Configurar estilo de la tabla **
        //table1.setBackground(Color.WHITE); // Fondo de las celdas blanco
        //table1.setForeground(Color.BLACK); // Texto negro
        //table1.setGridColor(Color.GRAY); // Bordes de la tabla

        // ** Encabezado de la tabla personalizado **
        //JTableHeader header = table1.getTableHeader();
        //header.setBackground(new Color(0, 51, 102)); // Azul oscuro
        //header.setForeground(Color.WHITE); // Letras blancas
        //header.setFont(new Font("Arial", Font.BOLD, 14));

        // ** Centrar texto en celdas de la tabla **
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //for (int i = 0; i < table1.getColumnCount(); i++) {
            //table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        //}

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
    /*
    public void showdata() {
        NonEditableTableModel modelo = new NonEditableTableModel();

        modelo.addColumn("Saldo Inicial");
        modelo.addColumn("Total de ventas en efectivo");
        modelo.addColumn("Total de egresos");
        modelo.addColumn("Total de ingresos");

        table1.setModel(modelo);
        actualizarSaldoActual();
    }

     */

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

    public void actualizarTotal() {
        Connection con = conexion.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT SUM(valor) AS total FROM caja";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                sum_total = rs.getInt("total");
                textField1.setText(String.valueOf(sum_total));
                SwingUtilities.invokeLater(() -> textField1.setText(String.valueOf(sum_total)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
