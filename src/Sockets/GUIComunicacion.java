package Sockets;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Clase que representa la interfaz gráfica para la comunicación del chat.
 *
 * @author Nicolle
 */
public class GUIComunicacion {
    private JTextField textField1;
    private JButton enviarMensajeButton;
    private JTextArea textArea1;
    private JPanel main;
    private JButton salirButton;
    private PrintWriter out;
    private BufferedReader in;
    private JFrame frame;

    /**
     * Constructor que inicializa la interfaz y establece la conexión con el servidor.
     */
    public GUIComunicacion() {
        conectarServidor();
        aplicarEstilos();

        // Permitir enviar mensaje con Enter en el JTextField
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        enviarMensajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarConexion();
                JOptionPane.showMessageDialog(null, "Saliendo del chat...");
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(salirButton);
                if (topFrame != null) {
                    topFrame.dispose();
                }
            }
        });
    }

    /**
     * Aplica estilos visuales a la interfaz.
     */
    public void aplicarEstilos() {
        main.setBackground(Color.DARK_GRAY);
        enviarMensajeButton.setBackground(new Color(0, 51, 102));
        salirButton.setBackground(new Color(0, 51, 102));
        enviarMensajeButton.setForeground(Color.WHITE);
        salirButton.setForeground(Color.WHITE);
    }

    /**
     * Conecta el cliente al servidor solicitando la dirección IP.
     */
    public void conectarServidor() {
        try {
            String serverAddress = JOptionPane.showInputDialog("Ingrese la IP del servidor (localhost si es local)");
            if (serverAddress == null || serverAddress.isEmpty()) serverAddress = "localhost";

            Socket socket = new Socket(serverAddress, 123);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    String receivedMessage;
                    while ((receivedMessage = in.readLine()) != null) {
                        if (receivedMessage.contains("El servidor se ha desconectado")) {
                            System.exit(0);
                        }
                        String finalReceivedMessage = receivedMessage;
                        SwingUtilities.invokeLater(() -> textArea1.append(finalReceivedMessage + "\n"));
                    }
                } catch (IOException e) {
                    if (!e.getMessage().equals("Socket closed")) {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error en el cliente: " + e.getMessage()));
                    }
                }
            }).start();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Envía un mensaje al servidor y lo muestra en la interfaz.
     */
    public void enviarMensaje() {
        String sendMessage = textField1.getText();
        if (!sendMessage.isEmpty() && out != null) {
            out.println("Cliente: " + sendMessage);
            textArea1.append("Yo: " + sendMessage + "\n");
            textField1.setText("");
        }

        if (sendMessage.equalsIgnoreCase("salir")) {
            out.println("cliente ha salido del chat");
            System.exit(0);
        }
    }

    /**
     * Cierra la conexión con el servidor.
     */
    public void cerrarConexion() {
        try {
            if (out != null) {
                out.println("salir");
                out.close();
            }
            if (in != null) in.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error cerrando conexión: " + e.getMessage());
        }
    }

    /**
     * Inicializa y muestra la ventana del cliente de chat.
     */
    public void runcliente() {
        frame = new JFrame("Chat Cliente");
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
     * Clase interna que representa un panel con imagen de fondo.
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
         *
         * @param g Objeto Graphics utilizado para dibujar la imagen.
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
