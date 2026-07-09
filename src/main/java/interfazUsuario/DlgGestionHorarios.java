package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Horario;

import javax.swing.*;
import java.awt.*;

public class DlgGestionHorarios extends JDialog {

    private Control control;
    private JTextField txtCodigo, txtNombre, txtHoraInicio, txtHoraFin;
    private JButton btnGuardar, btnCancelar;

    public DlgGestionHorarios(JFrame parent, Control control) {
        super(parent, "Gestión de Horarios", true);
        this.control = control;
        initUI();
        setSize(400, 250);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Código:"), gbc);
        txtCodigo = new JTextField(10);
        gbc.gridx = 1;
        add(txtCodigo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        add(txtNombre, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Hora Inicio (HH:mm):"), gbc);
        txtHoraInicio = new JTextField(8);
        gbc.gridx = 1;
        add(txtHoraInicio, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Hora Fin (HH:mm):"), gbc);
        txtHoraFin = new JTextField(8);
        gbc.gridx = 1;
        add(txtHoraFin, gbc);
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
        String nombre = txtNombre.getText().trim();
        String inicio = txtHoraInicio.getText().trim();
        String fin = txtHoraFin.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || inicio.isEmpty() || fin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }
        try {
            control.guardaHorario(new Horario(codigo, nombre, inicio, fin));
            JOptionPane.showMessageDialog(this, "Horario guardado correctamente");
            dispose();
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}