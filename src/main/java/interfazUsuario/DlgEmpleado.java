package interfazUsuario;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import objetosNegocio.*;
import control.Control;
import control.UtileriasGUI;
import excepciones.FachadaException;
import objetosServicio.Fecha;

public class DlgEmpleado extends JDialog {

    private Empleado empleado;
    private String operacion;
    private Control control;
    private StringBuffer respuesta;

    // Componentes
    private JTextField txtCodigoUsuario;   // Campo unificado (no editable)
    private JTextField txtNombres, txtApellidos, txtCedula,
            txtDireccion, txtCorreo, txtCelular,
            txtDiaNac, txtMesNac, txtAnioNac,
            txtDiaIng, txtMesIng, txtAnioIng;
    private JPasswordField pwdContrasena;
    private JCheckBox chkMostrarContrasena, chkAdmin;
    private JComboBox<Cargo> cmbCargo;
    private JComboBox<Horario> cmbHorario;
    private JComboBox<String> cmbGenero;
    private JComboBox<String> cmbEstadoCivil;

    public DlgEmpleado(JFrame frame, Control control, Empleado emp,
                       String operacion, StringBuffer respuesta) {
        super(frame, true);
        this.control = control;
        this.empleado = emp != null ? emp : new Empleado();
        this.operacion = operacion;
        this.respuesta = respuesta;

        initComponents();
        cargarDatos();
        // Reemplazar setSize fijo por tamaño de pantalla
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(screenSize.width, screenSize.height);
    setLocationRelativeTo(frame);

    setVisible(true);
    }

    private void initComponents() {
        setTitle(switch (operacion) {
            case UtileriasGUI.AGREGAR -> "Nuevo Empleado";
            case UtileriasGUI.ACTUALIZAR -> "Actualizar Empleado";
            default -> "Datos del Empleado";
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // ========== CAMPO UNIFICADO: Código / Usuario (no editable) ==========
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Código / Usuario:"), gbc);
        txtCodigoUsuario = new JTextField(20);
        txtCodigoUsuario.setEditable(false);
        txtCodigoUsuario.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1;
        panel.add(txtCodigoUsuario, gbc);
        row++;

        // Nombres
        addLabelAndField(panel, gbc, "Nombres:", txtNombres = new JTextField(20), row++);
        // Apellidos
        addLabelAndField(panel, gbc, "Apellidos:", txtApellidos = new JTextField(20), row++);
        // Cédula
        addLabelAndField(panel, gbc, "Cédula:", txtCedula = new JTextField(15), row++);

        // Género (ComboBox)
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Género:"), gbc);
        cmbGenero = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        gbc.gridx = 1;
        panel.add(cmbGenero, gbc);
        row++;

        // Estado Civil (ComboBox)
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Estado Civil:"), gbc);
        cmbEstadoCivil = new JComboBox<>(new String[]{"Soltero", "Casado", "Divorciado", "Unión Libre"});
        gbc.gridx = 1;
        panel.add(cmbEstadoCivil, gbc);
        row++;

        // Dirección
        addLabelAndField(panel, gbc, "Dirección:", txtDireccion = new JTextField(25), row++);
        // Correo
        addLabelAndField(panel, gbc, "Correo:", txtCorreo = new JTextField(25), row++);
        // Celular
        addLabelAndField(panel, gbc, "Celular:", txtCelular = new JTextField(15), row++);

        // CARGO (ComboBox)
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Cargo:"), gbc);
        cmbCargo = new JComboBox<>();
        try {
            ArrayList<Cargo> cargos = control.consultaCargos();
            for (Cargo c : cargos) cmbCargo.addItem(c);
        } catch (FachadaException e) { /* manejar */ }
        gbc.gridx = 1;
        panel.add(cmbCargo, gbc);
        row++;

        // HORARIO (ComboBox)
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Horario:"), gbc);
        cmbHorario = new JComboBox<>();
        try {
            ArrayList<Horario> horarios = control.consultaHorarios();
            for (Horario h : horarios) cmbHorario.addItem(h);
        } catch (FachadaException e) { /* manejar */ }
        gbc.gridx = 1;
        panel.add(cmbHorario, gbc);
        row++;

        // CONTRASEÑA
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Contraseña:"), gbc);
        JPanel pwdPanel = new JPanel(new BorderLayout(5, 0));
        pwdContrasena = new JPasswordField(15);
        chkMostrarContrasena = new JCheckBox("Mostrar");
        chkMostrarContrasena.addActionListener(e -> {
            if (chkMostrarContrasena.isSelected())
                pwdContrasena.setEchoChar((char) 0);
            else
                pwdContrasena.setEchoChar('*');
        });
        pwdPanel.add(pwdContrasena, BorderLayout.CENTER);
        pwdPanel.add(chkMostrarContrasena, BorderLayout.EAST);
        gbc.gridx = 1;
        panel.add(pwdPanel, gbc);
        row++;

        // CHECK ADMIN
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Administrador:"), gbc);
        chkAdmin = new JCheckBox();
        gbc.gridx = 1;
        panel.add(chkAdmin, gbc);
        row++;

        // FECHAS
        addFechaPanel(panel, gbc, "Fecha Nacimiento (dd/mm/aaaa):",
                txtDiaNac = new JTextField(3),
                txtMesNac = new JTextField(3),
                txtAnioNac = new JTextField(5), row++);
        addFechaPanel(panel, gbc, "Fecha Ingreso (dd/mm/aaaa):",
                txtDiaIng = new JTextField(3),
                txtMesIng = new JTextField(3),
                txtAnioIng = new JTextField(5), row++);

        // BOTONES
        JButton btnAceptar = new JButton(operacion.equals(UtileriasGUI.AGREGAR) ? "Guardar" : "Actualizar");
        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.addActionListener(this::aceptar);
        btnCancelar.addActionListener(e -> { respuesta.append(UtileriasGUI.CANCELAR); dispose(); });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botones.add(btnAceptar);
        botones.add(btnCancelar);

        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(panel), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        if (UtileriasGUI.DESPLEGAR.equals(operacion)) {
            setEditableAll(false);
            btnAceptar.setEnabled(false);
        }

        // ========== LISTENERS PARA GENERAR CÓDIGO/USUARIO ==========
        DocumentListener dl = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { generarCodigoUsuario(); }
            @Override public void removeUpdate(DocumentEvent e) { generarCodigoUsuario(); }
            @Override public void changedUpdate(DocumentEvent e) { generarCodigoUsuario(); }
        };
        txtNombres.getDocument().addDocumentListener(dl);
        txtApellidos.getDocument().addDocumentListener(dl);
        txtCedula.getDocument().addDocumentListener(dl);
        cmbCargo.addActionListener(e -> generarCodigoUsuario());
    }

    // ========== MÉTODOS AUXILIARES ==========
    private void addLabelAndField(JPanel panel, GridBagConstraints gbc,
                                  String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void addFechaPanel(JPanel panel, GridBagConstraints gbc,
                               String label, JTextField dia, JTextField mes, JTextField anio, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        p.add(dia);
        p.add(new JLabel("/"));
        p.add(mes);
        p.add(new JLabel("/"));
        p.add(anio);
        gbc.gridx = 1;
        panel.add(p, gbc);
    }

    private void setEditableAll(boolean editable) {
        // El campo código/usuario siempre no editable
        txtCodigoUsuario.setEditable(false);
        txtNombres.setEditable(editable);
        txtApellidos.setEditable(editable);
        txtCedula.setEditable(editable);
        txtDireccion.setEditable(editable);
        txtCorreo.setEditable(editable);
        txtCelular.setEditable(editable);
        pwdContrasena.setEditable(editable);
        chkAdmin.setEnabled(editable);
        cmbCargo.setEnabled(editable);
        cmbHorario.setEnabled(editable);
        cmbGenero.setEnabled(editable);
        cmbEstadoCivil.setEnabled(editable);
    }

    private void cargarDatos() {
        // Mostrar el código/usuario existente o generarlo
        if (empleado.getCodigoEmpleado() != null && !empleado.getCodigoEmpleado().isEmpty()) {
            txtCodigoUsuario.setText(empleado.getCodigoEmpleado());
        } else {
            generarCodigoUsuario();
        }

        txtNombres.setText(empleado.getNombres());
        txtApellidos.setText(empleado.getApellidos());
        txtCedula.setText(empleado.getCedula());
        txtDireccion.setText(empleado.getDireccion());
        txtCorreo.setText(empleado.getCorreo());
        txtCelular.setText(empleado.getCelular());

        if (empleado.getGenero() != null) {
            cmbGenero.setSelectedItem(empleado.getGenero());
        }
        if (empleado.getEstadoCivil() != null) {
            cmbEstadoCivil.setSelectedItem(empleado.getEstadoCivil());
        }

        if (empleado.getCargo() != null) {
            for (int i = 0; i < cmbCargo.getItemCount(); i++) {
                if (cmbCargo.getItemAt(i).getCodigoCargo().equals(empleado.getCargo().getCodigoCargo())) {
                    cmbCargo.setSelectedIndex(i);
                    break;
                }
            }
        }

        if (empleado.getHorario() != null) {
            for (int i = 0; i < cmbHorario.getItemCount(); i++) {
                if (cmbHorario.getItemAt(i).getCodigo().equals(empleado.getHorario())) {
                    cmbHorario.setSelectedIndex(i);
                    break;
                }
            }
        }

        if (empleado.getFechaNacimiento() != null) {
            Fecha f = empleado.getFechaNacimiento();
            txtDiaNac.setText(String.valueOf(f.getDia()));
            txtMesNac.setText(String.valueOf(f.getMes()));
            txtAnioNac.setText(String.valueOf(f.getAnio()));
        }
        if (empleado.getFechaIngreso() != null) {
            Fecha f = empleado.getFechaIngreso();
            txtDiaIng.setText(String.valueOf(f.getDia()));
            txtMesIng.setText(String.valueOf(f.getMes()));
            txtAnioIng.setText(String.valueOf(f.getAnio()));
        }
    }

    // ========== GENERAR CÓDIGO / USUARIO ==========
    private void generarCodigoUsuario() {
        String nombre = txtNombres.getText().trim().toUpperCase();
        String cedula = txtCedula.getText().trim();
        Cargo cargo = (Cargo) cmbCargo.getSelectedItem();

        // Solo generar si hay nombre, cédula y cargo
        if (nombre.length() >= 2 && cedula.length() >= 4 && cargo != null) {
            String primerasLetras = nombre.substring(0, 2);
            String ultimosDigitos = cedula.length() >= 4 ? cedula.substring(cedula.length() - 4) : cedula;
            String codigoCargo = cargo.getNombreCargo().length() >= 2 ?
                    cargo.getNombreCargo().substring(0, 2).toUpperCase() : "XX";
            txtCodigoUsuario.setText(primerasLetras + ultimosDigitos + codigoCargo);
        } else {
            txtCodigoUsuario.setText("");
        }
    }

    // ========== GUARDAR ==========
    private void aceptar(ActionEvent e) {
        try {
            // El código/usuario es el generado
            String codigoUsuario = txtCodigoUsuario.getText().trim();
            if (codigoUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Genere un código/usuario válido (nombre, cédula y cargo son necesarios)",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            empleado.setCodigoEmpleado(codigoUsuario);
            empleado.setNombres(txtNombres.getText().trim());
            empleado.setApellidos(txtApellidos.getText().trim());
            empleado.setCedula(txtCedula.getText().trim());
            empleado.setGenero((String) cmbGenero.getSelectedItem());
            empleado.setEstadoCivil((String) cmbEstadoCivil.getSelectedItem());
            empleado.setDireccion(txtDireccion.getText().trim());
            empleado.setCorreo(txtCorreo.getText().trim());
            empleado.setCelular(txtCelular.getText().trim());

            Cargo cargoSeleccionado = (Cargo) cmbCargo.getSelectedItem();
            empleado.setCargo(cargoSeleccionado);

            Horario horarioSeleccionado = (Horario) cmbHorario.getSelectedItem();
            if (horarioSeleccionado != null) {
                empleado.setHorario(horarioSeleccionado.getCodigo());
            }

            if (!txtDiaNac.getText().trim().isEmpty()) {
                empleado.setFechaNacimiento(new Fecha(
                        Integer.parseInt(txtDiaNac.getText().trim()),
                        Integer.parseInt(txtMesNac.getText().trim()),
                        Integer.parseInt(txtAnioNac.getText().trim())
                ));
            }
            if (!txtDiaIng.getText().trim().isEmpty()) {
                empleado.setFechaIngreso(new Fecha(
                        Integer.parseInt(txtDiaIng.getText().trim()),
                        Integer.parseInt(txtMesIng.getText().trim()),
                        Integer.parseInt(txtAnioIng.getText().trim())
                ));
            }

            // El usuario es el mismo código generado
            String nombreUsuario = codigoUsuario;
            String contrasena = new String(pwdContrasena.getPassword()).trim();
            if (contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La contraseña es obligatoria",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String cargoUsuario = chkAdmin.isSelected() ? "admin" : "empleado";
            Usuario usuario = new Usuario(cargoUsuario, nombreUsuario, contrasena);
            empleado.setUsuario(nombreUsuario);

            if (UtileriasGUI.AGREGAR.equals(operacion)) {
                control.getFachada().agrega(empleado);
                control.getFachada().agrega(usuario);
                JOptionPane.showMessageDialog(this, "Empleado y usuario creados correctamente");
            } else {
                control.getFachada().actualiza(empleado);
                Usuario existente = control.getFachada().obten(usuario);
                if (existente != null) {
                    existente.setCargo(cargoUsuario);
                    existente.setContrasena(contrasena);
                    control.getFachada().actualiza(existente);
                } else {
                    control.getFachada().agrega(usuario);
                }
                JOptionPane.showMessageDialog(this, "Empleado y usuario actualizados correctamente");
            }

            respuesta.append(UtileriasGUI.AGREGAR);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        String codigoUsuario = txtCodigoUsuario.getText().trim();
        if (codigoUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El código/usuario no puede estar vacío. Verifique nombre, cédula y cargo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}