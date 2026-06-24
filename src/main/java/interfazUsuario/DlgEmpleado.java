package interfazUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import objetosNegocio.Empleado;
import objetosServicio.Fecha;
import control.UtileriasGUI;
import control.Control;
import excepciones.FachadaException;

public class DlgEmpleado extends JDialog {

    private Empleado empleado;
    private String operacion; // AGREGAR, ACTUALIZAR, DESPLEGAR
    private Control control;
    private StringBuffer respuesta;

    // Componentes del formulario
    private JTextField txtCodigo, txtNombres, txtApellidos, txtCedula, txtGenero,
            txtEstadoCivil, txtDireccion, txtCorreo, txtCargo,
            txtCentroTrabajo, txtUsuario, txtHorario, txtCelular;

    private JTextField txtDiaNac, txtMesNac, txtAnioNac;
    private JTextField txtDiaIng, txtMesIng, txtAnioIng;

    public DlgEmpleado(JFrame frame, Control control, Empleado emp,
                       String operacion, StringBuffer respuesta) {
        super(frame, true);
        this.control = control;
        this.empleado = emp != null ? emp : new Empleado();
        this.operacion = operacion;
        this.respuesta = respuesta;

        initComponents();
        cargarDatos();
        setLocationRelativeTo(frame);
        setSize(680, 620);
        setVisible(true);
    }

    public DlgEmpleado(JFrame frame, String el_empleado_ya_está_registrado, boolean b, Empleado bEmpleado, String DESPLEGAR, StringBuffer respuesta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void initComponents() {
        setTitle(switch (operacion) {
            case "AGREGAR" -> "Nuevo Empleado";
            case "ACTUALIZAR" -> "Actualizar Empleado";
            default -> "Datos del Empleado";
        });

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        panel.add(new JLabel("Código Empleado:"));
        panel.add(txtCodigo = new JTextField(15));

        panel.add(new JLabel("Nombres:"));
        panel.add(txtNombres = new JTextField(20));

        panel.add(new JLabel("Apellidos:"));
        panel.add(txtApellidos = new JTextField(20));

        panel.add(new JLabel("Cédula:"));
        panel.add(txtCedula = new JTextField(15));

        panel.add(new JLabel("Género:"));
        panel.add(txtGenero = new JTextField(10));

        panel.add(new JLabel("Estado Civil:"));
        panel.add(txtEstadoCivil = new JTextField(15));

        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion = new JTextField(25));

        panel.add(new JLabel("Correo Electrónico:"));
        panel.add(txtCorreo = new JTextField(25));

        panel.add(new JLabel("Cargo:"));
        panel.add(txtCargo = new JTextField(20));

        panel.add(new JLabel("Centro de Trabajo:"));
        panel.add(txtCentroTrabajo = new JTextField(20));

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario = new JTextField(15));

        panel.add(new JLabel("Horario:"));
        panel.add(txtHorario = new JTextField(20));

        panel.add(new JLabel("Celular:"));
        panel.add(txtCelular = new JTextField(15));

        // Fechas
        panel.add(new JLabel("Fecha Nacimiento (dd/mm/aaaa):"));
        panel.add(createFechaPanel(txtDiaNac, txtMesNac, txtAnioNac));

        panel.add(new JLabel("Fecha Ingreso (dd/mm/aaaa):"));
        panel.add(createFechaPanel(txtDiaIng, txtMesIng, txtAnioIng));

        JButton btnAceptar = new JButton(operacion.equals("AGREGAR") ? "Guardar" : "Actualizar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(this::aceptar);
        btnCancelar.addActionListener(this::cancelar);

        JPanel botones = new JPanel();
        botones.add(btnAceptar);
        botones.add(btnCancelar);

        setLayout(new BorderLayout(10, 10));
        add(panel, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        // Si solo se va a ver, deshabilitar edición
        if ("DESPLEGAR".equals(operacion)) {
            setEditableAll(false);
            btnAceptar.setEnabled(false);
        }
    }

    private JPanel createFechaPanel(JTextField dia, JTextField mes, JTextField anio) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.add(dia = new JTextField(3));
        p.add(new JLabel("/"));
        p.add(mes = new JTextField(3));
        p.add(new JLabel("/"));
        p.add(anio = new JTextField(5));
        return p;
    }

    private void setEditableAll(boolean editable) {
        txtCodigo.setEditable(editable);
        txtNombres.setEditable(editable);
        txtApellidos.setEditable(editable);
        txtCedula.setEditable(editable);
        txtGenero.setEditable(editable);
        txtEstadoCivil.setEditable(editable);
        txtDireccion.setEditable(editable);
        txtCorreo.setEditable(editable);
        txtCargo.setEditable(editable);
        txtCentroTrabajo.setEditable(editable);
        txtUsuario.setEditable(editable);
        txtHorario.setEditable(editable);
        txtCelular.setEditable(editable);
    }

    private void cargarDatos() {
        txtCodigo.setText(empleado.getCodigoEmpleado());
        txtNombres.setText(empleado.getNombres());
        txtApellidos.setText(empleado.getApellidos());
        txtCedula.setText(empleado.getCedula());
        txtGenero.setText(empleado.getGenero());
        txtEstadoCivil.setText(empleado.getEstadoCivil());
        txtDireccion.setText(empleado.getDireccion());
        txtCorreo.setText(empleado.getCorreo());
        txtCargo.setText(empleado.getCargo());
        txtCentroTrabajo.setText(empleado.getCentroDeTrabajo());
        txtUsuario.setText(empleado.getUsuario());
        txtHorario.setText(empleado.getHorario());
        txtCelular.setText(empleado.getCelular());
    }

    private void aceptar(ActionEvent e) {
        try {
            empleado.setCodigoEmpleado(txtCodigo.getText().trim());
            empleado.setNombres(txtNombres.getText().trim());
            empleado.setApellidos(txtApellidos.getText().trim());
            empleado.setCedula(txtCedula.getText().trim());
            empleado.setGenero(txtGenero.getText().trim());
            empleado.setEstadoCivil(txtEstadoCivil.getText().trim());
            empleado.setDireccion(txtDireccion.getText().trim());
            empleado.setCorreo(txtCorreo.getText().trim());
            empleado.setCargo(txtCargo.getText().trim());
            empleado.setCentroDeTrabajo(txtCentroTrabajo.getText().trim());
            empleado.setUsuario(txtUsuario.getText().trim());
            empleado.setHorario(txtHorario.getText().trim());
            empleado.setCelular(txtCelular.getText().trim());

            if ("AGREGAR".equals(operacion)) {
                control.getFachada().agrega(empleado);
                JOptionPane.showMessageDialog(this, "Empleado agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else if ("ACTUALIZAR".equals(operacion)) {
                control.getFachada().actualiza(empleado);
                JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            respuesta.append(UtileriasGUI.AGREGAR); // o ACTUALIZAR
            dispose();

        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar(ActionEvent e) {
        respuesta.append(UtileriasGUI.CANCELAR);
        dispose();
    }
}