package Pedidos;

import conexion.ConexionFarmacia;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton cancelarPedidoButton;
    private JButton generarVentaButton;
    private JComboBox comboBoxClientes;
    private JTextField textField10;
    private JPanel Panel_datos;
    private JTable tablaCarrito;
    private JTextArea textArea1;
    private JTextField textField11;
    double total = 0;
    int item = 0;
    String product="";
    DefaultTableModel model = new DefaultTableModel();

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
        agregarListenerTabla();
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
            if (comboBoxClientes.getSelectedItem().toString().equals("CLIENTES")) {
                JOptionPane.showMessageDialog(null, "Seleccione un cliente");
            } else {
                ClientesItem clientesItem = (ClientesItem) comboBoxClientes.getSelectedItem();
            }


            /*
            if (clientesItem != null) {

                //precioprodorden.setText(String.valueOf(productoSeleccionado.getPrecio())); //
                //disponibletxt.setText(productoSeleccionado.Disponible());

            } */
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
                Panel_datos.setVisible(true);
                agregarProducto();
            }
        });
        cancelarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro? Se borran los pedidos",
                        "Confirmar acción",
                        JOptionPane.YES_NO_OPTION
                );

                // El usuario acepta cancelar el pedido
                if (respuesta == JOptionPane.YES_OPTION) {
                    model = (DefaultTableModel) tablaCarrito.getModel();
                    model.setRowCount(0);

                    Panel_datos.setVisible(false);
                    spinner1.setValue(0);
                    comboBox1.setSelectedIndex(0);
                    comboBox2.setSelectedIndex(0);
                    comboBoxClientes.setSelectedIndex(0);
                    textField11.setText("");

                }
            }
        });
        generarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //EmpleadosItem empSelec = (EmpleadosItem) comboBoxEmpleado.getSelectedItem();
                //int idemp = empSelec.getId();

                int can = (int) spinner1.getValue();
                String t_can = comboBox1.getSelectedItem().toString();
                int pre_u = Integer.parseInt(textField7.getText());
                int sub = pre_u * can;


                ClientesItem clientesItem = (ClientesItem) comboBoxClientes.getSelectedItem();
                int idcliente = clientesItem.getId();

            }

        });
    }


        private void agregarListenerTabla () {
            tablaProductos.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && tablaProductos.getSelectedRow() != -1) {
                    int filaSeleccionada = tablaProductos.getSelectedRow();
                    mostrarDetallesProducto(filaSeleccionada);
                }
            });

            tablaProductos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent event) {
                    if (!event.getValueIsAdjusting()) {
                        int fila = tablaProductos.getSelectedRow();
                        if (fila >= 0 && fila < tablaProductos.getRowCount()) {
                            product = tablaProductos.getValueAt(fila, 1).toString();

                        }
                    }
                }
            });
        }






    private void mostrarDetallesProducto(int filaSeleccionada) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaProductos.getModel();
        String idProducto = (String) modeloTabla.getValueAt(filaSeleccionada, 0);//revisar esta partte de codigo
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

        //variables de condición(obtenemos el stock normal y min)
        int stockmin = Integer.parseInt(textField8.getText());
        int stock = Integer.parseInt(textField6.getText());
        ////////////////////////////////////////////////////////

        model=(DefaultTableModel)tablaCarrito.getModel();

        int cantidad = (int) spinner1.getValue();
        String t_cantidad = comboBox1.getSelectedItem().toString();
        int precio_u = Integer.parseInt(textField7.getText());



        /*
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0 && fila < tablaProductos.getRowCount()) {
            // La columna dos tiene índice 1 (las columnas se indexan desde 0)
             product = tablaProductos.getValueAt(fila, 1).toString();
            System.out.println(product);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
        }

         */

        ArrayList lista = new ArrayList();
        if(stock == stockmin ){
            JOptionPane.showMessageDialog(null, "Este producto ha llegado al stock mínimo");
        }else if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "La cantidad ingresada es incorrecta");
        }else if(comboBoxClientes.getSelectedItem().equals("CLIENTES")){
            JOptionPane.showMessageDialog(null, "No se ha seleccionado el cliente");
        }
        else if(stock > 0 && stock > stockmin && cantidad > 0){
            int sub_total = precio_u * cantidad;
            total += sub_total;
            textField11.setText(String.valueOf(total));
            lista.add(item+=1);
            lista.add(product);
            lista.add(t_cantidad);
            lista.add(cantidad);
            lista.add(precio_u);
            lista.add(sub_total);
            Object[] ob = new Object[6];
            ob[0] = lista.get(0);
            ob[1] = lista.get(1);
            ob[2] = lista.get(2);
            ob[3] = lista.get(3);
            ob[4] = lista.get(4);
            ob[5] = lista.get(5);
            model.addRow(ob);
            tablaCarrito.setModel(model);

        }else{
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error, intentelo de nuevo");
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
        pedidos.addColumn("Item");
        pedidos.addColumn("Producto");
        pedidos.addColumn("tipo de Cant");
        pedidos.addColumn("Cantidad");
        pedidos.addColumn("Precio unitario");
        pedidos.addColumn("Subtotal");
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


