package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Empleado;
import objetosNegocio.Vacacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DlgBuscarVacacionesEmpleado extends JDialog {

    private Control control;
    private JComboBox<Empleado> cmbEmpleados;
    private JTable tablaVacaciones;
    private DefaultTableModel modeloTabla;
    private JButton btnBuscar, btnCerrar;

    public DlgBuscarVacacionesEmpleado(JFrame parent, Control control) {
        super(parent, "Vacaciones por Empleado", true);
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
        modeloTabla = new DefaultTableModel(new String[]{"Inicio", "Fin", "Reincorporación", "Estado"}, 0);
        tablaVacaciones = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaVacaciones);
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
            ArrayList<Vacacion> vacaciones = control.consultaVacacionesPorEmpleado(codigo);
            for (Vacacion v : vacaciones) {
                modeloTabla.addRow(new Object[]{
                        v.getFechaInicio().toString(),
                        v.getFechaFin().toString(),
                        v.getFechaReincorporacion() != null ? v.getFechaReincorporacion().toString() : "--",
                        v.getEstado()
                });
            }
            if (vacaciones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron vacaciones para este empleado");
            }
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
/*package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Vacacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DlgBuscarVacacionesEmpleado extends JDialog {

    private Control control;
    private JTextField txtCodigo;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public DlgBuscarVacacionesEmpleado(JFrame parent, Control control) {
        super(parent, "Vacaciones por Empleado", true);
        this.control = control;
        initUI();
        setSize(700, 400);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Código Empleado:"));
        txtCodigo = new JTextField(15);
        top.add(txtCodigo);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        top.add(btnBuscar);
        add(top, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"Inicio", "Fin", "Reincorporación", "Estado"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel bottom = new JPanel();
        bottom.add(btnCerrar);
        add(bottom, BorderLayout.SOUTH);
    }

    private void buscar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código");
            return;
        }
        modeloTabla.setRowCount(0);
        try {
            ArrayList<Vacacion> vacaciones = control.consultaVacacionesPorEmpleado(codigo);
            for (Vacacion v : vacaciones) {
                modeloTabla.addRow(new Object[]{
                        v.getFechaInicio().toString(),
                        v.getFechaFin().toString(),
                        v.getFechaReincorporacion() != null ? v.getFechaReincorporacion().toString() : "--",
                        v.getEstado()
                });
            }
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}*/