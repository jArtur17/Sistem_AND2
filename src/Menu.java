import Caja.CajaGUI;
import Cliente.ClienteGUI;
import Detalle_Financiero.Detalle_FinancieroGUI;
import Reportes.ReportesGUI;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JPanel main;
    private JButton movimientoFinancieroButton;
    private JButton cajaButton;
    private JButton reportesButton;
    private JButton pedidosButton;
    private JButton clientesButton;
    private JButton productosButton;
    private JButton hisotrialPedidosButton;
    private JButton chatButton;

    private JFrame frame;


    public Menu(JFrame frame)
    {
        this.frame  = frame;

        movimientoFinancieroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Detalle_FinancieroGUI detalleFinancieroGUI = new Detalle_FinancieroGUI(frame);
                detalleFinancieroGUI.runFinanciero();
                frame.setVisible(false);
            }
        });
        cajaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CajaGUI cajaGUI = new CajaGUI(frame);
                cajaGUI.runCaja();
                frame.setVisible(false);


            }
        });

        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportesGUI reportesGUI = new ReportesGUI(frame);
                reportesGUI.runReport();
                frame.setVisible(false);


            }
        });


        pedidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteGUI clientes = new ClienteGUI(frame);
                clientes.runCliente();
                frame.setVisible(false);
            }
        });


        productosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Data Base Game");
        Menu menu = new Menu(frame); // Se pasa el frame al constructor de Menu
        frame.setContentPane(menu.main);
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(320,210);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}


