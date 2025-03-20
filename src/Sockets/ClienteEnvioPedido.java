package Sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteEnvioPedido {
    private static final String SERVIDOR = "localhost";
    private static final int PUERTO = 12345;

    public static void enviarPedido(String pedido) {
        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {
            salida.println(pedido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
