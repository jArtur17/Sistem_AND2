package Sockets;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Clase que representa un cliente de chat basado en sockets.
 *
 * @author Dannier
 */
public class ChatClient {

    /**
     * Método principal que ejecuta el cliente de chat.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Solicita la dirección IP del servidor
        String serverAdress = JOptionPane.showInputDialog("Ingrese la IP del servidor (localhost si es local)");
        if (serverAdress == null || serverAdress.isEmpty()) serverAdress = "localhost";

        try (Socket socket = new Socket(serverAdress, 12345)) {
            // Configura los flujos de entrada y salida para la comunicación con el servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String sendMessage, receivedMessage;

            do {
                // Solicita un mensaje al usuario
                sendMessage = JOptionPane.showInputDialog("Escribe tu mensaje");
                if (sendMessage == null || sendMessage.equalsIgnoreCase("salir")) {
                    out.println("salir");
                    break;
                }

                // Envía el mensaje al servidor
                out.println(sendMessage);

                // Recibe la respuesta del servidor
                receivedMessage = in.readLine();

                if (receivedMessage == null || receivedMessage.equalsIgnoreCase("salir")) {
                    JOptionPane.showMessageDialog(null, "El servidor ha cerrado la conexión");
                    break;
                }

                // Muestra el mensaje recibido del servidor
                JOptionPane.showMessageDialog(null, "Servidor dice: " + receivedMessage);

            } while (true);
        } catch (IOException e) {
            // Muestra un mensaje de error en caso de fallo en la conexión
            JOptionPane.showMessageDialog(null, "Error en el cliente: " + e.getMessage());
        }
    }
}
