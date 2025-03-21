import Caja.CajaGUI;
import Cliente.ClienteGUI;
import Detalle_Financiero.Detalle_FinancieroGUI;
import Pedidos.PedidosGUI;
import Producto.ProductoGUI;
import Sockets.Servidor;
import Sockets.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;



public class Menu {
    private JPanel main;
    private JButton movimientoFinancieroButton;
    private JButton cajaButton;
    private JButton reportesButton;
    private JButton pedidosButton;
    private JButton clientesButton;
    private JButton productosButton;
    private JButton hisotrialPedidosButton;
    private JButton chatButton;

    private JFrame frame;


    public Menu(JFrame frame) {
        this.frame = frame;

        // Aplicar fondo degradado
        main = new FondoPanel();
        main.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 8, 10, 0)); // Botones en fila
        buttonPanel.setOpaque(false);

        // Crear botones estilizados
        pedidosButton = createStyledButton("Pedidos");
        hisotrialPedidosButton = createStyledButton("Historial Pedidos");
        chatButton = createStyledButton("Chat");
        clientesButton = createStyledButton("Clientes");
        productosButton = createStyledButton("Productos");
        movimientoFinancieroButton = createStyledButton("Movimiento Financiero");
        cajaButton = createStyledButton("Caja");
        reportesButton = createStyledButton("Reportes");

        // Agregar botones al panel
        buttonPanel.add(pedidosButton);
        buttonPanel.add(hisotrialPedidosButton);
        buttonPanel.add(chatButton);
        buttonPanel.add(clientesButton);
        buttonPanel.add(productosButton);
        buttonPanel.add(movimientoFinancieroButton);
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
        movimientoFinancieroButton.addActionListener(e -> {
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
                Servidor servidor = new Servidor();
                PedidosGUI pGUI = new PedidosGUI();
                //pGUI.setServidor(servidor);
                pGUI.PedidosMain();
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
                //Servidor socketS = new Servidor();
                //socketS.iniciar();
                //socketS.SocketServidor();

                //Cliente sock = new Cliente();
                //sock.SocketCliente();
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

        frame.setContentPane(main);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 51, 102)); // Azul oscuro
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> button.setBackground(new Color(51, 153, 255))); // Azul claro al hacer clic
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
        JFrame frame = new JFrame("Data Base Game");
        new Menu(frame);
    }
}


