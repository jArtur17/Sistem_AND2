package Historial;
import Conexion.Conexion;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
/**

 * @author Nombre Arturo
 * @version 1.0
 */
public class HistorialPedidos {
    private JButton button1;
    private JTable tablahistorial;
    private JPanel Panel;
    private JLabel subtitulo;
    Conexion conR = new Conexion();

    public HistorialPedidos(JFrame frame) {
        //llamar los pedidos en la tabla
        Historialordenes();
        subtitulo.setOpaque(true);
        subtitulo.setBackground(new Color(25, 25, 112));
        subtitulo.setForeground(Color.WHITE);



        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

    /**
     * Returns the Historial Pedidos.
     *
     * @return the Historial
     */
    public HistorialPedidos() {

    }


    /**
     * /*
     * (non-Javadoc)
     *
     */
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

    /**
     * Estado de la base de datos.
     *
     * @param idPedido
     * @
     */
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

    /**
     * Estado de historial
     */
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


    /**
     * Runs the Historial Pedidos.
     */
    public void runHistorial() {

        JFrame frame = new JFrame("Historial Pedidos");
        frame.setContentPane(new HistorialPedidos(frame).Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,700);
        frame.setResizable(false);
        frame.setVisible(true);
    }




}

