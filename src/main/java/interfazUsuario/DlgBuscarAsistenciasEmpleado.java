package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Asistencia;
import objetosNegocio.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DlgBuscarAsistenciasEmpleado extends JDialog {

    private Control control;
    private JComboBox<Empleado> cmbEmpleados;  // ← ComboBox de empleados
    private JTable tablaAsistencias;
    private DefaultTableModel modeloTabla;
    private JButton btnBuscar, btnCerrar;

    public DlgBuscarAsistenciasEmpleado(JFrame parent, Control control) {
        super(parent, "Buscar Asistencias por Empleado", true);
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
        cargarEmpleados();  // ← Cargar todos los empleados
        panelSuperior.add(cmbEmpleados);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this::buscar);
        panelSuperior.add(btnBuscar);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"Fecha", "Hora Ingreso", "Hora Salida", "Estado"}, 0);
        tablaAsistencias = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaAsistencias);
        add(scroll, BorderLayout.CENTER);

        // Botón cerrar
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnCerrar);
        add(panelInferior, BorderLayout.SOUTH);
    }

    // ========== CARGAR EMPLEADOS EN EL COMBO ==========
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

    // ========== BUSCAR ASISTENCIAS ==========
    private void buscar(ActionEvent e) {
        Empleado seleccionado = (Empleado) cmbEmpleados.getSelectedItem();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
            return;
        }

        String codigo = seleccionado.getCodigoEmpleado();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<Asistencia> asistencias = control.consultaAsistenciasPorEmpleado(codigo);
            for (Asistencia a : asistencias) {
                modeloTabla.addRow(new Object[]{
                        a.getFecha().toString(),
                        a.getHoraIngreso(),
                        a.getHoraSalida() != null ? a.getHoraSalida() : "--",
                        a.getEstado()
                });
            }
            if (asistencias.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron asistencias para este empleado");
            }
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}