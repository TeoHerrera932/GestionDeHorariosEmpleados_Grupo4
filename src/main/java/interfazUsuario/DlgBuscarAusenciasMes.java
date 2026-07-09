package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Ausencia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DlgBuscarAusenciasMes extends JDialog {

    private Control control;
    private JTextField txtMes, txtAnio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public DlgBuscarAusenciasMes(JFrame parent, Control control) {
        super(parent, "Ausencias por Mes", true);
        this.control = control;
        initUI();
        setSize(700, 400);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Mes (1-12):"));
        txtMes = new JTextField(3);
        top.add(txtMes);
        top.add(new JLabel("Año:"));
        txtAnio = new JTextField(5);
        top.add(txtAnio);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        top.add(btnBuscar);
        add(top, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"Código Empleado", "Fecha", "Motivo", "Estado"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel bottom = new JPanel();
        bottom.add(btnCerrar);
        add(bottom, BorderLayout.SOUTH);
    }

    private void buscar() {
        try {
            int mes = Integer.parseInt(txtMes.getText().trim());
            int anio = Integer.parseInt(txtAnio.getText().trim());
            modeloTabla.setRowCount(0);
            ArrayList<Ausencia> ausencias = control.consultaAusenciasPorMes(mes, anio);
            for (Ausencia a : ausencias) {
                modeloTabla.addRow(new Object[]{
                        a.getCodigoEmpleado(),
                        a.getFecha().toString(),
                        a.getMotivo(),
                        a.getEstado()
                });
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos");
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}