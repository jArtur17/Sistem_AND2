package Socket;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private JTextField textField1;
    private JButton enviarButton;
    private JTextArea textArea1;
    private JPanel panel;

    public static void main(String[] args) {

        JFrame frame = new JFrame("GUIAdivina");
        Servidor servidor = new Servidor();
        frame.setContentPane(servidor.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 1000);
        frame.setVisible(true);

        JTextArea textArea = servidor.textArea1 = new JTextArea();
        JPanel panel = servidor.panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton enviarButton = servidor.enviarButton = new JButton("Iniciar Servidor");

        textArea.setEditable(false);
        panel.add(scrollPane);

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (ServerSocket serverSocket = new ServerSocket(12345)) {
                    textArea.append("Servidor iniciado. Esperando conexión...\n");

                    Socket clientSocket = serverSocket.accept();
                    textArea.append("Cliente conectado.\n");

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String receivedMessage, sendMessage;

                    do {
                        receivedMessage = in.readLine();

                        if (receivedMessage == null || receivedMessage.equalsIgnoreCase("salir")) {
                            textArea.append("Cliente ha abandonado el chat.\n");
                            break;
                        }

                        textArea.append("Cliente dice: " + receivedMessage + "\n");

                        sendMessage = JOptionPane.showInputDialog("Escribe tu mensaje");

                        if (sendMessage == null || sendMessage.equalsIgnoreCase("salir")) {
                            out.println("salir");
                            break;
                        }
                        out.println(sendMessage);
                        out.flush();
                        textArea.append("Servidor dice: "+ sendMessage +"\n");

                    } while (true);

                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException t) {
                    textArea.append("Error en el servidor: " + t.getMessage() + "\n");
                }
            }
        });

        panel.add(enviarButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
