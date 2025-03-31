package Cliente;

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
import java.util.ArrayList;

import Conexion.Conexion;
import Producto.Producto;

public class ClienteGUI {
    private JPanel main;
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6;
    private JTable table1;
    private JButton registrarButton, actualizarButton, eliminarButton, BackButton;
    private JButton button1;
    private JTextField campoBusqueda;
    private JFrame frame, parentFrame;
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Conexion connectionFA = new Conexion();

    public ClienteGUI(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        textField1.setEditable(false);
        textField1.setVisible(false);
        showdata();

        // Cambiar color y fuente de los labels
        for (Component c : main.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        aplicarEstilos();

        registrarButton.addActionListener(e -> {
            String correo = textField4.getText();
            String cedula = textField2.getText();
            if(clienteDAO.existeCliente(cedula)){
                JOptionPane.showMessageDialog(null, "la cédula ya se encuentra registrada");
                return;
            } else if (clienteDAO.existeCorreo(correo)) {
                JOptionPane.showMessageDialog(null, "el correo ya se encuentra registrado");
                return;
            }

            if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");
            } else {
                Cliente cliente = new Cliente(0, textField2.getText(), textField3.getText(), textField5.getText(), textField4.getText(), textField6.getText());
                clienteDAO.agregar(cliente);
                clear();
                showdata();
            }
        });

        actualizarButton.addActionListener(e -> {
            if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");
            } else {
                int id_cliente = Integer.parseInt(textField1.getText());
                Cliente cliente = new Cliente(id_cliente, textField2.getText(), textField3.getText(), textField5.getText(), textField4.getText(), textField6.getText());
                clienteDAO.actualizar(cliente);
                clear();
                showdata();
            }
        });

        eliminarButton.addActionListener(e -> {
            if (!textField1.getText().trim().isEmpty()) {
                clienteDAO.eliminar(Integer.parseInt(textField1.getText()));
                clear();
                showdata();
            }
        });

        BackButton.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
            frame.dispose();
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String terminoBusqueda = campoBusqueda.getText();
                try {
                    java.util.List<Cliente> clientes = buscarClientes(terminoBusqueda);
                    // Actualizar la tabla con los resultados
                    actualizarTablaClientes(clientes);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al buscar productos");
                }
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow >= 0) {
                    textField1.setText(String.valueOf(table1.getValueAt(selectedRow, 0)));
                    textField2.setText(String.valueOf(table1.getValueAt(selectedRow, 1)));
                    textField3.setText(String.valueOf( table1.getValueAt(selectedRow, 2)));
                    textField4.setText(String.valueOf(table1.getValueAt(selectedRow, 4)));
                    textField5.setText(String.valueOf(table1.getValueAt(selectedRow, 3)));
                    textField6.setText(String.valueOf( table1.getValueAt(selectedRow, 5)));
                }
            }
        });
    }

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


    public void showdata() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id_Cliente");
        modelo.addColumn("Cedula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Numero");
        modelo.addColumn("Correo");
        modelo.addColumn("Direccion");
        table1.setModel(modelo);

        Connection con = connectionFA.getConnection();
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM cliente")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
    }

    public void runCliente() {
        frame = new JFrame("Gestión de Clientes");
        FondoPanel fondoPanel = new FondoPanel();
        main.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(main, BorderLayout.CENTER);

        URL iconoURL = getClass().getClassLoader().getResource("imagenes/img_9.png");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }

        frame.setContentPane(fondoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 650);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/img_8.png");
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

    public java.util.List<Cliente> buscarClientes(String terminoBusqueda) throws SQLException {

        PreparedStatement consulta = null;
        ResultSet resultado = null;
        java.util.List<Cliente> clientes = new ArrayList<>();

        try {
            Connection con = connectionFA.getConnection();
            String sql = "SELECT * FROM cliente WHERE nombre LIKE ?";
            consulta = con.prepareStatement(sql);
            consulta.setString(1, "%" + terminoBusqueda + "%"); // Búsqueda parcial
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(resultado.getInt("id_cliente"));
                cliente.setCedula(resultado.getString("cedula"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setTelefono(resultado.getString("telefono"));
                cliente.setCorreo(resultado.getString("correo"));
                cliente.setDireccion(resultado.getString("direccion"));
                clientes.add(cliente);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    public void actualizarTablaClientes(java.util.List<Cliente> clientes) {
        DefaultTableModel modelo = new DefaultTableModel();

        // Definir las columnas
        modelo.addColumn("Id_Cliente");
        modelo.addColumn("Cedula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Numero");
        modelo.addColumn("Correo");
        modelo.addColumn("Direccion");

        for (Cliente cliente : clientes) {
            Object[] fila = {
                    cliente.getId_cliente(),
                    cliente.getCedula(),
                    cliente.getNombre(),
                    cliente.getTelefono(),
                    cliente.getCorreo(),
                    cliente.getDireccion(),
            };
            modelo.addRow(fila);
        }

        // Establecer el modelo en la tabla
        table1.setModel(modelo);
    }
}
