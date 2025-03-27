package Sockets;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class ChatServer {

    public static void main(String[] args) {


        try(ServerSocket serverSocket = new ServerSocket(12345)) {
            JOptionPane.showMessageDialog(null, "servidor iniciado  conexion..");
            Socket clientesocket = serverSocket.accept();
            JOptionPane.showMessageDialog(null, "Cliente conectado");

            BufferedReader in  = new BufferedReader(new InputStreamReader(clientesocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientesocket.getOutputStream(), true);

            String receivedMessage, sendMessage;

            do{

                receivedMessage = in.readLine();
                if(receivedMessage == null || receivedMessage.equalsIgnoreCase("salir")){
                    JOptionPane.showMessageDialog(null, "cliente ha salido del chat");
                    break;

                }
                JOptionPane.showMessageDialog(null, " cliente dice " + receivedMessage);

                sendMessage = JOptionPane.showInputDialog("Escribe tu mensaje ");
                if(sendMessage == null || sendMessage.equalsIgnoreCase("salir")){
                    out.println("salir");
                    break;

                }

                out.println(sendMessage);
            }while (true);

            clientesocket.close();
            serverSocket.close();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "error al servidor " + e.getMessage());

        }
    }
}
