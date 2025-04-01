package Sockets;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

/**
 * Clase que representa un servidor de chat basado en sockets.
 *
 * @author Dannier
 */
public class ChatServer {

    /**
     * Método principal que ejecuta el servidor de chat.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            JOptionPane.showMessageDialog(null, "Servidor iniciado, esperando conexión...");
            Socket clientesocket = serverSocket.accept();
            JOptionPane.showMessageDialog(null, "Cliente conectado");

            // Configura los flujos de entrada y salida para la comunicación con el cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(clientesocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientesocket.getOutputStream(), true);

            String receivedMessage, sendMessage;

            do {
                // Recibe el mensaje del cliente
                receivedMessage = in.readLine();
                if (receivedMessage == null || receivedMessage.equalsIgnoreCase("salir")) {
                    JOptionPane.showMessageDialog(null, "El cliente ha salido del chat");
                    break;
                }
                JOptionPane.showMessageDialog(null, "Cliente dice: " + receivedMessage);

                // Solicita un mensaje al servidor para enviar al cliente
                sendMessage = JOptionPane.showInputDialog("Escribe tu mensaje");
                if (sendMessage == null || sendMessage.equalsIgnoreCase("salir")) {
                    out.println("salir");
                    break;
                }

                // Envía el mensaje al cliente
                out.println(sendMessage);
            } while (true);

            // Cierra las conexiones
            clientesocket.close();
            serverSocket.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error en el servidor: " + e.getMessage());
        }
    }
}
