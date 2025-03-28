package Historial;
import Caja.CajaGUI;
import Conexion.Conexion;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;

public class HistorialPedidosGUI {
    private JButton volver;
    private JTable tablahistorial;
    private JPanel Panel;
    private JLabel subtitulo;
    private JFrame frame;
    private JFrame parentFrame;
    Conexion conR = new Conexion();

    public HistorialPedidosGUI(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        //llamar los pedidos en la tabla
        Historialordenes();
        subtitulo.setOpaque(true);
        subtitulo.setBackground(new Color(25, 25, 112));
        subtitulo.setForeground(Color.WHITE);

        // Cambiar color y fuente de los labels
        for (Component c : Panel.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        aplicarEstilos();

        // ** Configurar estilo de la tabla **
        tablahistorial.setBackground(Color.WHITE); // Fondo de las celdas blanco
        tablahistorial.setForeground(Color.BLACK); // Texto negro
        tablahistorial.setGridColor(Color.GRAY); // Bordes de la tabla

        // ** Encabezado de la tabla personalizado **
        JTableHeader header = tablahistorial.getTableHeader();
        header.setBackground(new Color(0, 51, 102)); // Azul oscuro
        header.setForeground(Color.WHITE); // Letras blancas
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // ** Centrar texto en celdas de la tabla **
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablahistorial.getColumnCount(); i++) {
            tablahistorial.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // ** Estilo del botón "Volver" **
        volver.setBackground(new Color(0, 51, 102)); // Azul oscuro
        volver.setForeground(Color.WHITE); // Texto blanco
        volver.setFocusPainted(false);
        volver.setBorderPainted(false);




        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parentFrame != null) {
                    parentFrame.setVisible(true);
                }
                frame.dispose();

            }
        });


        tablahistorial.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {//e = objeto del tablemodelevent

                if (e.getType() == TableModelEvent.UPDATE) {//verifica si es un tipo actualización en este caso si la el pedido pasó a enviado, getTye obtiene que tipo de evento
                    int fila = e.getFirstRow(); //fila, almacena el num de fila. e= obtener la fila modificada
                    int columna = e.getColumn();//guarda el num de la columna. obtiene la colum actualizada

                    if (columna == 3) { // Columna de estado
                        DefaultTableModel model = (DefaultTableModel) tablahistorial.getModel();
                        String nuevoEstado = model.getValueAt(fila, columna).toString();
                        int idPedido = Integer.parseInt(model.getValueAt(fila, 0).toString());
                        //System.out.println(idPedido);
                        //System.out.println(nuevoEstado);
                        actualizarEstado(idPedido, nuevoEstado);
                        actualizarStock(idPedido);
                        JOptionPane.showMessageDialog(null, "El stock se ha actualizado!");


                    }

                }
            }
        });
    }

    public HistorialPedidosGUI() {

    }


    public void Historialordenes() {
        DefaultTableModel orden = new DefaultTableModel();
        orden.addColumn("id_pedido");
        orden.addColumn("Cliente");
        orden.addColumn("fecha_hora");
        orden.addColumn("estado");
        orden.addColumn("metodo_pago");
        orden.addColumn("total");
        tablahistorial.setModel(orden);
        tablahistorial.getColumnModel().getColumn(3).setCellEditor(new EstadoCellEditor());

        Connection con = conR.getConnection();

        String[] Arreglo = new String[6];

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT p.id_pedido, c.nombre, p.fecha_hora, p.estado, p.metodo_pago, p.total " +
                    "FROM pedidos p " +
                    "JOIN cliente c ON p.id_cliente = c.id_cliente " +
                    "WHERE p.estado = 'Entregado'"; //consulta join para mostrar el nombre del cliente
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Arreglo[0] = rs.getString(1); // id_pedido
                Arreglo[1] = rs.getString(2); // nombre del cliente
                Arreglo[2] = rs.getString(3); // fecha_hora
                Arreglo[3] = rs.getString(4); // estado
                Arreglo[4] = rs.getString(5); // metodo_pago
                Arreglo[5] = rs.getString(6); // total

                orden.addRow(Arreglo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void aplicarEstilos() {
        Panel.setBackground(Color.DARK_GRAY);
        volver.setForeground(Color.WHITE);

        JTableHeader header = tablahistorial.getTableHeader();
        header.setBackground(new Color(51, 153, 255));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablahistorial.getColumnCount(); i++) {
            tablahistorial.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    class EstadoCellEditor extends DefaultCellEditor {
        public EstadoCellEditor() {
            super(new JComboBox<>(new String[]{"Entregado", "Enviado"}));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JComboBox<String> comboBox = (JComboBox<String>) getComponent();
            comboBox.setSelectedItem(value);
            return comboBox;
        }
    }

    public void actualizarEstado(int idPedido, String nuevoEstado) {
        Connection con = conR.getConnection();

        String sql = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                //System.out.println("Estado actualizado correctamente en la base de datos.");
                JOptionPane.showMessageDialog(null, "Estado actualizado correctamente.");
                Historialordenes(); // Recargar datos de la tabla
            } else {
                //System.out.println("No se pudo actualizar el estado en la base de datos.");
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarStock(int idPedido) {
        Connection con = conR.getConnection();

        try {

            //Obtener los productos del pedido desde detalle_pedidos
            String sqlDetalle = "SELECT id_producto, cantidad FROM detalle_pedido WHERE id_pedido = ?";
            PreparedStatement pstmtDetalle = con.prepareStatement(sqlDetalle);
            pstmtDetalle.setInt(1, idPedido);
            ResultSet rsDetalle = pstmtDetalle.executeQuery();

            //Actualizar el stock de cada producto (detalle_pedido - tabla carrito)
            while (rsDetalle.next()) {
                int idProducto = rsDetalle.getInt("id_producto");
                int cantidadPedido = rsDetalle.getInt("cantidad");

                //Obtener el stock actual del producto
                String sqlStock = "SELECT stock FROM producto WHERE id_producto = ?";
                PreparedStatement pstmtStock = con.prepareStatement(sqlStock);
                pstmtStock.setInt(1, idProducto);
                ResultSet rsStock = pstmtStock.executeQuery();

                if (rsStock.next()) {
                    int stockActual = rsStock.getInt("stock"); //stock Actual del producto
                    int nuevoStock = stockActual - cantidadPedido; //resta del stok con la cantidad

                    //Actualizar el stock en la tabla producto
                    String sqlUpdateStock = "UPDATE producto SET stock = ? WHERE id_producto = ?";
                    PreparedStatement pstmtUpdateStock = con.prepareStatement(sqlUpdateStock);
                    pstmtUpdateStock.setInt(1, nuevoStock);
                    pstmtUpdateStock.setInt(2, idProducto);
                    pstmtUpdateStock.executeUpdate();
                }
            }

            // 4. Confirmar la transacción (si es necesario)
            // con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            // con.rollback(); // En caso de error, deshacer la transacción
        }
    }


    public void runHistorial() {

        frame = new JFrame("Historial Pedidos");
        frame.setContentPane(new HistorialPedidosGUI(frame).Panel);

        // ** Cargar imagen de fondo **
        FondoPanel fondoPanel = new FondoPanel();
        Panel.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(Panel, BorderLayout.CENTER);

        // ** Cargar icono desde resources/imagenes/ **
        URL iconoURL = getClass().getClassLoader().getResource("imagenes/img_6.png");
        if (iconoURL != null) {
            ImageIcon icono = new ImageIcon(iconoURL);
            frame.setIconImage(icono.getImage());
        } else {
            System.out.println("⚠ ERROR: No se encontró la imagen icono_medicina.png");
        }

        frame.setContentPane(fondoPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600,650);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // ** Clase interna para dibujar el fondo con imagen y degradado **
    class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/img_10.png");
            if (imagenURL != null) {
                this.imagenFondo = new ImageIcon(imagenURL).getImage();
            } else {
                System.out.println("⚠ ERROR: No se encontró la imagen fondo_drogueria.jpg");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // ** Dibujar imagen de fondo **
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            } else {
                // ** Si no hay imagen, dibujar degradado azul **
                GradientPaint gp = new GradientPaint(0, 0, new Color(51, 153, 255), getWidth(), getHeight(), new Color(0, 51, 102));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }




}

