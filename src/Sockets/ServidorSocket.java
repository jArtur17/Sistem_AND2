package Sockets;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServidorSocket {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clientes = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Servidor de Pedidos iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClienteHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClienteHandler implements Runnable {
        private Socket socket;
        private PrintWriter salida;

        public ClienteHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                salida = new PrintWriter(socket.getOutputStream(), true);

                synchronized (clientes) {
                    clientes.add(salida);
                }

                String pedido;
                while ((pedido = entrada.readLine()) != null) {
                    System.out.println("Pedido recibido: " + pedido);
                    enviarPedidoATodos(pedido);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                synchronized (clientes) {
                    clientes.remove(salida);
                }
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }

    private static void enviarPedidoATodos(String pedido) {
        synchronized (clientes) {
            for (PrintWriter cliente : clientes) {
                cliente.println(pedido);
            }
        }
    }
}
