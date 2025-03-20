package Proyecto;

import Proyecto.Cliente.ClienteGUI;
import Proyecto.Producto.ProductoGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JButton clienteButton;
    private JButton productosButton;
    private JButton salirButton;
    private JPanel main;
    private JFrame frame;
    private JFrame parentFrame;


    public  Menu(JFrame frame) {
        this.frame = frame;

        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteGUI clienteGUI = new ClienteGUI(frame);
                clienteGUI.runCliente();
            }
        });
        productosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoGUI productoGUI = new ProductoGUI(frame);
                productoGUI.runProducto();
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
                if (frame != null) {
                    JOptionPane.showMessageDialog(null, "GoodBye");
                    frame.dispose();
                }
            }
        });
    }
    public static void main(String[] args) {

            JFrame frame = new JFrame("Data Base Game");
            Menu menu = new Menu(frame); // Se pasa el frame al constructor de Menu
            frame.setContentPane(menu.main);
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(600,600);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        }
    }

