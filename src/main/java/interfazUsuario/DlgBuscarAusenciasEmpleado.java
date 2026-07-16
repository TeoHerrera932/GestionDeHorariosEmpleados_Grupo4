package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Ausencia;
import objetosNegocio.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DlgBuscarAusenciasEmpleado extends JDialog {

    private Control control;
    private JComboBox<Empleado> cmbEmpleados;
    private JTable tablaAusencias;
    private DefaultTableModel modeloTabla;
    private JButton btnBuscar, btnCerrar;

    public DlgBuscarAusenciasEmpleado(JFrame parent, Control control) {
        super(parent, "Ausencias por Empleado", true);
        this.control = control;
        initUI();
        setSize(700, 400);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior con el ComboBox
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Seleccione Empleado:"));

        cmbEmpleados = new JComboBox<>();
        cargarEmpleados();
        panelSuperior.add(cmbEmpleados);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this::buscar);
        panelSuperior.add(btnBuscar);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"Fecha", "Motivo", "Estado"}, 0);
        tablaAusencias = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaAusencias);
        add(scroll, BorderLayout.CENTER);

        // Botón cerrar
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnCerrar);
        add(panelInferior, BorderLayout.SOUTH);
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

    private void buscar(ActionEvent e) {
        Empleado seleccionado = (Empleado) cmbEmpleados.getSelectedItem();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
            return;
        }

        String codigo = seleccionado.getCodigoEmpleado();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<Ausencia> ausencias = control.consultaAusenciasPorEmpleado(codigo);
            for (Ausencia a : ausencias) {
                modeloTabla.addRow(new Object[]{
                        a.getFecha().toString(),
                        a.getMotivo(),
                        a.getEstado()
                });
            }
            if (ausencias.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron ausencias para este empleado");
            }
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}