import Caja.CajaGUI;
import Cliente.ClienteGUI;
import Detalle_Financiero.Detalle_FinancieroGUI;
import Historial.HistorialPedidosGUI;
import Pedidos.PedidosGUI;
import Producto.ProductoGUI;
import Reportes.ReportesGUI;
import Sockets.GUIComunicacion;
import Sockets.GUIComunicacionServer;
//import Sockets.GUIComunicacion;
//import Sockets.GUIComunicacionServer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;



public class Menu {
    private JPanel main;
    private JButton movimientoButton;
    private JButton cajaButton;
    private JButton reportesButton;
    private JButton pedidosButton;
    private JButton clientesButton;
    private JButton productosButton;
    private JButton hisotrialButton;
    private JButton chatButton;

    private JFrame frame;


    public Menu(JFrame frame) {
        this.frame = frame;

        // Aplicar fondo degradado
        main = new FondoPanel();
        main.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Espaciado horizontal: 20px, vertical: 10px

        buttonPanel.setOpaque(false);

        // Crear botones estilizados
        pedidosButton = createStyledButton("Pedidos");
        hisotrialButton = createStyledButton("Historial");
        chatButton = createStyledButton("Chat");
        clientesButton = createStyledButton("Clientes");
        productosButton = createStyledButton("Productos");
        movimientoButton = createStyledButton("Movimiento");
        cajaButton = createStyledButton("Caja");
        reportesButton = createStyledButton("Reportes");

        // Agregar botones al panel
        buttonPanel.add(pedidosButton);
        buttonPanel.add(hisotrialButton);
        buttonPanel.add(chatButton);
        buttonPanel.add(clientesButton);
        buttonPanel.add(productosButton);
        buttonPanel.add(movimientoButton);
        buttonPanel.add(cajaButton);
        buttonPanel.add(reportesButton);

        // Imagen en la parte inferior
        JLabel imageLabel = new JLabel();
        URL imageUrl = getClass().getClassLoader().getResource("imagenes/img_2.png");
        if (imageUrl != null) {
            imageLabel.setIcon(new ImageIcon(imageUrl));
        }
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        main.add(buttonPanel, BorderLayout.NORTH);
        main.add(imageLabel, BorderLayout.SOUTH);

        // Configuración de eventos
        movimientoButton.addActionListener(e -> {
            Detalle_FinancieroGUI detalleFinancieroGUI = new Detalle_FinancieroGUI(frame);
            detalleFinancieroGUI.runFinanciero();
            frame.setVisible(false);
        });

        cajaButton.addActionListener(e -> {
            CajaGUI cajaGUI = new CajaGUI(frame);
            cajaGUI.runCaja();
            frame.setVisible(false);
        });

        pedidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PedidosGUI pGUI = new PedidosGUI();
                pGUI.RunPedidos();
                frame.setVisible(false);
            }
        });

        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteGUI clientes = new ClienteGUI(frame);
                clientes.runCliente();
                frame.setVisible(false);
            }
        });

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIComunicacionServer guiComunicacionServer = new GUIComunicacionServer();
                guiComunicacionServer.runservidor();
                GUIComunicacion guiComunicacion = new GUIComunicacion();
                guiComunicacion.runcliente();

            }
        });

        productosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoGUI producto = new ProductoGUI(frame);
                producto.runProducto();
                frame.setVisible(false);
            }
        });

        hisotrialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorialPedidosGUI historialPedidosGUI  = new HistorialPedidosGUI(frame);
                historialPedidosGUI.runHistorial();
                frame.setVisible(false);
            }
        });
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportesGUI reportesGUI  = new ReportesGUI(frame);
                reportesGUI.runReport();
                frame.setVisible(false);
            }
        });


        FondoPanel fondoPanel = new FondoPanel();
        main.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(main, BorderLayout.CENTER);

        URL iconoURL = getClass().getClassLoader().getResource("imagenes/img_1.png");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }

        frame.setContentPane(main);
        frame.setSize(995, 620);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 51, 102)); // Azul oscuro
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> button.setBackground(new Color(51, 153, 255))); // Azul claro al hacer clic
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    class FondoPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(51, 153, 255), getWidth(), getHeight(), new Color(0, 51, 102));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        new Menu(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}


