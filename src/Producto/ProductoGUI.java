package Producto;
import Cliente.ClienteGUI;
import Conexion.Conexion;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import static java.sql.Date.valueOf;

/**
 * La clase `ProductoGUI` proporciona una interfaz gráfica para la gestión de productos.
 *
 * @author Nicolle
 */
public class ProductoGUI {
    /** El panel principal que contiene todos los componentes de la GUI. */
    private JPanel main;
    /** Campos de texto para los datos del producto. */
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    /** Botones para registrar, actualizar, eliminar y volver. */
    private JButton registrarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton BackButton;
    /** La tabla que muestra los datos de los productos. */
    private JTable table1;
    /** Campos de texto adicionales para datos del producto. */
    private JTextField textField7;
    private JTextField textField9;
    private JTextField textField8;
    private JTextField textField10;
    /** Botón para realizar búsquedas. */
    private JButton button1;
    /** Campo de texto para realizar búsquedas. */
    private JTextField campoBusqueda;
    /** El frame principal de la GUI. */
    private JFrame frame;
    /** El frame padre de la GUI. */
    private JFrame parentFrame;
    /** Objeto para la conexión a la base de datos. */
    private Conexion connectionFA = new Conexion();
    /** Objeto DAO para interactuar con la base de datos de productos. */
    ProductoDAO productoDAO = new ProductoDAO();
    /** Objeto Producto para almacenar datos de productos. */
    Producto producto = new Producto();
    /** Variable para almacenar el número de filas seleccionadas. */
    int rows = 0;

    /**
     * Constructor de `ProductoGUI`.
     *
     * @param parentFrame El frame padre de esta GUI.
     */
    public ProductoGUI(JFrame parentFrame)
    {
        textField1.setEditable(false);
        textField1.setVisible(false);

        this.parentFrame = parentFrame;

        Dimension backButtonSize = new Dimension(86, 23);
        BackButton.setPreferredSize(backButtonSize);
        BackButton.setMinimumSize(backButtonSize);
        BackButton.setMaximumSize(backButtonSize);

        obtainInvent();
        // Cambiar color y fuente de los labels
        for (Component c : main.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;

                // Cambiar color del texto a blanco
                label.setForeground(Color.WHITE);

                // Aplicar la misma fuente de la tabla
                Font fontTabla = table1.getFont();
                label.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        aplicarEstilos();

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si algún campo está vacío
                if (textField2.getText().trim().isEmpty() ||
                        textField3.getText().trim().isEmpty() ||
                        textField4.getText().trim().isEmpty() ||
                        textField5.getText().trim().isEmpty() ||
                        textField6.getText().trim().isEmpty() ||
                        textField7.getText().trim().isEmpty() ||
                        textField8.getText().trim().isEmpty() ||
                        textField9.getText().trim().isEmpty() ||
                        textField10.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                    return;
                }

                // Obtener valores de los campos
                String nombre = textField2.getText();
                String categoria = textField3.getText();
                int stock = Integer.parseInt(textField4.getText());
                int stock_minimo = Integer.parseInt(textField5.getText());
                Date fecha_vencimiento = valueOf(textField6.getText());
                String indicaciones = textField9.getText();
                String almacen = textField8.getText();
                String lote = textField10.getText();

                String precio_t = (textField5.getText());

                if (!precio_t.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "El campo de precio solo debe contener números");
                    return;
                }

                String nombreProducto = textField2.getText();
                if(productoDAO.existeProducto(nombreProducto)){
                    JOptionPane.showMessageDialog(null, "El producto ya existe");
                    return;
                }else{
                    int precio_unitario = Integer.parseInt(precio_t);

                    //se agrega el producto
                    Producto producto = new Producto(0,  nombre, categoria, stock, stock_minimo, precio_unitario, fecha_vencimiento, indicaciones, almacen, lote);
                    productoDAO.agregar(producto);
                    JOptionPane.showMessageDialog(null, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    clear();
                    obtainInvent();
                }


            }
        });




        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si algún campo obligatorio está vacío
                if (textField2.getText().trim().isEmpty() ||
                        textField3.getText().trim().isEmpty() ||
                        textField4.getText().trim().isEmpty() ||
                        textField5.getText().trim().isEmpty() ||
                        textField6.getText().trim().isEmpty() ||
                        textField7.getText().trim().isEmpty() ||
                        textField8.getText().trim().isEmpty() ||
                        textField9.getText().trim().isEmpty() ||
                        textField10.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    // Obtener valores de los campos
                    int id_producto = Integer.parseInt(textField1.getText());
                    String nombre = textField2.getText();
                    String categoria = textField3.getText();
                    int stock = Integer.parseInt(textField4.getText());
                    int stock_minimo = Integer.parseInt(textField7.getText());
                    Date fecha_vencimiento = valueOf(textField6.getText());
                    String indicaciones = textField9.getText();
                    String almacen = textField8.getText();
                    String lote = textField10.getText();

                    String precio_t = (textField5.getText());

                    if (!precio_t.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo de precio solo debe contener números");
                        return;
                    }

                    int precio_unitario = Integer.parseInt(precio_t);


                    Producto producto = new Producto(id_producto, nombre, categoria, stock, stock_minimo, precio_unitario, fecha_vencimiento, indicaciones, almacen, lote);
                    productoDAO.actualizar(producto);

                    clear();
                    obtainInvent();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error al convertir un campo numérico. Verifique los datos ingresados.");
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() ||
                        textField3.getText().trim().isEmpty() ||
                        textField4.getText().trim().isEmpty() ||
                        textField5.getText().trim().isEmpty() ||
                        textField6.getText().trim().isEmpty() ||
                        textField7.getText().trim().isEmpty() ||
                        textField8.getText().trim().isEmpty() ||
                        textField9.getText().trim().isEmpty() ||
                        textField10.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete all fields");
                }else{
                    int id_producto = Integer.parseInt(textField1.getText());
                    productoDAO.eliminar(id_producto);

                    clear();
                    obtainInvent();
                }


            }
        });

        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BackButton.setBackground(new Color(51, 153, 255)); // Azul claro al dar click
                if (parentFrame != null) {
                    parentFrame.setVisible(true);
                }
                frame.dispose();
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String terminoBusqueda = campoBusqueda.getText();
                try {
                    java.util.List<Producto> productos = buscarProductos(terminoBusqueda);
                    // Actualizar la tabla con los resultados
                    actualizarTablaProductos(productos);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al buscar productos");
                }
            }
        });



        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int selectedRows = table1.getSelectedRow();

                if (selectedRows >= 0) {
                    textField1.setText(String.valueOf(table1.getValueAt(selectedRows, 0))); // id_producto (Integer)
                    textField2.setText((String) table1.getValueAt(selectedRows, 1)); // Nombre (String)
                    textField3.setText((String) table1.getValueAt(selectedRows, 2)); // Categoria (String)
                    textField4.setText(String.valueOf(table1.getValueAt(selectedRows, 3))); // Stock (Integer)
                    textField7.setText(String.valueOf(table1.getValueAt(selectedRows, 4))); // Stock_Minimo (Integer)
                    textField5.setText(String.valueOf(table1.getValueAt(selectedRows, 5))); // Precio_Unitario (Integer)
                    textField6.setText(String.valueOf(table1.getValueAt(selectedRows, 6))); // Fecha Vencimiento (Date)
                    textField9.setText(String.valueOf(table1.getValueAt(selectedRows, 7))); // Indicaciones (String)
                    textField8.setText(String.valueOf(table1.getValueAt(selectedRows, 8))); // Almacen (String)
                    textField10.setText(String.valueOf(table1.getValueAt(selectedRows, 9))); // Lote (String)

                    rows = selectedRows;
                }
            }
        });
    }





    /**
     * Aplica estilos visuales a los componentes de la GUI.
     */
    public void aplicarEstilos() {
        main.setBackground(Color.DARK_GRAY);
        registrarButton.setBackground(new Color(0, 51, 102));
        actualizarButton.setBackground(new Color(0, 51, 102));
        eliminarButton.setBackground(new Color(0, 51, 102));
        BackButton.setBackground(new Color(0, 51, 102));

        registrarButton.setForeground(Color.WHITE);
        actualizarButton.setForeground(Color.WHITE);
        eliminarButton.setForeground(Color.WHITE);
        BackButton.setForeground(Color.WHITE);

        JTableHeader header = table1.getTableHeader();
        header.setBackground(new Color(0, 51, 102));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table1.getColumnCount(); i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    /**
     * Obtiene y muestra los datos de los productos en la tabla.
     */
    public void obtainInvent()
    {
        NonEditableTableModel modeloa = new NonEditableTableModel();
        table1.setDefaultEditor(Object.class, null);


        modeloa.addColumn("id_product");
        modeloa.addColumn("Nombre");
        modeloa.addColumn("Categoria");
        modeloa.addColumn("Stock");
        modeloa.addColumn("Stock_Minimo");
        modeloa.addColumn("Precio_Unitario");
        modeloa.addColumn("Fecha Vencimiento");
        modeloa.addColumn("Indicaciones");
        modeloa.addColumn("Almacen");
        modeloa.addColumn("Lote");

        table1.setModel(modeloa);

        String[] dato = new String[10];

        Connection con = connectionFA.getConnection();

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM producto");


            while (rs.next())
            {

                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);
                dato[6] = rs.getString(7);
                dato[7] = rs.getString(8);
                dato[8] = rs.getString(9);
                dato[9] = rs.getString(10);

                modeloa.addRow(dato);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Clase interna para un modelo de tabla no editable.
     */
    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }



    /**
     * Limpia los campos de texto.
     */
    public void clear()
    {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
        textField8.setText("");
        textField9.setText("");
        textField10.setText("");
    }

    /**
     * Inicia la GUI de gestión de productos.
     */
    public  void runProducto(){

        frame = new JFrame("Gestion de Productos");
        FondoPanel fondoPanel = new FondoPanel();
        main.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(main, BorderLayout.CENTER);

        URL iconoURL = getClass().getClassLoader().getResource("imagenes/img.png");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }

        frame.setContentPane(fondoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700,700);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Clase interna para el panel de fondo con una imagen.
     */
    class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/img_5.png");
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

    /**
     * Busca productos en la base de datos por nombre.
     *
     * @param terminoBusqueda El término de búsqueda.
     * @return Una lista de productos que coinciden con la búsqueda.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public java.util.List<Producto> buscarProductos(String terminoBusqueda) throws SQLException {

        PreparedStatement consulta = null;
        ResultSet resultado = null;
        java.util.List<Producto> productos = new ArrayList<>();

        try {
            Connection con = connectionFA.getConnection();
            String sql = "SELECT * FROM producto WHERE nombre LIKE ?";
            consulta = con.prepareStatement(sql);
            consulta.setString(1, "%" + terminoBusqueda + "%"); // Búsqueda parcial
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setId_producto(resultado.getInt("id_producto"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setCategoria(resultado.getString("categoria"));
                producto.setStock(resultado.getInt("stock"));
                producto.setStock_minimo(resultado.getInt("stock_minimo"));
                producto.setPrecio_unitario(resultado.getInt("precio_unitario"));
                producto.setFecha_vencimiento(resultado.getDate("fecha_vencimiento"));
                producto.setIndicaciones(resultado.getString("indicaciones"));
                producto.setAlmacen(resultado.getString("almacen"));
                producto.setLote(resultado.getString("lote"));
                productos.add(producto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    /**
     * Actualiza la tabla con los resultados de la búsqueda de productos.
     *
     * @param productos La lista de productos a mostrar en la tabla.
     */
    public void actualizarTablaProductos(java.util.List<Producto> productos) {
        DefaultTableModel modeloa = new DefaultTableModel();

        // Definir las columnas
        modeloa.addColumn("id_product");
        modeloa.addColumn("Nombre");
        modeloa.addColumn("Categoria");
        modeloa.addColumn("Stock");
        modeloa.addColumn("Stock_Minimo");
        modeloa.addColumn("Precio_Unitario");
        modeloa.addColumn("Fecha Vencimiento");
        modeloa.addColumn("Indicaciones");
        modeloa.addColumn("Almacen");
        modeloa.addColumn("Lote");

        for (Producto producto : productos) {
            Object[] fila = {
                    producto.getId_producto(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getStock(),
                    producto.getStock_minimo(),
                    producto.getPrecio_unitario(),
                    producto.getFecha_vencimiento(),
                    producto.getIndicaciones(),
                    producto.getAlmacen(),
                    producto.getLote()
            };
            modeloa.addRow(fila);
        }

        // Establecer el modelo en la tabla
        table1.setModel(modeloa);
    }
}