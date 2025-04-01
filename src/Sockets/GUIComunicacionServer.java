package Sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

/**
 * Clase que representa la interfaz gráfica del servidor de chat.
 * La interfaz fue diseñada por Nicolle.
 *
 * @author Nicolle
 */
public class GUIComunicacionServer {
    private JTextField textField1;
    private JTextArea textArea1;
    private JButton enviarMensajeAlClienteButton;
    private JPanel main;
    private JButton salirButton;
    private PrintWriter out;
    private BufferedReader in;
    private Socket clienteSocket;
    private JFrame frame;
    private JFrame parentFrame;

    /**
     * Constructor que inicializa la interfaz gráfica del servidor y establece la conexión con el cliente.
     */
    public GUIComunicacionServer() {
        aplicarEstilos();

        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        enviarMensajeAlClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        new Thread(this::servidor).start(); // Inicia el servidor en un hilo separado

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarConexion();
                JOptionPane.showMessageDialog(null, "El servidor ha cerrado la conexión.");
                if (parentFrame != null) {
                    parentFrame.setVisible(true);
                }
                frame.dispose();
            }
        });
    }

    /**
     * Aplica los estilos de la interfaz gráfica.
     */
    public void aplicarEstilos() {
        main.setBackground(Color.DARK_GRAY);
        enviarMensajeAlClienteButton.setBackground(new Color(0, 51, 102));
        salirButton.setBackground(new Color(0, 51, 102));
        enviarMensajeAlClienteButton.setForeground(Color.WHITE);
        salirButton.setForeground(Color.WHITE);
    }

    /**
     * Método que inicia el servidor y espera la conexión del cliente.
     */
    public void servidor() {
        try (ServerSocket serverSocket = new ServerSocket(123)) {
            clienteSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            out = new PrintWriter(clienteSocket.getOutputStream(), true);

            String receivedMessage;
            while ((receivedMessage = in.readLine()) != null) {
                if (receivedMessage.contains("ha salido del chat")) {
                    System.exit(0);
                }
                String finalReceivedMessage = receivedMessage;
                SwingUtilities.invokeLater(() -> textArea1.append(finalReceivedMessage + "\n"));
            }
            clienteSocket.close();
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error en el servidor: " + e.getMessage()));
        }
    }

    /**
     * Envía un mensaje al cliente.
     */
    public void enviarMensaje() {
        String sendMessage = textField1.getText();
        if (!sendMessage.isEmpty() && out != null) {
            out.println("Servidor: " + sendMessage);
            textArea1.append("Yo: " + sendMessage + "\n");
            textField1.setText("");
        }
    }

    /**
     * Cierra la conexión con el cliente.
     */
    public void cerrarConexion() {
        try {
            if (out != null) {
                out.println("Servidor ha salido del chat");
                out.close();
            }
            if (in != null) in.close();
            if (clienteSocket != null) clienteSocket.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error cerrando conexión: " + e.getMessage());
        }
    }

    /**
     * Método que inicia la ventana del servidor.
     */
    public void runservidor() {
        frame = new JFrame("Servidor Chat");
        frame.setContentPane(this.main);
        frame.pack();
        FondoPanel fondoPanel = new FondoPanel();
        main.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(main, BorderLayout.CENTER);

        URL iconoURL = getClass().getClassLoader().getResource("imagenes/img_12.png");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }

        frame.setContentPane(fondoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Clase interna para manejar el fondo de la interfaz gráfica.
     */
    class FondoPanel extends JPanel {
        private Image imagenFondo;

        /**
         * Constructor que carga la imagen de fondo.
         */
        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/img_13.png");
            if (imagenURL != null) {
                this.imagenFondo = new ImageIcon(imagenURL).getImage();
            }
        }

        /**
         * Dibuja la imagen de fondo en el panel.
         * @param g Objeto Graphics utilizado para dibujar.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
