package Sockets;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientePantallaPedido extends JFrame {
    private DefaultListModel<String> modeloLista;
    private JList<String> listaPedidos;

    public ClientePantallaPedido() {
        setTitle("Confirmación de Pedidos - Droguería");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modeloLista = new DefaultListModel<>();
        listaPedidos = new JList<>(modeloLista);
        add(new JScrollPane(listaPedidos));

        new Thread(this::conectarServidor).start();
    }

    private void conectarServidor() {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String pedido;
            while ((pedido = entrada.readLine()) != null) {
                modeloLista.addElement(pedido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientePantallaPedidos().setVisible(true));
    }
}
