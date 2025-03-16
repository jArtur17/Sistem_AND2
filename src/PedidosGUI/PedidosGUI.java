package PedidosGUI;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;

public class PedidosGUI {


    private JPanel Panel_medicamentos;
    private JTextField textField1;
    private JScrollPane ScrolPanel_medicamentos;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTable table1;
    private JPanel Panelprincipal;
    private JTextField textField5;



    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        PedidosGUI pedidosGUI = new PedidosGUI();
        frame.setContentPane(pedidosGUI.Panelprincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setResizable(false);

        // Ajustar el tamaño preferido del JScrollPane
        pedidosGUI.ScrolPanel_medicamentos.setPreferredSize(new Dimension(300, 150));

        frame.setVisible(true);
    }


}
