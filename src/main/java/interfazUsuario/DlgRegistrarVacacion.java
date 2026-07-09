package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Vacacion;
import objetosServicio.Fecha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DlgRegistrarVacacion extends JDialog {

    private Control control;
    private JTextField txtCodigoEmpleado;
    private JTextField txtDiaIni, txtMesIni, txtAnioIni;
    private JTextField txtDiaFin, txtMesFin, txtAnioFin;
    private JTextField txtDiaRein, txtMesRein, txtAnioRein;
    private JButton btnGuardar, btnCancelar;

    public DlgRegistrarVacacion(JFrame parent, Control control) {
        super(parent, "Registrar Vacación", true);
        this.control = control;
        initUI();
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Código Empleado
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Código Empleado:"), gbc);
        txtCodigoEmpleado = new JTextField(15);
        gbc.gridx = 1;
        add(txtCodigoEmpleado, gbc);
        row++;

        // Fecha Inicio
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Fecha Inicio (dd/mm/aaaa):"), gbc);
        JPanel pIni = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        txtDiaIni = new JTextField(3);
        txtMesIni = new JTextField(3);
        txtAnioIni = new JTextField(5);
        pIni.add(txtDiaIni);
        pIni.add(new JLabel("/"));
        pIni.add(txtMesIni);
        pIni.add(new JLabel("/"));
        pIni.add(txtAnioIni);
        gbc.gridx = 1;
        add(pIni, gbc);
        row++;

        // Fecha Fin
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Fecha Fin (dd/mm/aaaa):"), gbc);
        JPanel pFin = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        txtDiaFin = new JTextField(3);
        txtMesFin = new JTextField(3);
        txtAnioFin = new JTextField(5);
        pFin.add(txtDiaFin);
        pFin.add(new JLabel("/"));
        pFin.add(txtMesFin);
        pFin.add(new JLabel("/"));
        pFin.add(txtAnioFin);
        gbc.gridx = 1;
        add(pFin, gbc);
        row++;

        // Fecha Retorno
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Fecha Retorno (dd/mm/aaaa):"), gbc);
        JPanel pRein = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        txtDiaRein = new JTextField(3);
        txtMesRein = new JTextField(3);
        txtAnioRein = new JTextField(5);
        pRein.add(txtDiaRein);
        pRein.add(new JLabel("/"));
        pRein.add(txtMesRein);
        pRein.add(new JLabel("/"));
        pRein.add(txtAnioRein);
        gbc.gridx = 1;
        add(pRein, gbc);
        row++;

        // Botones
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(this::guardar);
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);
    }

    private void guardar(ActionEvent e) {
        try {
            String codigo = txtCodigoEmpleado.getText().trim();
            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el código del empleado",
                        "Campo obligatorio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Fecha fechaInicio = leerFecha(txtDiaIni, txtMesIni, txtAnioIni);
            Fecha fechaFin = leerFecha(txtDiaFin, txtMesFin, txtAnioFin);
            Fecha fechaRetorno = leerFecha(txtDiaRein, txtMesRein, txtAnioRein);

            if (fechaInicio == null || fechaFin == null || fechaRetorno == null) {
                JOptionPane.showMessageDialog(this, "Todas las fechas son obligatorias",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vacacion vacacion = new Vacacion(codigo, fechaInicio, fechaFin);
            vacacion.setFechaReincorporacion(fechaRetorno);
            vacacion.setEstado("PENDIENTE");

            control.registraVacacion(vacacion);
            JOptionPane.showMessageDialog(this, "Vacación registrada correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos para las fechas",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Fecha leerFecha(JTextField dia, JTextField mes, JTextField anio) {
        String d = dia.getText().trim();
        String m = mes.getText().trim();
        String a = anio.getText().trim();

        if (d.isEmpty() || m.isEmpty() || a.isEmpty()) {
            return null;
        }

        try {
            int dd = Integer.parseInt(d);
            int mm = Integer.parseInt(m);
            int aa = Integer.parseInt(a);
            return new Fecha(dd, mm, aa);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}