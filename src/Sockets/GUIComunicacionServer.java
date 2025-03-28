package Sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

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

    public GUIComunicacionServer() {

        // Cambiar color y fuente de los labels
        for (Component c : main.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        aplicarEstilos();


        // Permitir enviar mensaje con Enter en el JTextField
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



        new Thread(this::servidor).start(); // para que se conecten mutuamente

        enviarMensajeAlClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarConexion(); // Cierra la conexión correctamente
                JOptionPane.showMessageDialog(null, "El servidor ha cerrado la conexión.");

                if (parentFrame != null){
                    parentFrame.setVisible(true);
                }
                frame.dispose();
            }
        });
    }


      public void aplicarEstilos() {
        main.setBackground(Color.DARK_GRAY);
        enviarMensajeAlClienteButton.setBackground(new Color(0, 51, 102));
        salirButton.setBackground(new Color(0, 51, 102));


        enviarMensajeAlClienteButton.setForeground(Color.WHITE);
        salirButton.setForeground(Color.WHITE);

      }

        public void servidor() {
        try (ServerSocket serverSocket = new ServerSocket(123)) {

            clienteSocket = serverSocket.accept();

            in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream())); // el in
            // es lo que voy a recibir de la parte del cliente por eso se pone in.etc

            out = new PrintWriter(clienteSocket.getOutputStream(), true);
            // el out es lo que yo mando en este caso la salida, o sea la respuesta a enviar al cliente

            String receivedMessage;
            while ((receivedMessage = in.readLine()) != null) { // recibi el mensaje del cliente con el in
                if (receivedMessage.contains("ha salido del chat")) {
                    System.exit(0);


                }

                String finalReceivedMessage = receivedMessage;
                SwingUtilities.invokeLater(() -> textArea1.append( finalReceivedMessage + "\n" ));
            }

            clienteSocket.close();
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error en el servidor: " + e.getMessage()));
        }
    }

    public void enviarMensaje() {
        String sendMessage = textField1.getText();
        if (!sendMessage.isEmpty() && out != null) {
            out.println("Servidor: " + sendMessage); // Agregar prefijo "Servidor"
            textArea1.append("Yo: " + sendMessage + "\n"); // Mostrarlo como "Yo" en la interfaz
            textField1.setText(""); // Limpiar el campo de texto después de enviar
        }
    }
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


    class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/img_13.png");
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
