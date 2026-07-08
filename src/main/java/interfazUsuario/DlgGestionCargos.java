package interfazUsuario;

import control.Control;
import javax.swing.*;
import objetosNegocio.Cargo;
import excepciones.FachadaException;

public class DlgGestionCargos extends JDialog {

    private Control control;

    public DlgGestionCargos(JFrame parent, Control control) {
        super(parent, true);
        this.control = control;
        initUI();
    }

    private void initUI() {
        setTitle("Gestión de Cargos");
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Aquí puedes agregar un formulario o tabla para listar y agregar cargos
        // Por ahora un ejemplo simple:
        String codigo = JOptionPane.showInputDialog(this, "Código del Cargo (2 letras):");
        if (codigo == null) return;

        String nombre = JOptionPane.showInputDialog(this, "Nombre del Cargo:");
        String centro = JOptionPane.showInputDialog(this, "Centro de Trabajo:");
        String horario = JOptionPane.showInputDialog(this, "Tipo de Horario:");

        if (nombre != null) {
            Cargo cargo = new Cargo(codigo.toUpperCase(), nombre, centro, horario);
            try {
                control.getFachada().agregaCargo(cargo);
                JOptionPane.showMessageDialog(this, "Cargo registrado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }
}