import Caja.CajaGUI;
import Detalle_Financiero.Detalle_FinancieroGUI;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JPanel main;
    private JButton movimientoFinancieroButton;
    private JButton cajaButton;

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


