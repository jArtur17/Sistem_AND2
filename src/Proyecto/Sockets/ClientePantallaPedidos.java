package Sockets;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientePantallaPedidos extends JFrame {
    private DefaultListModel<String> modeloLista;
    private JList<String> listaPedidos;

    public ClientePantallaPedidos() {
        setTitle("Confirmación de Pedidos - Droguería");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modeloLista = new DefaultListModel<>();
        listaPedidos = new JList<>(modeloLista);
        add(new JScrollPane(listaPedidos));

        // Conectar al servidor en un hilo separado
        new Thread(this::conectarServidor).start();
    }

    private void conectarServidor() {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String pedido;
            while ((pedido = entrada.readLine()) != null) {
                modeloLista.addElement(pedido); // Agrega el pedido a la lista en la GUI
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientePantallaPedidos().setVisible(true));
    }
}
