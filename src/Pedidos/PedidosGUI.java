package Pedidos;

import conexion.ConexionFarmacia;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PedidosGUI {
    private JTextField textField4;
    private JComboBox comboBox1;
    private JSpinner spinner1;
    private JComboBox comboBox2;
    private JPanel PanelPrincipal;
    private JTable tablaProductos;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField2;
    private JTextField textField9;
    private JScrollPane Scrol_productos;
    private JButton agregarProductoButton;
    private JButton cancelarButton;
    private JButton generarVentaButton;
    private JComboBox comboBoxClientes;
    private JTextField textField10;
    private JPanel Panel_datos;
    private JTable tablaCarrito;
    private JTextArea textArea1;
    private JTextField textField11;
    double total = 0;
    int item = 0;

    ConexionFarmacia cf = new ConexionFarmacia();

    public PedidosGUI() {
        //JFrame frame = new JFrame("Reloj en tiempo real");
        //JLabel labelHora = new JLabel();
        //frame.add(labelHora);
        //frame.setSize(200, 100);
        //frame.setVisible(true);

        Timer timer = new Timer(1000, new ActionListener() { // Actualizar cada segundo
            @Override
            public void actionPerformed(ActionEvent e) {
                textField4.setEditable(false);
                LocalDateTime ahora = LocalDateTime.now();
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Incluye la fecha
                String fechaHoraFormateada = ahora.format(formateador);
                textField4.setText(fechaHoraFormateada);

            }
        });
        timer.start();

        //textField11.setText(fechaHoraFormateada);

        cargarClientes();
        Productos();
        Carrito();
        //agregarListenerTabla();
        Panel_datos.setVisible(false);
        Scrol_productos.setPreferredSize(new Dimension(200, 200));
        Panel_datos.setBackground(new Color(200, 200, 200));
        textField1.setBackground(new Color(220, 230, 240));
        textField6.setBackground(new Color(220, 220, 220));
        textField7.setBackground(new Color(220, 220, 220));
        textField8.setBackground(new Color(220, 220, 220));
        textField5.setBackground(new Color(220, 220, 220));
        textField2.setBackground(new Color(220, 220, 220));
        textField9.setBackground(new Color(220, 220, 220));
        textField3.setBackground(new Color(220, 220, 220));

        textArea1.setLineWrap(true);


        comboBoxClientes.addActionListener(e -> {
            if (comboBoxClientes.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione un cliente");
            }

            ClientesItem clientesItem = (ClientesItem) comboBoxClientes.getSelectedItem();

            if (clientesItem != null) {

                //precioprodorden.setText(String.valueOf(productoSeleccionado.getPrecio())); //
                //disponibletxt.setText(productoSeleccionado.Disponible());
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem().toString().equals("unidad")) {
                    spinner1.setValue(0);
                } else if (comboBox1.getSelectedItem().toString().equals("blister")) {
                    spinner1.setValue(10);
                } else if (comboBox1.getSelectedItem().toString().equals("caja")) {
                    spinner1.setValue(100);

                }
            }
        });

        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });
    }
/*
    private void agregarListenerTabla() {
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaProductos.getSelectedRow() != -1) {
                int filaSeleccionada = tablaProductos.getSelectedRow();
                mostrarDetallesProducto(filaSeleccionada);
            }
        });
    }

 */



    private void mostrarDetallesProducto(int filaSeleccionada) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaProductos.getModel();
        String idProducto = (String) modeloTabla.getValueAt(filaSeleccionada, 0);  // Asumiendo que el ID está en la primera columna
        Connection conexion = cf.getConnection();
        try {
            PreparedStatement consulta = conexion.prepareStatement("SELECT categoria, stock, stock_minimo, precio_unitario, fecha_vencimiento, indicaciones, almacen, lote FROM producto WHERE id_producto = ?");
            consulta.setInt(1, Integer.parseInt(idProducto));
            ResultSet resultados = consulta.executeQuery();

            if (resultados.next()) {
                textField5.setText(resultados.getString("categoria"));
                textField6.setText(String.valueOf(resultados.getInt("stock")));
                textField8.setText(String.valueOf(resultados.getInt("stock_minimo")));
                textField7.setText(String.valueOf(resultados.getInt("precio_unitario")));
                textField3.setText(String.valueOf(resultados.getString("fecha_vencimiento")));
                textArea1.setText(String.valueOf(resultados.getString("indicaciones")));
                textField2.setText(String.valueOf(resultados.getString("lote")));
                textField9.setText(String.valueOf(resultados.getString("almacen")));
                Panel_datos.setVisible(true); // Activa el panel
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void agregarProducto(){

        //comienza la orden
        textField1.setText("En preparacion...");
        ///////////////////////////////////////

        //obtener el id del producto
        int fila = tablaCarrito.getSelectedRow();
        if (fila != -1) {
            DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
            Object valor = modelo.getValueAt(fila, 0);

            if(valor != null){
                try{
                    int idprod;
                    if(valor instanceof Integer){
                        idprod = (Integer) valor;
                        System.out.println(idprod);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        }



        //variables de condición(obtenemos el stock normal y min)
        int stockmin = Integer.parseInt(textField8.getText());
        int stock = Integer.parseInt(textField6.getText());
        ////////////////////////////////////////////////////////


        DefaultTableModel model = new DefaultTableModel();

        int cantidad = (int) spinner1.getValue();
        String t_cantidad = comboBox1.getSelectedItem().toString();
        int precio_u = Integer.parseInt(textField7.getText());



        int sub_total = precio_u * cantidad;
        total += sub_total;
        textField11.setText(String.valueOf(total));



        //ArrayList lista = new ArrayList();
        if(stock == stockmin ){
            JOptionPane.showMessageDialog(null, "Este producto ha llegado al stock mínimo");
        } else if (tablaCarrito.getModel().getRowCount() > 0) {
            JOptionPane.showMessageDialog(null, "No se hay productos en el carrito");
        } else if (spinner1.getValue().equals(0)) {
            JOptionPane.showMessageDialog(null, "No se ha escogido una cantidad");
        }
        else if(stock > 0 && stock > stockmin){
            /*
            lista.add(item+1);
            lista.add(idprod);
            lista.add(cantidad);
            lista.add(precio_u);
            lista.add(cantp);
            lista.add(total);
            lista.add(dispo);
            Object[] ob = new Object[6];
            ob[0] = lista.get(0);
            ob[1] = lista.get(1);
            ob[2] = lista.get(2);
            ob[3] = lista.get(3);
            ob[4] = lista.get(4);
            ob[5] = lista.get(5);
            model.addRow(ob);
            tablacarrito.setModel(model);
            calcularTotal();

             */

        }else{
            JOptionPane.showMessageDialog(null, "Este producto no está disponible");
        }

    }









    public static void main(String[] args) {

        JFrame frame = new JFrame("Main");
        frame.setContentPane(new PedidosGUI().PanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,700);

        frame.setResizable(false);
        frame.setVisible(true);



    }





















    public void Productos() {

        DefaultTableModel pedidos = new DefaultTableModel();
        pedidos.addColumn("No.");
        pedidos.addColumn("Nombre");
        tablaProductos.setModel(pedidos);

        Connection con = cf.getConnection();

        String[] Arreglo = new String[2];

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_producto, nombre FROM producto");

            while (rs.next()) {
                Arreglo[0] = rs.getString(1);
                Arreglo[1] = rs.getString(2);

                pedidos.addRow(Arreglo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void Carrito() {
        DefaultTableModel pedidos = new DefaultTableModel();
        pedidos.addColumn("Pedido.");
        pedidos.addColumn("Producto");
        pedidos.addColumn("Producto");
        pedidos.addColumn("Tipo");
        pedidos.addColumn("Cantidad");
        pedidos.addColumn("Producto");
        pedidos.addColumn("Precio U");
        pedidos.addColumn("SubTotal");
        tablaCarrito.setModel(pedidos);
    }

    private void cargarClientes() {
        try {

            Connection con = cf.getConnection();
            Statement statement = con.createStatement();


            String query = "SELECT id_cliente, nombre FROM cliente";
            ResultSet rs = statement.executeQuery(query);

            // Agrega cada nombre al JComboBox
            while (rs.next()) {
                int idcliente = rs.getInt("id_cliente");
                String cliente = rs.getString("nombre");
                comboBoxClientes.addItem(new ClientesItem(idcliente, cliente));
            }


            rs.close();
            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar clientes.");
        }
    }

    class ClientesItem {
        private int id_cliente;
        private String nombre;

        public ClientesItem(int id_cliente, String nombre) {
            this.id_cliente= id_cliente;
            this.nombre = nombre;
        }

        public int getId() {
            return id_cliente;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }




}


