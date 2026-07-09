package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Centro;

import javax.swing.*;
import java.awt.*;

public class DlgGestionCentros extends JDialog {

    private Control control;
    private JTextField txtCodigo, txtDireccion;
    private JButton btnGuardar, btnCancelar;

    public DlgGestionCentros(JFrame parent, Control control) {
        super(parent, "Gestión de Centros", true);
        this.control = control;
        initUI();
        setSize(400, 180);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Código (una palabra):"), gbc);
        txtCodigo = new JTextField(15);
        gbc.gridx = 1;
        add(txtCodigo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Dirección:"), gbc);
        txtDireccion = new JTextField(25);
        gbc.gridx = 1;
        add(txtDireccion, gbc);
        row++;

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);
    }

    private void guardar() {
        String codigo = txtCodigo.getText().trim().toUpperCase();
        String direccion = txtDireccion.getText().trim();

        if (codigo.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ambos campos son obligatorios");
            return;
        }
        try {
            control.guardaCentro(new Centro(codigo, direccion));
            JOptionPane.showMessageDialog(this, "Centro guardado correctamente");
            dispose();
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}