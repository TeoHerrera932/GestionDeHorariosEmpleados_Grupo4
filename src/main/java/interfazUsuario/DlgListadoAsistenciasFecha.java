package interfazUsuario;

import control.Control;
import excepciones.FachadaException;
import objetosNegocio.Asistencia;
import objetosServicio.Fecha;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DlgListadoAsistenciasFecha extends JDialog {

    private Control control;
    private JTextField txtDia, txtMes, txtAnio;
    private JTable tablaAsistencias;
    private DefaultTableModel modeloTabla;
    private JButton btnBuscar, btnImprimir, btnCerrar;

    public DlgListadoAsistenciasFecha(JFrame parent, Control control) {
        super(parent, "Listado de Asistencias por Fecha", true);
        this.control = control;
        initUI();
        setSize(800, 450);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior con campos de fecha
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFecha.add(new JLabel("Fecha (dd/mm/aaaa):"));
        txtDia = new JTextField(3);
        panelFecha.add(txtDia);
        panelFecha.add(new JLabel("/"));
        txtMes = new JTextField(3);
        panelFecha.add(txtMes);
        panelFecha.add(new JLabel("/"));
        txtAnio = new JTextField(5);
        panelFecha.add(txtAnio);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this::buscar);
        panelFecha.add(btnBuscar);

        btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(this::imprimir);
        panelFecha.add(btnImprimir);

        add(panelFecha, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"Código Empleado", "Fecha", "Hora Ingreso", "Hora Salida", "Estado"}, 0);
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

    private void buscar(ActionEvent e) {
        try {
            int dia = Integer.parseInt(txtDia.getText().trim());
            int mes = Integer.parseInt(txtMes.getText().trim());
            int anio = Integer.parseInt(txtAnio.getText().trim());
            Fecha fecha = new Fecha(dia, mes, anio);

            modeloTabla.setRowCount(0);
            // Usamos el método de Control que obtiene todas las asistencias y filtra
            ArrayList<Asistencia> todas = control.consultaTodasAsistencias();
            for (Asistencia a : todas) {
                if (a.getFecha().equals(fecha)) {
                    modeloTabla.addRow(new Object[]{
                            a.getCodigoEmpleado(),
                            a.getFecha().toString(),
                            a.getHoraIngreso(),
                            a.getHoraSalida() != null ? a.getHoraSalida() : "--",
                            a.getEstado()
                    });
                }
            }
            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay asistencias para esta fecha");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos para la fecha");
        } catch (FachadaException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void imprimir(ActionEvent e) {
        try {
            boolean completo = tablaAsistencias.print(JTable.PrintMode.FIT_WIDTH);
            if (completo) {
                JOptionPane.showMessageDialog(this, "Impresión enviada");
            } else {
                JOptionPane.showMessageDialog(this, "Impresión cancelada");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al imprimir: " + ex.getMessage());
        }
    }
}