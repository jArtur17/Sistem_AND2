package Pedidos;

import javax.swing.*;
import java.awt.*;

public class PedidosCode extends JFrame {

    public PedidosCode() {
        // 1) Creación del frame
        setTitle("Pedidos Farmacia");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);

        // 2) Creación del panel de productos
        JPanel Panelproductos = new JPanel();
        Panelproductos.setBounds(10, 10, 200, 500); // Tamaño y posición
        Panelproductos.setBorder(BorderFactory.createTitledBorder("  Productos"));

        // 3) Agregar el scroll panel donde vamos a mostrar todos los productos
        JScrollPane scroll_productos = new JScrollPane(Panelproductos);
        scroll_productos.setBounds(10, 10, 200, 200); // Ajusta el ancho para que quepa el panel
        add(scroll_productos);

        // 4) Creo el panel de datos del pedido (tipo cant, cant, fecha y hora, m_pago)
        JPanel Panelderecho = new JPanel();
        Panelderecho.setBounds(220, 10, 560, 200); // Tamaño y posición
        Panelderecho.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));
        add(Panelderecho);

        // 5) Detalles del Panelderecho
        Panelderecho.setLayout(new BoxLayout(Panelderecho, BoxLayout.Y_AXIS)); // Usa BoxLayout vertical

        // Panel para Tipo de Cantidad
        JPanel panelTipoCantidad = new JPanel();
        panelTipoCantidad.setLayout(new BoxLayout(panelTipoCantidad, BoxLayout.X_AXIS)); // Usa BoxLayout horizontal
        JLabel Tipocantidad = new JLabel("Tipo de cantidad:");
        panelTipoCantidad.add(Tipocantidad);
        JComboBox<String> comboBoxTipoCantidad = new JComboBox<>(new String[]{"Unidad", "Blister", "Caja"});
        comboBoxTipoCantidad.setPreferredSize(new Dimension(100, 25)); // Ajusta el tamaño
        panelTipoCantidad.add(comboBoxTipoCantidad);
        Panelderecho.add(panelTipoCantidad);

        // Panel para Cantidad
        JPanel panelCantidad = new JPanel();
        panelCantidad.setLayout(new BoxLayout(panelCantidad, BoxLayout.X_AXIS)); // Usa BoxLayout horizontal
        JLabel cantidad = new JLabel("Cantidad:");
        panelCantidad.add(cantidad);
        JTextField cantidadtxt = new JTextField();
        cantidadtxt.setPreferredSize(new Dimension(80, 25)); // Ajusta el tamaño
        panelCantidad.add(cantidadtxt);
        Panelderecho.add(panelCantidad);



    }

    public static void main(String[] args) {
        new PedidosCode();
    }

}
