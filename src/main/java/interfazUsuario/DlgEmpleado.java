package interfazUsuario;

import javax.swing.*;
import java.awt.*;
import objetosNegocio.Empleado;
import objetosServicio.Fecha;
import control.UtileriasGUI;

public class DlgEmpleado extends JDialog {

    private Empleado empleado;
    private String operacion;
    private StringBuffer respuesta;

    // Componentes
    private JTextField txtCodigo, txtNombres, txtApellidos, txtGenero, txtEstadoCivil,
            txtDireccion, txtCorreo, txtCargo, txtCentro, txtUsuario,
            txtCedula, txtHorario, txtCelular;
    private JTextField txtDiaNac, txtMesNac, txtAnioNac;
    private JTextField txtDiaIng, txtMesIng, txtAnioIng;

    public DlgEmpleado(JFrame frame, String title, boolean modal, Empleado emp,
                       String operacion, StringBuffer respuesta) {
        super(frame, title, modal);
        this.empleado = emp;
        this.operacion = operacion;
        this.respuesta = respuesta;

        initComponents();
        cargarDatos();
        setLocationRelativeTo(frame);
        setSize(600, 550);
        setVisible(true);
    }

    private void initComponents() {
        // Implementación básica del formulario (puedes mejorarla con GridBagLayout)
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo = new JTextField(15));

        panel.add(new JLabel("Nombres:"));
        panel.add(txtNombres = new JTextField(20));

        panel.add(new JLabel("Apellidos:"));
        panel.add(txtApellidos = new JTextField(20));

        panel.add(new JLabel("Fecha Nacimiento (dd/mm/aaaa):"));
        panel.add(createFechaPanel(txtDiaNac, txtMesNac, txtAnioNac));

        // ... (agrega los demás campos de forma similar)

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> aceptar());
        btnCancelar.addActionListener(e -> cancelar());

        JPanel botones = new JPanel();
        botones.add(btnAceptar);
        botones.add(btnCancelar);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private JPanel createFechaPanel(JTextField dia, JTextField mes, JTextField anio) {
        JPanel p = new JPanel();
        p.add(dia = new JTextField(2));
        p.add(new JLabel("/"));
        p.add(mes = new JTextField(2));
        p.add(new JLabel("/"));
        p.add(anio = new JTextField(4));
        return p;
    }

    private void cargarDatos() {
        if (empleado == null) return;
        txtCodigo.setText(empleado.getCodigoEmpleado());
        txtNombres.setText(empleado.getNombres());
        txtApellidos.setText(empleado.getApellidos());
        
    }

    private void aceptar() {
        // Llenar objeto empleado con datos de los JTextField
        // (implementación básica)
        respuesta.append(UtileriasGUI.AGREGAR); // o ACTUALIZAR según operacion
        dispose();
    }

    private void cancelar() {
        respuesta.append(UtileriasGUI.CANCELAR);
        dispose();
    }
}