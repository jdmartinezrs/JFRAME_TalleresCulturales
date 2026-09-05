package actividad33;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionTalleres extends JInternalFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtCodigo, txtNombre, txtInstructor, txtCupo, txtCosto;
    private JCheckBox chkActivo;
    private List<Taller> listaTalleres;
    private Taller tallerSeleccionado = null;

    public VentanaGestionTalleres(List<Taller> lista) {
        this.listaTalleres = lista;

        setTitle("Gestión / Edición / Eliminación de Talleres");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize(700, 480);
        setLayout(new BorderLayout(10, 10));

        // --- TABLA SUPERIOR ---
        String[] columnas = {"Código", "Nombre", "Instructor", "Cupo Max.", "Costo ($)", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // --- PANEL DE FORMULARIO INFERIOR ---
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Taller Seleccionado"));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Instructor:"));
        txtInstructor = new JTextField();
        panelFormulario.add(txtInstructor);

        panelFormulario.add(new JLabel("Cupo Máximo:"));
        txtCupo = new JTextField();
        panelFormulario.add(txtCupo);

        panelFormulario.add(new JLabel("Costo ($):"));
        txtCosto = new JTextField();
        panelFormulario.add(txtCosto);

        panelFormulario.add(new JLabel("Estado:"));
        chkActivo = new JCheckBox("Activo");
        panelFormulario.add(chkActivo);

        // --- PANEL DE BOTONES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar Cambios");
        JButton btnCambiarEstado = new JButton("Activar / Desactivar");
        JButton btnEliminar = new JButton("Eliminar Taller");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCambiarEstado);
        panelBotones.add(btnEliminar);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelFormulario, BorderLayout.CENTER);
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        add(panelSur, BorderLayout.SOUTH);

        // --- EVENTO AL SELECCIONAR UNA FILA DE LA TABLA ---
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0 && fila < listaTalleres.size()) {
                tallerSeleccionado = listaTalleres.get(fila);
                txtCodigo.setText(tallerSeleccionado.getCodigo());
                txtNombre.setText(tallerSeleccionado.getNombre());
                txtInstructor.setText(tallerSeleccionado.getInstructor());
                txtCupo.setText(String.valueOf(tallerSeleccionado.getCupoMaximo()));
                txtCosto.setText(String.valueOf(tallerSeleccionado.getCosto()));
                chkActivo.setSelected(tallerSeleccionado.isActivo());
            }
        });

        // --- GUARDAR EDICIÓN ---
        btnGuardar.addActionListener(e -> {
            if (tallerSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un taller de la tabla.");
                return;
            }
            try {
                tallerSeleccionado.setCodigo(txtCodigo.getText().trim());
                tallerSeleccionado.setNombre(txtNombre.getText().trim());
                tallerSeleccionado.setInstructor(txtInstructor.getText().trim());
                tallerSeleccionado.setCupoMaximo(Integer.parseInt(txtCupo.getText().trim()));
                tallerSeleccionado.setCosto(Double.parseDouble(txtCosto.getText().trim()));
                tallerSeleccionado.setActivo(chkActivo.isSelected());

                JOptionPane.showMessageDialog(this, "Taller actualizado con éxito.");
                cargarTabla();
                limpiarFormulario();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cupo y Costo deben ser numéricos.");
            }
        });

        // --- CAMBIAR ESTADO RÁPIDO ---
        btnCambiarEstado.addActionListener(e -> {
            if (tallerSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un taller de la tabla.");
                return;
            }
            boolean nuevoEstado = !tallerSeleccionado.isActivo();
            tallerSeleccionado.setActivo(nuevoEstado);
            chkActivo.setSelected(nuevoEstado);

            String estadoTexto = nuevoEstado ? "ACTIVO" : "INACTIVO";
            JOptionPane.showMessageDialog(this, "El estado del taller cambió a: " + estadoTexto);
            cargarTabla();
        });

        // --- ELIMINAR REGISTRO ---
        btnEliminar.addActionListener(e -> {
            if (tallerSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un taller para eliminar.");
                return;
            }
            int resp = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el taller: " + tallerSeleccionado.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (resp == JOptionPane.YES_OPTION) {
                listaTalleres.remove(tallerSeleccionado);
                JOptionPane.showMessageDialog(this, "Taller eliminado correctamente.");
                cargarTabla();
                limpiarFormulario();
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Taller t : listaTalleres) {
            Object[] fila = {
                t.getCodigo(),
                t.getNombre(),
                t.getInstructor(),
                t.getCupoMaximo(),
                String.format("%.2f", t.getCosto()),
                t.isActivo() ? "Activo" : "Inactivo"
            };
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarFormulario() {
        tallerSeleccionado = null;
        txtCodigo.setText("");
        txtNombre.setText("");
        txtInstructor.setText("");
        txtCupo.setText("");
        txtCosto.setText("");
        chkActivo.setSelected(false);
        tabla.clearSelection();
    }
}