package Pedidos;

import Caja.CajaGUI;
import Conexion.Conexion;
import Correo.EnviarCorreo;
import Historial.HistorialPedidos;
import Producto.ProductoGUI;
import Sockets.ChatServer;
import Sockets.GUIComunicacionServer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PedidosGUI {
    private JTextField textField4;
    private JComboBox comboBoxTipo;
    private JSpinner spinnercantidad;
    private JComboBox comboBox2;
    private JPanel PanelPrincipal;
    private JTable tablaProductos;
    private JTextField estadotxt;
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
    private JTextField totaltxt;
    private JPanel PanelCarrito;
    private JTextField cedulatxt;
    private JComboBox comboBoxProductos;
    private JTextField preciotxt;
    private JTextField stocktxt;
    private JTextField stockminimotxt;
    private JPanel Panelcantidad;
    private JPanel PanelCliente;
    private JButton mostrarCajaButton;
    private JTextField buscar_cliente;
    private JTextField buscar_productos;
    private JComboBox comboBoxMetodo;
    private JPanel main;
    private JButton BackButton;
    private JCheckBox IVACheckBox;
    int sub_total = 0;
    private JFrame frame, parentFrame;
    //JFrame frame = new JFrame("Main");

    //private JFormattedTextField stockminimotxt;

    /************************************************************************************************************************/
    //map para el stock simulado
    private Map<Integer, Integer> stockSimulado = new HashMap<>();

    //total del pedido
    int total = 0;

    int tot = 0;

    //item del carrito (auto incremental)
    int item = 0;

    //nombre del producto agregado
    String product="";

    //id del producto agregado
    String idProducto="";

    int precio_u = 0;

            //objeto defaultTableModel
    DefaultTableModel model = new DefaultTableModel();


/*************************************************************************************************************************/
    //conexion a la base de datos(cf:objeto de la conexion)
    Conexion cf = new Conexion();

    //importar caja
    CajaGUI c = new CajaGUI();

    //importarChat
    //GUIComunicacionServer chat = new GUIComunicacionServer();
    //ChatServer cs = new ChatServer();

    //historial pedidos
    HistorialPedidos h = new HistorialPedidos();
    private String textico;

    GenerarPDF pdf = new GenerarPDF();

    EnviarCorreo correo = new EnviarCorreo();

    /************************************************************************************************************************/

    public PedidosGUI(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        // Cambiar color y fuente de los labels
        for (Component c : main.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setForeground(Color.BLACK);
                label.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        aplicarEstilos();


/************************************************************************************************************************/
        //darle tamaño al combobox
        comboBoxClientes.setPreferredSize(new Dimension(300, 20));
        comboBoxProductos.setPreferredSize(new Dimension(300, 20));
        comboBoxTipo.setPreferredSize(new Dimension(300, 20));
        spinnercantidad.setPreferredSize(new Dimension(300, 20));

        //textfield oculto
        stockminimotxt.setVisible(false);

        //activar iva
        IVACheckBox.setSelected(true);

        //




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

        //configurar la tabla carrito con sus respectivas columnas
        Carrito();

        //cargar Productos
        cargarProductos();


/************************************************************************************************************************/

        //Paneles ocultos, (carrito y datos del producto)
        Panel_datos.setVisible(false);
        PanelCarrito.setVisible(false);

        //textfield que no se pueden editar
        totaltxt.setEditable(false);
        cedulatxt.setEditable(false);
        stocktxt.setEditable(false);
        preciotxt.setEditable(false);

        //color del panel de datos (gris claro)
        Panelcantidad.setBackground(new Color(255, 255, 255));
        PanelCliente.setBackground(new Color(255, 255, 255));


        //color de los textfield en el panel de datos (azul claro)
        estadotxt.setBackground(new Color(220, 230, 240));

/************************************************************************************************************************/
        //buscar cliente documentlistener
        buscar_cliente.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarClientes(buscar_cliente.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarClientes(buscar_cliente.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        buscar_productos.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarProductos(buscar_productos.getText().trim().toLowerCase());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarProductos(buscar_productos.getText().trim().toLowerCase());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });


        /*----------------------------------------------------------------------------------------------------------------------*/
        //accion del combobox clientes
        comboBoxClientes.addActionListener(e -> {
            try {
                ClientesItem cliente = (ClientesItem) comboBoxClientes.getSelectedItem();
                if (cliente != null) {
                    cedulatxt.setText(String.valueOf(cliente.getCedula()));
                } else {
                    cedulatxt.setText(""); // Limpiar cedulatxt si no hay cliente seleccionado
                }
            } catch (ClassCastException ex) {
                cedulatxt.setText(""); // Limpiar cedulatxt en caso de error
            }

        });
        /*----------------------------------------------------------------------------------------------------------------------*/

        //accion del combobox clientes
        comboBoxProductos.addActionListener(e -> {
            try {
                ProductosItem productosItem = (ProductosItem) comboBoxProductos.getSelectedItem();
                if (productosItem != null) {
                    preciotxt.setText(String.valueOf(productosItem.getStock_minimo()));
                    stocktxt.setText(String.valueOf(productosItem.getStock()));
                    stockminimotxt.setText(String.valueOf(productosItem.getPrecio_unitario()));
                    int stock = Integer.parseInt(stocktxt.getText());
                    int stockm = Integer.parseInt(stockminimotxt.getText());
                    if (stock == stockm) {
                        stocktxt.setForeground(Color.red);
                    } else {
                        stocktxt.setForeground(Color.black);
                    }
                } else {
                    stocktxt.setText("");
                    stockminimotxt.setText("");
                    preciotxt.setText("");
                }
            } catch (ClassCastException ex) {
                stocktxt.setText("");
                stockminimotxt.setText("");
                preciotxt.setText("");
            }
        });

        /*----------------------------------------------------------------------------------------------------------------------*/

        //accion de agregar productos (metodo agregarProducto)
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //chat.enviarMsj("Su pedido esta siendo preparado");
                try{
                    if (comboBoxClientes.getSelectedItem().equals("CLIENTES")) {
                        JOptionPane.showMessageDialog(null, "No se ha seleccionado un cliente");
                    }else{
                        agregarProducto();
                        buscar_cliente.setText("");
                        buscar_productos.setText("");
                    }
                }catch(ClassCastException ex){

                }

            }
        });
        /*----------------------------------------------------------------------------------------------------------------------*/

        //accion de cancelar el pedido
        cancelarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int respuesta = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro? Se borran los pedidos",
                            "Confirmar acción",
                            JOptionPane.YES_NO_OPTION
                    );

                    // El usuario acepta cancelar el pedido
                    if (respuesta == JOptionPane.YES_OPTION) {
                        comboBoxClientes.setEnabled(true);
                        model = (DefaultTableModel) tablaCarrito.getModel();
                        model.setRowCount(0);
                        stockSimulado.clear(); //limpiar el simulador de stock

                        PanelCarrito.setVisible(false);
                        Panel_datos.setVisible(false);
                        spinnercantidad.setValue(0);
                        comboBoxTipo.setSelectedIndex(0);
                        comboBoxClientes.setSelectedIndex(0);
                        comboBoxProductos.setSelectedIndex(0);
                        cedulatxt.setText("");
                        preciotxt.setText("");
                        stocktxt.setText("");
                        estadotxt.setText("");
                        totaltxt.setText("");
                        total = 0;
                        buscar_cliente.setText("");
                        buscar_productos.setText("");
                    }
                } catch (NumberFormatException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        /*----------------------------------------------------------------------------------------------------------------------*/

        //accion de generar la venta
        generarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    //Cliente y ID del cliente
                    ClientesItem clientesItem = (ClientesItem) comboBoxClientes.getSelectedItem();
                    int idcliente = clientesItem.getId();
                    /////////////////////////////////////////////////////////////////////////////

                    //variables
                    String cliente = comboBoxClientes.getSelectedItem().toString();
                    String fecha_hora = textField4.getText();
                    String estado = "Entregado";
                    String metodo = comboBoxMetodo.getSelectedItem().toString();
                    tot =Integer.parseInt(totaltxt.getText());
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
                        IVA();
                        //insertar datos primero en los pedidos
                        String sqlOrden = "INSERT INTO pedidos (id_cliente, fecha_hora, estado, metodo_pago, total) VALUES (?, ?, ?, ?, ?)";
                        psOrden = con.prepareStatement(sqlOrden, Statement.RETURN_GENERATED_KEYS); // Retornar clave generada
                        psOrden.setInt(1, idcliente);
                        psOrden.setString(2, fecha_hora);
                        psOrden.setString(3, estado);
                        psOrden.setString(4, metodo);
                        psOrden.setInt(5, tot);
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
                        int idDetalle = insertarDetalleFinanciero(metodo, tot, 0, "Pedido de: "+ cliente, fecha_hora); //se lleva el registro del pedido a movimientos
                        if (idDetalle != -1) {
                            c.EnviarDinero(idDetalle,"Pedido de: " + cliente, tot); // Pasar el id a EnviarDinero
                        }
                        estadotxt.setText("El pedido ha sido entregado ✔️");
                        //parte de PDF
                        PedidosDAO pedidoDAO = new PedidosDAO();
                        java.util.List<String> productos = pedidoDAO.obtenerProductosPorPedido(idPedido); // ID del pedido
                        int respuesta = JOptionPane.showConfirmDialog(null,
                                "Desea enviar la factura al cliente",
                                "Confirmar acción",
                                JOptionPane.YES_NO_OPTION
                        );

                        // El usuario acepta agregar el egreso aunque reste en la caja
                        if (respuesta == JOptionPane.YES_OPTION) {
                            pdf.generarFacturaPDF(1, productos, fecha_hora);
                            FacturaPorGmail(idcliente);
                        }

                        comboBoxClientes.setEnabled(true);
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
                }catch (ClassCastException ex){
                    JOptionPane.showMessageDialog(null, "Seleccione un cliente");
                }
                cedulatxt.setText("");
                comboBoxClientes.setSelectedIndex(0);
                comboBoxProductos.setSelectedIndex(0);
                comboBoxTipo.setSelectedIndex(0);
                stocktxt.setText("");
                preciotxt.setText("");
                totaltxt.setText("");
                spinnercantidad.setValue(0);
                //mostrarCajaButton.setVisible(true);
                model = (DefaultTableModel) tablaCarrito.getModel();
                model.setRowCount(0);
                buscar_productos.setText("");
                buscar_cliente.setText("");
                }
            });

        /*----------------------------------------------------------------------------------------------------------------------*/

        tablaCarrito.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                borrarFilaSeleccionada();
            }
        });

        /*----------------------------------------------------------------------------------------------------------------------*/


        BackButton.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
            frame.dispose();
        });
    }
    //fin de las acciones

    public void aplicarEstilos() {
        Panelcantidad.setBackground(Color.WHITE);
        agregarProductoButton.setBackground(new Color(0, 51, 102));
        BackButton.setBackground(new Color(0, 51, 102));

        agregarProductoButton.setForeground(Color.WHITE);
        BackButton.setForeground(Color.WHITE);

        PanelCarrito.setBackground(Color.WHITE);
        generarVentaButton.setBackground(new Color(0, 51, 102));
        cancelarPedidoButton.setBackground(new Color(0, 51, 102));

        generarVentaButton.setForeground(Color.WHITE);
        cancelarPedidoButton.setForeground(Color.WHITE);


        JTableHeader header = tablaCarrito.getTableHeader();
        header.setBackground(new Color(0, 51, 102));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaCarrito.getColumnCount(); i++) {
            tablaCarrito.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


    }

    private void agregarImagenFondo() {
        JLabel labelFondo = new JLabel(new ImageIcon(getClass().getResource("imagenes/img_8.png")));
        PanelPrincipal.add(labelFondo); // Agregar la imagen al panel
        PanelPrincipal.revalidate(); // Refrescar la interfaz
        PanelPrincipal.repaint(); // Redibujar el panel
    }

    /************************************************************************************************************************/

        /*-------------------------------------------------------------------------------------------------------------*/

        //metodo de agregar productos al carrito
        void agregarProducto() {
            try {
                DefaultTableModel model = (DefaultTableModel) tablaCarrito.getModel();

                // Variables de condición (obtenemos el stock normal y min)
                int stockmin = Integer.parseInt(stockminimotxt.getText());
                int stockReal = Integer.parseInt(stocktxt.getText());

                // Variables normales para agregar al carrito
                int cantidad = (int) spinnercantidad.getValue();
                String prod = comboBoxProductos.getSelectedItem().toString();
                String t_cantidad = comboBoxTipo.getSelectedItem().toString();
                precio_u = Integer.parseInt(preciotxt.getText());

                // Obtener el id del producto seleccionado
                ProductosItem productosItem = (ProductosItem) comboBoxProductos.getSelectedItem();
                int idProducto = productosItem.getId();

                // Obtener el stock simulado actual (si existe)
                int stockActual = stockSimulado.getOrDefault(idProducto, stockReal);

                // Condiciones:
                if (stockActual == stockmin) { // El producto llegó al stock mínimo en la base de datos
                    int respuesta = JOptionPane.showConfirmDialog(null,
                            "El producto ha llegado al stock minimo\n   ¿Desea ir a productos?",
                            "Confirmar acción",
                            JOptionPane.YES_NO_OPTION);
                    // El admin rechaza ver productos
                    if (respuesta == JOptionPane.YES_OPTION) {
                        frame.dispose();
                        ProductoGUI p = new ProductoGUI(frame);
                        p.runProducto();
                    }
                    return;
                } else if (cantidad <= 0) { // La cantidad es errónea
                    JOptionPane.showMessageDialog(null, "La cantidad ingresada es incorrecta");
                    return;
                }

                // Ajustar cantidad y subtotal según el tipo
                int cantidadReal = cantidad;
                if (t_cantidad.equals("blister")) {
                    cantidadReal = 10 * cantidad;
                } else if (t_cantidad.equals("caja")) {
                    cantidadReal = 100 * cantidad;
                }

                sub_total = precio_u * cantidadReal;

                // Verificar stock mínimo
                if (cantidadReal > stockActual) {
                    JOptionPane.showMessageDialog(null, "La cantidad agregada sobrepasaría el stock");
                    return;
                }else if (cantidadReal > stockReal) {
                    JOptionPane.showMessageDialog(null, "La cantidad agregada sobrepasa el stock");
                    return;
                }else if(stockActual - cantidadReal < stockmin){
                    JOptionPane.showMessageDialog(null, "La cantidad agregada sobrepasa el stock mínimo");
                    return;
                }

                // Actualizar stock simulado. llevar resta del stock falso
                stockActual -= cantidadReal;
                stockSimulado.put(idProducto, stockActual);

                // Agregar producto al carrito
                comboBoxClientes.setEnabled(false);



                PanelCarrito.setVisible(true);
                //iva: se debe calcular el iva primero para que el total se incremente con el iva en cada producto

                // Incrementar total en cada producto
                total += sub_total;
                totaltxt.setText(String.valueOf(total));

                // Agregar productos a la tabla
                Object[] ob = new Object[7];
                ob[0] = model.getRowCount() + 1; // Número de ítem
                ob[1] = idProducto;
                ob[2] = prod;
                ob[3] = t_cantidad;
                ob[4] = cantidadReal;
                ob[5] = precio_u;
                ob[6] = sub_total;
                model.addRow(ob);

                // Se agregó el producto sin errores
                estadotxt.setText("En preparacion...");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Agregue un cliente y producto válidos.");
                ex.printStackTrace(); // Imprimir el rastreo de la excepción para depuración
            }

        }

        /*-------------------------------------------------------------------------------------------------------------*/

        //definir columnas en la tabla del carrito
        public void Carrito() {
            DefaultTableModel pedidos = new DefaultTableModel();
            pedidos.addColumn("Item");
            pedidos.addColumn("ID"); //definir columna id como integer
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

                String query = "SELECT id_cliente, cedula, nombre FROM cliente";
                ResultSet rs = statement.executeQuery(query);

                // Agrega cada nombre al JComboBox
                while (rs.next()) {
                    int idcliente = rs.getInt("id_cliente");
                    String cedula = rs.getString("cedula");
                    String cliente = rs.getString("nombre");
                    comboBoxClientes.addItem(new ClientesItem(idcliente, cedula, cliente));
                }

                rs.close();
                statement.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar clientes.");
            }
        }

        /*-------------------------------------------------------------------------------------------------------------*/

        private void cargarProductos() {
            try {
                Connection con = cf.getConnection();
                Statement statement = con.createStatement();

                String query = "SELECT id_producto, nombre, stock, stock_minimo, precio_unitario FROM producto";
                ResultSet rs = statement.executeQuery(query);

                // Agrega cada nombre al JComboBox
                while (rs.next()) {
                    int id_producto = rs.getInt("id_producto");
                    String nombre = rs.getString("nombre");
                    int stock = rs.getInt("stock");
                    int stockm = rs.getInt("stock_minimo");
                    int preciou = rs.getInt("precio_unitario");
                    comboBoxProductos.addItem(new ProductosItem(id_producto, nombre, stock, preciou, stockm));
                }

                rs.close();
                statement.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar productos.");
            }
        }

        /*-------------------------------------------------------------------------------------------------------------*/

        private void buscarClientes(String texto) {
            new Thread(() -> {
                try {
                    Connection con = cf.getConnection();
                    String query = "SELECT id_cliente, cedula, nombre FROM cliente WHERE nombre LIKE ?";
                    PreparedStatement statement = con.prepareStatement(query);
                    statement.setString(1, "%" + texto + "%");

                    ResultSet resultSet = statement.executeQuery();

                    DefaultComboBoxModel<ClientesItem> model = new DefaultComboBoxModel<>();
                    while (resultSet.next()) {
                        int idCliente = resultSet.getInt("id_cliente");
                        String cedula = resultSet.getString("cedula");
                        String nombre = resultSet.getString("nombre");
                        model.addElement(new ClientesItem(idCliente, cedula, nombre));
                    }

                    SwingUtilities.invokeLater(() -> {
                        comboBoxClientes.setModel(model);
                    });

                    resultSet.close();
                    statement.close();
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }).start();
        }

        /*-------------------------------------------------------------------------------------------------------------*/

        private void buscarProductos(String texto) {
            new Thread(() -> {
                try {
                    Connection con = cf.getConnection();
                    String query = "SELECT id_producto, nombre, stock, stock_minimo, precio_unitario FROM producto WHERE LOWER(nombre) LIKE ?";
                    PreparedStatement statement = con.prepareStatement(query);
                    statement.setString(1, "%" + texto + "%");

                    ResultSet resultSet = statement.executeQuery();

                    DefaultComboBoxModel<ProductosItem> model = new DefaultComboBoxModel<>();
                    while (resultSet.next()) {
                        int id_Producto = resultSet.getInt("id_producto");
                        String nombre = resultSet.getString("nombre");
                        int stock = resultSet.getInt("stock");
                        int stockm = resultSet.getInt("stock_minimo");
                        int prec = resultSet.getInt("precio_unitario");
                        model.addElement(new ProductosItem(id_Producto, nombre, stock, prec, stockm));
                    }

                    SwingUtilities.invokeLater(() -> {
                        comboBoxProductos.setModel(model);
                    });

                    resultSet.close();
                    statement.close();
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }).start();
        }

        /*-------------------------------------------------------------------------------------------------------------*/

        private void borrarFilaSeleccionada() {
            int filaSeleccionada = tablaCarrito.getSelectedRow();
            if (filaSeleccionada != -1) {
                DefaultTableModel model = (DefaultTableModel) tablaCarrito.getModel();

                // Obtener el ID del producto y la cantidad del producto eliminado
                int idProducto = Integer.parseInt(model.getValueAt(filaSeleccionada, 1).toString());
                int cantidadEliminada = Integer.parseInt(model.getValueAt(filaSeleccionada, 4).toString());


                // Obtener el subtotal del producto eliminado
                int subtotal = Integer.parseInt(model.getValueAt(filaSeleccionada, 6).toString());

                // Restar el subtotal del total
                total -= subtotal;
                totaltxt.setText(String.valueOf(total));

                // Obtener el stock simulado actual del producto
                int stockSimuladoActual = stockSimulado.getOrDefault(idProducto, Integer.parseInt(stocktxt.getText()));

                // Restablecer el stock simulado del producto eliminado
                stockSimulado.put(idProducto, stockSimuladoActual + cantidadEliminada);

                // Eliminar la fila de la tabla
                model.removeRow(filaSeleccionada);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para borrar.");
            }
        }

        /*-------------------------------------------------------------------------------------------------------------*/

        public int insertarDetalleFinanciero(String tipopago, int ingreso, int egreso, String descripcion, String fechah) {
            Connection con = cf.getConnection();
            PreparedStatement psDetalle = null;
            ResultSet generatedKeys = null;

            try {
                String sqlDetalle = "INSERT INTO detalle_financiero (tipo_pago, ingreso, egreso, descripcion, fecha_hora) VALUES (?, ?, ?, ?, ?)";
                psDetalle = con.prepareStatement(sqlDetalle, Statement.RETURN_GENERATED_KEYS);
                psDetalle.setString(1, tipopago);
                psDetalle.setInt(2, ingreso);
                psDetalle.setInt(3, egreso);
                psDetalle.setString(4, descripcion);
                psDetalle.setString(5, fechah);
                psDetalle.executeUpdate();

                generatedKeys = psDetalle.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Devuelve el id generado
                } else {
                    throw new SQLException("No se pudo obtener el id generado.");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Cerrar recursos
                try {
                    if (generatedKeys != null) generatedKeys.close();
                    if (psDetalle != null) psDetalle.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }return -1;
        }

        /*-------------------------------------------------------------------------------------------------------------*/

        public void IVA(){
            if(IVACheckBox.isSelected()){
                double iva = tot * 0.19;
                tot = (int) (tot+iva);
                JOptionPane.showMessageDialog(null, "El total con IVA es: " + "$"+ tot);

            }
        }

        /*-------------------------------------------------------------------------------------------------------------*/

        public void FacturaPorGmail(int idCliente){
            try {
                Connection con = cf.getConnection();

                //Preparar la consulta SQL para obtener el correo electrónico del cliente
                String consulta = "SELECT correo FROM cliente WHERE id_cliente = ?";
                PreparedStatement sentencia = con.prepareStatement(consulta);
                sentencia.setInt(1, idCliente); //recibir el id del cliente

                //Ejecutar la consulta y obtener el resultado
                ResultSet resultado = sentencia.executeQuery();

                //Verificar si se encontró el correo electrónico y obtenerlo
                if (resultado.next()) {
                    String correoCliente = resultado.getString("correo");
                    correo.EnviarCorreo(correoCliente);
                } else {
                    System.out.println("No se encontró el correo electrónico del cliente.");
                }

                // 6. Cerrar la conexión y los recursos
                resultado.close();
                sentencia.close();
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }






        //fin de metodos
/************************************************************************************************************************/
    //main
    public void RunPedidos() {
        frame = new JFrame("Pedidos");
        frame.setContentPane(new PedidosGUI(frame).PanelPrincipal);
        // Centrar el JFrame después de agregar el PanelPrincipal
        frame.setLocationRelativeTo(null);

        FondoPanel fondoPanel = new FondoPanel();
        PanelPrincipal.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(PanelPrincipal, BorderLayout.CENTER);

        URL iconoURL = getClass().getClassLoader().getResource("imagenes/img_16.png");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }

        frame.setContentPane(fondoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,800);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/img_11.png");
            if (imagenURL != null) {
                this.imagenFondo = new ImageIcon(imagenURL).getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

}



/************************************************************************************************************************/

    //clase para obtener el id del cliente
    class ClientesItem {
        private int id_cliente;
        private String nombre, cedula;

        public ClientesItem(int id_cliente, String cedula, String nombre) {
            this.id_cliente= id_cliente;
            this.nombre = nombre;
            this.cedula = cedula;
        }

        public int getId() {
            return id_cliente;
        }

        public String getCedula(){return cedula;}

        @Override
        public String toString() {
            return nombre;
        }
    }


    //clase para obtener el id del cliente
    class ProductosItem {
        private int id_producto, stock, stock_minimo, precio_unitario;
        private String nombre;

        public ProductosItem(int id_producto, String nombre, int stock, int stock_minimo, int precio_unitario) {
            this.id_producto = id_producto;
            this.nombre = nombre;
            this.stock = stock;
            this.stock_minimo = stock_minimo;
            this.precio_unitario = precio_unitario;

        }

        public int getId() {
            return id_producto;
        }

        public int getPrecio_unitario(){return precio_unitario;}

        public int getStock_minimo() {return stock_minimo;}

        public int getStock(){return stock;}

        @Override
        public String toString() {
            return nombre;
        }
    }




//fin del codigo --jArtur
//mayoria del codigo finalizado ✔️


