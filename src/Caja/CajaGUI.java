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
/**
 * @author Nicolle
 * @version 1.0
 */
public class CajaGUI {
    private JPanel main;
    private JButton volverButton;
    private JTable table1;
    private JTextField saldoactualtxt;
    int sum_total = 0;

    private JFrame frame;
    private JFrame parentFrame;

    private CajaDAO cajaDAO = new CajaDAO();
    private Conexion conexion = new Conexion();
    //private PedidosGUI p = new PedidosGUI();


    public CajaGUI() {


    }


    /**
     * Enviar dinero enviado a la caja con éx
     */
    public void EnviarDinero(int Detallef, String concepto, int total){
        sum_total += total;
        saldoactualtxt.setText(String.valueOf(sum_total));
        System.out.println(sum_total);

        Connection con = conexion.getConnection();
        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO caja (id_detallefinanciero, concepto, valor) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);

            ps.setInt(1, Detallef);
            ps.setString(2, concepto);
            ps.setInt(3, total);

            ps.executeUpdate();


            //System.out.println("Dinero enviado a la caja con éxito.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * CajaGUI de la tabla.
     *
     * @param parentFrame
     * @return
     */
    public CajaGUI(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        saldoactualtxt.setEditable(false);
        showdata();
        //actualizarTotal();
        actualizarSaldoEnTextField();

        // Cambiar color y fuente de los labels
        for (Component c : main.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        aplicarEstilos();




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

    /**
     * This method is called when the actualizar is done.
     */
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
                saldoactualtxt.setText(String.valueOf(sum_total));
                SwingUtilities.invokeLater(() -> saldoactualtxt.setText(String.valueOf(sum_total)));
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

    /**
     * Returns the total number of SALDOs in the detalle.
     *
     * @return
     */
    public int obtenerSaldoTotal() {
        int saldo = 0;
        String sql = "SELECT COALESCE(SUM(Ingreso) - SUM(Egreso), 0) AS saldo_actual FROM detalle_financiero";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                saldo = rs.getInt("saldo_actual");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return saldo;
    }


    /**
     * Muestra el saldo en el JTextField
     */
    public void actualizarSaldoEnTextField() {
        int saldo = obtenerSaldoTotal();
        saldoactualtxt.setText(String.valueOf(saldo)); // Muestra el saldo en el JTextField
    }

    /**
     * AplicarEstilos.
     */
    public void aplicarEstilos() {
        main.setBackground(Color.DARK_GRAY);

        volverButton.setBackground(new Color(0, 51, 102));


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

    }

    /**
     * Returns the Obtener total of the obtener.
     *
     * @return the Ob
     */
    public int Obtenertotal(){
        int total = obtenerSaldoTotal();
        return total;
    }


    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /**
     * Run Caja.
     */
    public void runCaja() {
        frame = new JFrame("Gestión de Caja");

        // ** Cargar imagen de fondo **
        FondoPanel fondoPanel = new FondoPanel();
        main.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(main, BorderLayout.CENTER);

        // ** Cargar icono desde resources/imagenes/ **
        URL iconoURL = getClass().getClassLoader().getResource("imagenes/img_4.png");
        if (iconoURL != null) {
            ImageIcon icono = new ImageIcon(iconoURL);
            frame.setIconImage(icono.getImage());
        } else {
            System.out.println("⚠ ERROR: No se encontró la imagen icono_medicina.png");
        }

        frame.setContentPane(fondoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(600,650);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

/*************************************************************************************************************************************/
    // ** Clase interna para dibujar el fondo con imagen y degradado **
    class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/img_3.png");
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
