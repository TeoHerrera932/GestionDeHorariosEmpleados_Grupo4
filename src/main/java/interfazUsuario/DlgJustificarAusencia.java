package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Ausencia;
import objetosNegocio.Empleado;
import objetosServicio.Fecha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DlgJustificarAusencia extends JDialog {

    private Control control;
    private JComboBox<Empleado> cmbEmpleados;  // ← Cambio de txtCodigo a combo
    private JTextField txtDia, txtMes, txtAnio, txtMotivo;
    private JButton btnGuardar, btnCancelar;

    public DlgJustificarAusencia(JFrame parent, Control control) {
        super(parent, "Justificar Ausencia", true);
        this.control = control;
        initUI();
        setSize(450, 250);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Empleado (ComboBox)
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Empleado:"), gbc);
        cmbEmpleados = new JComboBox<>();
        cargarEmpleados();
        gbc.gridx = 1;
        add(cmbEmpleados, gbc);
        row++;

        // Fecha
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Fecha (dd/mm/aaaa):"), gbc);
        JPanel pFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        txtDia = new JTextField(3);
        txtMes = new JTextField(3);
        txtAnio = new JTextField(5);
        pFecha.add(txtDia);
        pFecha.add(new JLabel("/"));
        pFecha.add(txtMes);
        pFecha.add(new JLabel("/"));
        pFecha.add(txtAnio);
        gbc.gridx = 1;
        add(pFecha, gbc);
        row++;

        // Motivo
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Motivo:"), gbc);
        txtMotivo = new JTextField(25);
        gbc.gridx = 1;
        add(txtMotivo, gbc);
        row++;

        // Botones
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(this::guardar);
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);
    }

    private void cargarEmpleados() {
        try {
            ArrayList<Empleado> empleados = control.obtenerTodosLosEmpleados();
            for (Empleado e : empleados) {
                cmbEmpleados.addItem(e);
            }
            if (cmbEmpleados.getItemCount() > 0) {
                cmbEmpleados.setSelectedIndex(0);
            }
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar empleados: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardar(ActionEvent e) {
        Empleado seleccionado = (Empleado) cmbEmpleados.getSelectedItem();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
            return;
        }

        try {
            String codigo = seleccionado.getCodigoEmpleado();
            int dia = Integer.parseInt(txtDia.getText().trim());
            int mes = Integer.parseInt(txtMes.getText().trim());
            int anio = Integer.parseInt(txtAnio.getText().trim());
            String motivo = txtMotivo.getText().trim();

            if (motivo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un motivo");
                return;
            }

            Ausencia ausencia = new Ausencia(codigo, new Fecha(dia, mes, anio), motivo);
            ausencia.setEstado("JUSTIFICADA");
            control.registraAusencia(ausencia);
            JOptionPane.showMessageDialog(this, "Ausencia justificada correctamente");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Ingrese valores numéricos.");
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
/*package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Ausencia;
import objetosServicio.Fecha;

import javax.swing.*;
import java.awt.*;

public class DlgJustificarAusencia extends JDialog {

    private Control control;
    private JTextField txtCodigo, txtDia, txtMes, txtAnio, txtMotivo;
    private JButton btnGuardar, btnCancelar;

    public DlgJustificarAusencia(JFrame parent, Control control) {
        super(parent, "Justificar Ausencia", true);
        this.control = control;
        initUI();
        setSize(450, 250);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Código Empleado:"), gbc);
        txtCodigo = new JTextField(15);
        gbc.gridx = 1;
        add(txtCodigo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Fecha (dd/mm/aaaa):"), gbc);
        JPanel pFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtDia = new JTextField(3);
        txtMes = new JTextField(3);
        txtAnio = new JTextField(5);
        pFecha.add(txtDia); pFecha.add(new JLabel("/"));
        pFecha.add(txtMes); pFecha.add(new JLabel("/"));
        pFecha.add(txtAnio);
        gbc.gridx = 1;
        add(pFecha, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Motivo:"), gbc);
        txtMotivo = new JTextField(25);
        gbc.gridx = 1;
        add(txtMotivo, gbc);
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
        try {
            String codigo = txtCodigo.getText().trim();
            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese código de empleado");
                return;
            }
            int dia = Integer.parseInt(txtDia.getText().trim());
            int mes = Integer.parseInt(txtMes.getText().trim());
            int anio = Integer.parseInt(txtAnio.getText().trim());
            String motivo = txtMotivo.getText().trim();

            Ausencia ausencia = new Ausencia(codigo, new Fecha(dia, mes, anio), motivo);
            ausencia.setEstado("JUSTIFICADA");
            control.registraAusencia(ausencia);
            JOptionPane.showMessageDialog(this, "Ausencia justificada correctamente");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida");
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}*/