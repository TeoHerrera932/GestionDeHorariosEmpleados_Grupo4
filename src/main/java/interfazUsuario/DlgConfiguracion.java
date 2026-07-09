package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Cargo;
import objetosNegocio.Centro;
import objetosNegocio.Horario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Diálogo único de configuración que agrupa la gestión de Cargos, Centros y Horarios.
 * Se accede desde el menú Configuración de VentanaPrincipalAdmin.
 */
public class DlgConfiguracion extends JDialog {

    private Control control;

    // Componentes para la pestaña Cargos
    private JTextField txtCargoCodigo, txtCargoNombre, txtCargoCentro, txtCargoHorario;
    private JTable tablaCargos;
    private DefaultTableModel modeloCargos;

    // Componentes para la pestaña Centros
    private JTextField txtCentroCodigo, txtCentroDireccion;
    private JTable tablaCentros;
    private DefaultTableModel modeloCentros;

    // Componentes para la pestaña Horarios
    private JTextField txtHorarioCodigo, txtHorarioNombre, txtHorarioInicio, txtHorarioFin;
    private JTable tablaHorarios;
    private DefaultTableModel modeloHorarios;

    public DlgConfiguracion(JFrame parent, Control control) {
        super(parent, "Configuración del Sistema", true);
        this.control = control;
        initUI();
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void initUI() {
        // Panel con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // ==================== PESTAÑA CARGOS ====================
        JPanel panelCargos = new JPanel(new BorderLayout(10, 10));
        panelCargos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulario de Cargos
        JPanel formCargos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formCargos.add(new JLabel("Código:"), gbc);
        txtCargoCodigo = new JTextField(6);
        gbc.gridx = 1;
        formCargos.add(txtCargoCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formCargos.add(new JLabel("Nombre:"), gbc);
        txtCargoNombre = new JTextField(20);
        gbc.gridx = 1;
        formCargos.add(txtCargoNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formCargos.add(new JLabel("Centro Trabajo:"), gbc);
        txtCargoCentro = new JTextField(15);
        gbc.gridx = 1;
        formCargos.add(txtCargoCentro, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formCargos.add(new JLabel("Tipo Horario:"), gbc);
        txtCargoHorario = new JTextField(10);
        gbc.gridx = 1;
        formCargos.add(txtCargoHorario, gbc);

        JButton btnAgregarCargo = new JButton("Agregar Cargo");
        btnAgregarCargo.addActionListener(this::agregarCargo);
        JButton btnEliminarCargo = new JButton("Eliminar Seleccionado");
        btnEliminarCargo.addActionListener(this::eliminarCargo);

        JPanel botonesCargos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botonesCargos.add(btnAgregarCargo);
        botonesCargos.add(btnEliminarCargo);

        // Tabla de Cargos
        modeloCargos = new DefaultTableModel(new String[]{"Código", "Nombre", "Centro", "Horario"}, 0);
        tablaCargos = new JTable(modeloCargos);
        JScrollPane scrollCargos = new JScrollPane(tablaCargos);

        // Organizar pestaña Cargos
        JPanel panelSuperiorCargos = new JPanel(new BorderLayout());
        panelSuperiorCargos.add(formCargos, BorderLayout.CENTER);
        panelSuperiorCargos.add(botonesCargos, BorderLayout.SOUTH);
        panelCargos.add(panelSuperiorCargos, BorderLayout.NORTH);
        panelCargos.add(scrollCargos, BorderLayout.CENTER);

        tabbedPane.addTab("Cargos", panelCargos);

        // ==================== PESTAÑA CENTROS ====================
        JPanel panelCentros = new JPanel(new BorderLayout(10, 10));
        panelCentros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formCentros = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formCentros.add(new JLabel("Código:"), gbc);
        txtCentroCodigo = new JTextField(10);
        gbc.gridx = 1;
        formCentros.add(txtCentroCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formCentros.add(new JLabel("Dirección:"), gbc);
        txtCentroDireccion = new JTextField(30);
        gbc.gridx = 1;
        formCentros.add(txtCentroDireccion, gbc);

        JButton btnAgregarCentro = new JButton("Agregar Centro");
        btnAgregarCentro.addActionListener(this::agregarCentro);
        JButton btnEliminarCentro = new JButton("Eliminar Seleccionado");
        btnEliminarCentro.addActionListener(this::eliminarCentro);

        JPanel botonesCentros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botonesCentros.add(btnAgregarCentro);
        botonesCentros.add(btnEliminarCentro);

        modeloCentros = new DefaultTableModel(new String[]{"Código", "Dirección"}, 0);
        tablaCentros = new JTable(modeloCentros);
        JScrollPane scrollCentros = new JScrollPane(tablaCentros);

        JPanel panelSuperiorCentros = new JPanel(new BorderLayout());
        panelSuperiorCentros.add(formCentros, BorderLayout.CENTER);
        panelSuperiorCentros.add(botonesCentros, BorderLayout.SOUTH);
        panelCentros.add(panelSuperiorCentros, BorderLayout.NORTH);
        panelCentros.add(scrollCentros, BorderLayout.CENTER);

        tabbedPane.addTab("Centros", panelCentros);

        // ==================== PESTAÑA HORARIOS ====================
        JPanel panelHorarios = new JPanel(new BorderLayout(10, 10));
        panelHorarios.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formHorarios = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formHorarios.add(new JLabel("Código:"), gbc);
        txtHorarioCodigo = new JTextField(6);
        gbc.gridx = 1;
        formHorarios.add(txtHorarioCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formHorarios.add(new JLabel("Nombre:"), gbc);
        txtHorarioNombre = new JTextField(15);
        gbc.gridx = 1;
        formHorarios.add(txtHorarioNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formHorarios.add(new JLabel("Hora Inicio (HH:mm):"), gbc);
        txtHorarioInicio = new JTextField(8);
        gbc.gridx = 1;
        formHorarios.add(txtHorarioInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formHorarios.add(new JLabel("Hora Fin (HH:mm):"), gbc);
        txtHorarioFin = new JTextField(8);
        gbc.gridx = 1;
        formHorarios.add(txtHorarioFin, gbc);

        JButton btnAgregarHorario = new JButton("Agregar Horario");
        btnAgregarHorario.addActionListener(this::agregarHorario);
        JButton btnEliminarHorario = new JButton("Eliminar Seleccionado");
        btnEliminarHorario.addActionListener(this::eliminarHorario);

        JPanel botonesHorarios = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botonesHorarios.add(btnAgregarHorario);
        botonesHorarios.add(btnEliminarHorario);

        modeloHorarios = new DefaultTableModel(new String[]{"Código", "Nombre", "Inicio", "Fin"}, 0);
        tablaHorarios = new JTable(modeloHorarios);
        JScrollPane scrollHorarios = new JScrollPane(tablaHorarios);

        JPanel panelSuperiorHorarios = new JPanel(new BorderLayout());
        panelSuperiorHorarios.add(formHorarios, BorderLayout.CENTER);
        panelSuperiorHorarios.add(botonesHorarios, BorderLayout.SOUTH);
        panelHorarios.add(panelSuperiorHorarios, BorderLayout.NORTH);
        panelHorarios.add(scrollHorarios, BorderLayout.CENTER);

        tabbedPane.addTab("Horarios", panelHorarios);

        // ==================== BOTÓN CERRAR ====================
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.add(btnCerrar);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarCargos();
        cargarCentros();
        cargarHorarios();
    }

    // ==================== MÉTODOS PARA CARGOS ====================
    private void cargarCargos() {
        modeloCargos.setRowCount(0);
        try {
            ArrayList<Cargo> cargos = control.consultaCargos();
            for (Cargo c : cargos) {
                modeloCargos.addRow(new Object[]{
                        c.getCodigoCargo(),
                        c.getNombreCargo(),
                        c.getCentroTrabajo(),
                        c.getTipoHorario()
                });
            }
        } catch (FachadaException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar cargos: " + e.getMessage());
        }
    }

    private void agregarCargo(ActionEvent e) {
        String codigo = txtCargoCodigo.getText().trim().toUpperCase();
        String nombre = txtCargoNombre.getText().trim();
        String centro = txtCargoCentro.getText().trim();
        String horario = txtCargoHorario.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || centro.isEmpty() || horario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos del cargo son obligatorios");
            return;
        }

        try {
            control.getFachada().agregaCargo(new Cargo(codigo, nombre, centro, horario));
            JOptionPane.showMessageDialog(this, "Cargo agregado correctamente");
            limpiarCamposCargo();
            cargarCargos();
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarCargo(ActionEvent e) {
        int fila = tablaCargos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cargo para eliminar");
            return;
        }
        String codigo = (String) modeloCargos.getValueAt(fila, 0);
        String nombre = (String) modeloCargos.getValueAt(fila, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar cargo " + codigo + " - " + nombre + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Cargo cargo = new Cargo(codigo, nombre,
                        (String) modeloCargos.getValueAt(fila, 2),
                        (String) modeloCargos.getValueAt(fila, 3));
                control.eliminaCargo(cargo);
                JOptionPane.showMessageDialog(this, "Cargo eliminado correctamente");
                cargarCargos();
            } catch (FachadaException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void limpiarCamposCargo() {
        txtCargoCodigo.setText("");
        txtCargoNombre.setText("");
        txtCargoCentro.setText("");
        txtCargoHorario.setText("");
    }

    // ==================== MÉTODOS PARA CENTROS ====================
    private void cargarCentros() {
        modeloCentros.setRowCount(0);
        try {
            ArrayList<Centro> centros = control.consultaCentros();
            for (Centro c : centros) {
                modeloCentros.addRow(new Object[]{c.getCodigo(), c.getDireccion()});
            }
        } catch (FachadaException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar centros: " + e.getMessage());
        }
    }

    private void agregarCentro(ActionEvent e) {
        String codigo = txtCentroCodigo.getText().trim().toUpperCase();
        String direccion = txtCentroDireccion.getText().trim();

        if (codigo.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Código y dirección son obligatorios");
            return;
        }

        try {
            control.guardaCentro(new Centro(codigo, direccion));
            JOptionPane.showMessageDialog(this, "Centro agregado correctamente");
            txtCentroCodigo.setText("");
            txtCentroDireccion.setText("");
            cargarCentros();
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarCentro(ActionEvent e) {
        int fila = tablaCentros.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un centro para eliminar");
            return;
        }
        String codigo = (String) modeloCentros.getValueAt(fila, 0);
        String direccion = (String) modeloCentros.getValueAt(fila, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar centro " + codigo + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                control.eliminaCentro(new Centro(codigo, direccion));
                JOptionPane.showMessageDialog(this, "Centro eliminado correctamente");
                cargarCentros();
            } catch (FachadaException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    // ==================== MÉTODOS PARA HORARIOS ====================
    private void cargarHorarios() {
        modeloHorarios.setRowCount(0);
        try {
            ArrayList<Horario> horarios = control.consultaHorarios();
            for (Horario h : horarios) {
                modeloHorarios.addRow(new Object[]{
                        h.getCodigo(),
                        h.getNombre(),
                        h.getHoraInicio(),
                        h.getHoraFin()
                });
            }
        } catch (FachadaException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar horarios: " + e.getMessage());
        }
    }

    private void agregarHorario(ActionEvent e) {
        String codigo = txtHorarioCodigo.getText().trim().toUpperCase();
        String nombre = txtHorarioNombre.getText().trim();
        String inicio = txtHorarioInicio.getText().trim();
        String fin = txtHorarioFin.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || inicio.isEmpty() || fin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos del horario son obligatorios");
            return;
        }

        try {
            control.guardaHorario(new Horario(codigo, nombre, inicio, fin));
            JOptionPane.showMessageDialog(this, "Horario agregado correctamente");
            limpiarCamposHorario();
            cargarHorarios();
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarHorario(ActionEvent e) {
        int fila = tablaHorarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario para eliminar");
            return;
        }
        String codigo = (String) modeloHorarios.getValueAt(fila, 0);
        String nombre = (String) modeloHorarios.getValueAt(fila, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar horario " + codigo + " - " + nombre + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Horario horario = new Horario(codigo, nombre,
                        (String) modeloHorarios.getValueAt(fila, 2),
                        (String) modeloHorarios.getValueAt(fila, 3));
                control.eliminaHorario(horario);
                JOptionPane.showMessageDialog(this, "Horario eliminado correctamente");
                cargarHorarios();
            } catch (FachadaException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void limpiarCamposHorario() {
        txtHorarioCodigo.setText("");
        txtHorarioNombre.setText("");
        txtHorarioInicio.setText("");
        txtHorarioFin.setText("");
    }
}