package Proyecto.Pedidos;

import Conexion.Conexion;

import javax.swing.*;
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
    private JPanel Panel_datos;
    private JTable tablaCarrito;
    private JTextArea textArea1;
    private JTextField textField11;
    private JPanel PanelCarrito;
    private JFrame Jframe;

/************************************************************************************************************************/
    //variables propias globales

    //total del pedido
    double total = 0;

    //item del carrito (auto incremental)
    int item = 0;

    //nombre del producto agregado
    String product="";

    //id del producto agregado
    String idProducto="";

    //objeto defaultTableModel
    DefaultTableModel model = new DefaultTableModel();

/*************************************************************************************************************************/
    //conexion a la base de datos(cf:objeto de la conexion)
    Conexion cf = new Conexion();
/************************************************************************************************************************/

    public PedidosGUI(JFrame parentFrame) {

/************************************************************************************************************************/

        //Reloj del sistema, hora y fecha.
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField4.setEditable(false);
                LocalDateTime ahora = LocalDateTime.now();
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); // Incluye la fecha
                String fechaHoraFormateada = ahora.format(formateador);
                textField4.setText(fechaHoraFormateada);
            }
        });
        timer.start();
/************************************************************************************************************************/

        //cargar clientes en el comboBox clientes
        cargarClientes();

        //llamar los productos disponibles en la base de datos
        Productos();

        //configurar la tabla carrito con sus respectivas columnas
        Carrito();

        //accion para identificar si se selecciono un producto en la tabla
        agregarListenerTabla();

/************************************************************************************************************************/

        //Paneles ocultos, (carrito y datos del producto)
        Panel_datos.setVisible(false);
        PanelCarrito.setVisible(false);

        //textfield que no se pueden editar
        textField6.setEditable(false);
        textField8.setEditable(false);
        textField7.setEditable(false);
        textField3.setEditable(false);
        textField5.setEditable(false);
        textField2.setEditable(false);
        textField9.setEditable(false);
        textArea1.setEditable(false);
        textField11.setEditable(false);

        //definir el tamaño del scrol de prodcutos en la tabla productos
        Scrol_productos.setPreferredSize(new Dimension(200, 200));

        //color del panel de datos (gris claro)
        Panel_datos.setBackground(new Color(200, 200, 200));

        //color de los textfield en el panel de datos (azul claro)
        textField1.setBackground(new Color(220, 230, 240));
        textField6.setBackground(new Color(220, 220, 220));
        textField7.setBackground(new Color(220, 220, 220));
        textField8.setBackground(new Color(220, 220, 220));
        textField5.setBackground(new Color(220, 220, 220));
        textField2.setBackground(new Color(220, 220, 220));
        textField9.setBackground(new Color(220, 220, 220));
        textField3.setBackground(new Color(220, 220, 220));

        //darle formato al textArea para que muestre el texto en varios parrafos
        textArea1.setLineWrap(true);

        //color del textArea (azul claro)
        textArea1.setBackground(new Color(220, 230, 240));
/************************************************************************************************************************/
        //acciones

        /*----------------------------------------------------------------------------------------------------------------------*/
        //accion del combobox clientes
        comboBoxClientes.addActionListener(e -> {
            if (comboBoxClientes.getSelectedItem().toString().equals("CLIENTES")) {
                JOptionPane.showMessageDialog(null, "Seleccione un cliente");
            } else {
                ClientesItem clientesItem = (ClientesItem) comboBoxClientes.getSelectedItem();
            }
        });
        /*----------------------------------------------------------------------------------------------------------------------*/

        //accion del combobox tipo de cantidad
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem().toString().equals("unidad")) {
                    spinner1.setEnabled(true);
                    spinner1.setValue(0);
                } else if (comboBox1.getSelectedItem().toString().equals("blister")) {
                    spinner1.setValue(10);
                    spinner1.setEnabled(false);
                } else if (comboBox1.getSelectedItem().toString().equals("caja")) {
                    spinner1.setValue(100);
                    spinner1.setEnabled(false);
                }
            }
        });
        /*----------------------------------------------------------------------------------------------------------------------*/

        //accion de agregar productos (metodo agregarProducto)
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Panel_datos.setVisible(true);
                agregarProducto();
            }
        });
        /*----------------------------------------------------------------------------------------------------------------------*/

        //accion de cancelar el pedido
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

                    PanelCarrito.setVisible(false);
                    Panel_datos.setVisible(false);
                    spinner1.setValue(0);
                    comboBox1.setSelectedIndex(0);
                    comboBox2.setSelectedIndex(0);
                    comboBoxClientes.setSelectedIndex(0);
                    textField1.setText("");
                    textField11.setText("");
                    total = 0;
                }

            }
        });
        /*----------------------------------------------------------------------------------------------------------------------*/

        //accion de generar la venta
        generarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //Cliente y ID del cliente
                ClientesItem clientesItem = (ClientesItem) comboBoxClientes.getSelectedItem();
                int idcliente = clientesItem.getId();
                /////////////////////////////////////////////////////////////////////////////

                //variables
                String fecha_hora = textField4.getText();
                String estado = "Entregado";
                String metodo = comboBox2.getSelectedItem().toString();
                Double tot = Double.parseDouble(textField11.getText());
                ///////////////////////////////////////////////////////

                //variables sql
                Connection con = null;
                PreparedStatement psOrden = null;
                PreparedStatement psProductos = null;
                ResultSet rs = null;
                /////////////////////////////////////

                //bloque try
                try {
                    con = cf.getConnection();
                    con.setAutoCommit(false);

                    //insertar datos primero en los pedidos
                    String sqlOrden = "INSERT INTO pedidos (id_cliente, fecha_hora, estado, metodo_pago, total) VALUES (?, ?, ?, ?, ?)";
                    psOrden = con.prepareStatement(sqlOrden, Statement.RETURN_GENERATED_KEYS); // Retornar clave generada
                    psOrden.setInt(1, idcliente);
                    psOrden.setString(2, fecha_hora);
                    psOrden.setString(3, estado);
                    psOrden.setString(4, metodo);
                    psOrden.setDouble(5, tot);
                    psOrden.executeUpdate();
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    // Obtener el ID del pedidoo recién insertado
                    rs = psOrden.getGeneratedKeys();
                    int idPedido = -1;
                    if (rs.next()) {
                        idPedido = rs.getInt(1);
                    } else {
                        throw new SQLException("Error al obtener el ID");
                    }
                    ////////////////////////////////////////////////////

                    /*----------------------------------------------------------------------------------------------------------------*/

                    // Insertar los productos en detalle pedidos
                    String sqlProductoOrden = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, tipo_cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
                    psProductos = con.prepareStatement(sqlProductoOrden);

                    DefaultTableModel model = (DefaultTableModel) tablaCarrito.getModel();
                    int rowCount = model.getRowCount();

                    if (rowCount == 0) {
                        JOptionPane.showMessageDialog(null, "El carrito está vacío. Agregue productos antes de generar la venta.");
                        con.rollback();
                        return;
                    }


                    // Insertar cada producto en la tabla detalle_pedido usando la jtable carrito
                    for (int i = 0; i < rowCount; i++) {
                        /*----------------------------------------------------------------------------------------------------------------------*/
                        int idPro = Integer.parseInt(model.getValueAt(i, 1).toString()); //id del producto
                        int can = Integer.parseInt(tablaCarrito.getValueAt(i, 4).toString()); // cantidad
                        String t_can = tablaCarrito.getValueAt(i, 3).toString(); //Obtener tipo_cantidad
                        int pre_u = Integer.parseInt(tablaCarrito.getValueAt(i, 5).toString()); //precio_unitario
                        int sub = Integer.parseInt(tablaCarrito.getValueAt(i, 6).toString()); //subtotal
                        /*----------------------------------------------------------------------------------------------------------------------*/
                        psProductos.setInt(1, idPedido);
                        psProductos.setInt(2, idPro);
                        psProductos.setInt(3, can);
                        psProductos.setString(4, t_can);
                        psProductos.setInt(5, pre_u);
                        psProductos.setDouble(6, sub);
                        psProductos.addBatch(); // Agregar al batch
                    }
                    psProductos.executeBatch();
                    con.commit();
                    /*----------------------------------------------------------------------------------------------------------------------*/
                    JOptionPane.showMessageDialog(null, "Venta generada con éxito.");
                    /*----------------------------------------------------------------------------------------------------------------------*/

                } catch (SQLException ex) {
                    try {
                        if (con != null) {
                            con.rollback(); //si hay error
                        }
                    } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                    }
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al generar la venta. Intente nuevamente.");
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (psOrden != null) psOrden.close();
                        if (psProductos != null) psProductos.close();
                        if (con != null) con.close();
                    } catch (SQLException closeEx) {
                        closeEx.printStackTrace();
                    }
                }

            }

        });
    }
    //fin de las acciones
/************************************************************************************************************************/

        //métodos

        /*-------------------------------------------------------------------------------------------------------------*/

        //accion de la tabla para identificar la fila seleccionada
        private void agregarListenerTabla () {
            tablaProductos.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && tablaProductos.getSelectedRow() != -1) {
                    int filaSeleccionada = tablaProductos.getSelectedRow();
                    mostrarDetallesProducto(filaSeleccionada);
                }
            });

        }

        /*-------------------------------------------------------------------------------------------------------------*/

        //metodo traer detalles del producto al ser seleccionado
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

        /*-------------------------------------------------------------------------------------------------------------*/

        //metodo de agregar productos al carrito
        void agregarProducto(){

            //comienza la orden
            textField1.setText("En preparacion...");
            ///////////////////////////////////////


            //variables de condición(obtenemos el stock normal y min)
            int stockmin = Integer.parseInt(textField8.getText());
            int stock = Integer.parseInt(textField6.getText());
            ////////////////////////////////////////////////////////

            model=(DefaultTableModel)tablaCarrito.getModel();

            //variables normales para agregar al carrito
            int cantidad = (int) spinner1.getValue();
            String prod="";
            String t_cantidad = comboBox1.getSelectedItem().toString();
            int precio_u = Integer.parseInt(textField7.getText());
            ////////////////////////////////////////////////////////

            //obtener el id del producto seleccionado
            int fila = tablaProductos.getSelectedRow();
            idProducto = tablaProductos.getValueAt(fila, 0).toString();
            //////////////////////////////////////////////////////////////////

            if (fila >= 0 && fila < tablaProductos.getRowCount()) {
                prod = tablaProductos.getValueAt(fila, 1).toString(); //columna(1): nombre del producto
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un producto");
                return;
            }

            if(stock == stockmin ){
                JOptionPane.showMessageDialog(null, "Este producto ha llegado al stock mínimo");
            }else if (comboBoxClientes.getSelectedItem().equals("CLIENTES")) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado un cliente");
            }else if(cantidad <= 0){
                JOptionPane.showMessageDialog(null, "La cantidad ingresada es incorrecta");
            }
            else if(stock > 0 && stock > stockmin && cantidad > 0){

                //activar panel del carrito
                PanelCarrito.setVisible(true);

                //definir subtotal
                int sub_total = precio_u * cantidad;

                //incrementar total en cada producto
                total += sub_total;
                textField11.setText(String.valueOf(total));

                //agregar productos
                ArrayList lista = new ArrayList();
                lista.add(item+=1);
                lista.add(idProducto);
                lista.add(prod);
                lista.add(t_cantidad);
                lista.add(cantidad);
                lista.add(precio_u);
                lista.add(sub_total);

                Object[] ob = new Object[7];
                ob[0] = lista.get(0);
                ob[1] = lista.get(1);
                ob[2] = lista.get(2);
                ob[3] = lista.get(3);
                ob[4] = lista.get(4);
                ob[5] = lista.get(5);
                ob[6] = lista.get(6);

                model.addRow(ob);
                tablaCarrito.setModel(model); //le damos el modelo al carrito

            }else{
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error, intentelo de nuevo");
            }

        }

        /*-------------------------------------------------------------------------------------------------------------*/

        //cargar productos en la tabla productos
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

        /*-------------------------------------------------------------------------------------------------------------*/

        //definir columnas en la tabla del carrito
        public void Carrito() {
            DefaultTableModel pedidos = new DefaultTableModel();
            pedidos.addColumn("Item");
            pedidos.addColumn("ID");
            pedidos.addColumn("Producto");
            pedidos.addColumn("tipo de Cant");
            pedidos.addColumn("Cantidad");
            pedidos.addColumn("Precio unitario");
            pedidos.addColumn("Subtotal");
            tablaCarrito.setModel(pedidos);
        }

        /*-------------------------------------------------------------------------------------------------------------*/

        //cargar clientes en el combobox de clientes
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
        //fin de metodos
/************************************************************************************************************************/
    //main
    public void runPedidos() {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new PedidosGUI().PanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,700);

        frame.setResizable(false);
        frame.setVisible(true);
    }
/************************************************************************************************************************/

    //clase para obtener el id del cliente
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

//fin del codigo --jArtur
//funciones a implementar: buscar por cedula, mejorar interfaz


