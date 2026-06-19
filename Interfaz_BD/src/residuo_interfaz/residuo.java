package residuo_interfaz;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class residuo extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtToxicidad;
    private JTextField chkEstado;
    private JTable tabla;

    public void guardarCargo(int codigo, int toxicidad) {

        String sql =
            "INSERT INTO residuo(ResCod, ResTox) " +
            "VALUES (?, ?)";

        try (
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, codigo);
            ps.setInt(2, toxicidad);
            

            ps.executeUpdate();

            System.out.println("Guardado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void listarCargos() {

        DefaultTableModel modelo =
            (DefaultTableModel) tabla.getModel();

        modelo.setRowCount(0);

        String sql = "SELECT * FROM residuo";

        try (
            Connection con = Conexion.conectar();
            PreparedStatement ps =
                con.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                modelo.addRow(new Object[]{
                        rs.getInt("ResCod"),
                        rs.getInt("ResTox"),
                        rs.getString("ResEstReg")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public residuo() {
        setTitle("Residuo");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Panel Registro
        JPanel panelRegistro = new JPanel(new GridBagLayout());
        panelRegistro.setBorder(new TitledBorder("Registro de Residuo"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelRegistro.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        txtCodigo = new JTextField(10);
        panelRegistro.add(txtCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelRegistro.add(new JLabel("Toxicidad:"), gbc);

        gbc.gridx = 1;
        txtToxicidad = new JTextField(25);
        panelRegistro.add(txtToxicidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelRegistro.add(new JLabel("Estado Registro:"), gbc);

        gbc.gridx = 1;
        chkEstado = new JTextField("A");
        panelRegistro.add(chkEstado, gbc);
        chkEstado.setEditable(false);

        // Tabla
        String[] columnas = {"Código", "Toxicidad", "Estado Registro"};
        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0);

        tabla = new JTable(modelo);
        
        listarCargos();
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(
                new TitledBorder("Tabla Cargo"));
        panelTabla.add(new JScrollPane(tabla));
        
        tabla.getSelectionModel().addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila >= 0) {

                txtCodigo.setText(
                    tabla.getValueAt(fila, 0).toString());

                txtToxicidad.setText(
                    tabla.getValueAt(fila, 1).toString());
            }
            txtCodigo.setEditable(false);
        });

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 10, 10));
        JButton adicionar=new JButton("Adicionar");
        JButton modificar=new JButton("Modificar");
        JButton eliminar=new JButton("Eliminar");
        JButton cancelar=new JButton("Cancelar");
        panelBotones.add(adicionar);
        panelBotones.add(modificar);
        panelBotones.add(eliminar);
        panelBotones.add(cancelar);
        
        panelBotones.add(new JButton("Inactivar"));
        panelBotones.add(new JButton("Reactivar"));
        panelBotones.add(new JButton("Actualizar"));
        panelBotones.add(new JButton("Salir"));

        panelPrincipal.add(panelRegistro, BorderLayout.NORTH);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
        
        //botones
        adicionar.addActionListener(e -> {

            int codigo =
                    Integer.parseInt(txtCodigo.getText());

            int toxicidad =
            		Integer.parseInt(txtToxicidad.getText());


            guardarCargo(codigo,toxicidad);
            listarCargos();
        });
        cancelar.addActionListener(e -> {
        	
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new residuo().setVisible(true);
        });
    }
}