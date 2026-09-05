package actividad33;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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

        setTitle("GESTIÓN Y EDICIÓN DE TALLERES");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize(750, 520);
        setLayout(new BorderLayout(10, 10));

        EstiloBrutalista.aplicarAFormulario(this);

        // --- TABLA SUPERIOR CON ESTILO ---
        String[] columnas = {"CÓDIGO", "NOMBRE", "INSTRUCTOR", "CUPO MAX.", "COSTO ($)", "ESTADO"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modeloTabla);
        EstiloBrutalista.estilizarTabla(tabla);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(720, 180));
        scrollPane.setBorder(EstiloBrutalista.BORDE_GROSERO);
        add(scrollPane, BorderLayout.CENTER);

        // --- FORMULARIO INFERIOR ---
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 6, 6));
        panelFormulario.setBackground(EstiloBrutalista.COLOR_FONDO_DESK);
        
        TitledBorder border = BorderFactory.createTitledBorder(EstiloBrutalista.BORDE_GROSERO, " DATOS DEL TALLER SELECCIONADO ");
        border.setTitleFont(EstiloBrutalista.FUENTE_BOTON);
        border.setTitleColor(EstiloBrutalista.COLOR_NEGRO);
        panelFormulario.setBorder(border);

        txtCodigo = agregarCampo(panelFormulario, "CÓDIGO:");
        txtNombre = agregarCampo(panelFormulario, "NOMBRE:");
        txtInstructor = agregarCampo(panelFormulario, "INSTRUCTOR:");
        txtCupo = agregarCampo(panelFormulario, "CUPO MÁXIMO:");
        txtCosto = agregarCampo(panelFormulario, "COSTO ($):");

        JLabel lblEstado = new JLabel("ESTADO:");
        lblEstado.setFont(EstiloBrutalista.FUENTE_LABEL);
        panelFormulario.add(lblEstado);

        chkActivo = new JCheckBox("ACTIVO");
        chkActivo.setFont(EstiloBrutalista.FUENTE_BOTON);
        chkActivo.setBackground(EstiloBrutalista.COLOR_FONDO_DESK);
        chkActivo.setForeground(EstiloBrutalista.COLOR_NEGRO);
        panelFormulario.add(chkActivo);

        // --- BOTONES INFERIORES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(EstiloBrutalista.COLOR_FONDO_DESK);

        JButton btnGuardar = EstiloBrutalista.crearBotonBrutalista("GUARDAR", EstiloBrutalista.COLOR_AMARILLO);
        JButton btnCambiarEstado = EstiloBrutalista.crearBotonBrutalista("ESTADO", EstiloBrutalista.COLOR_CIAN);
        JButton btnEliminar = EstiloBrutalista.crearBotonBrutalista("ELIMINAR", EstiloBrutalista.COLOR_ROSA);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCambiarEstado);
        panelBotones.add(btnEliminar);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(EstiloBrutalista.COLOR_FONDO_DESK);
        panelSur.add(panelFormulario, BorderLayout.CENTER);
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        add(panelSur, BorderLayout.SOUTH);

        // Selección en la tabla
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

        // Guardar Cambios
        btnGuardar.addActionListener(e -> {
            if (tallerSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "SELEACCIONE UN TALLER DE LA TABLA.", "ALERTA", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                tallerSeleccionado.setCodigo(txtCodigo.getText().trim());
                tallerSeleccionado.setNombre(txtNombre.getText().trim());
                tallerSeleccionado.setInstructor(txtInstructor.getText().trim());
                tallerSeleccionado.setCupoMaximo(Integer.parseInt(txtCupo.getText().trim()));
                tallerSeleccionado.setCosto(Double.parseDouble(txtCosto.getText().trim()));
                tallerSeleccionado.setActivo(chkActivo.isSelected());

                JOptionPane.showMessageDialog(this, "¡TALLER ACTUALIZADO!");
                cargarTabla();
                limpiarFormulario();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "VALORES NUMÉRICOS INCOHERENTES.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cambiar Estado
        btnCambiarEstado.addActionListener(e -> {
            if (tallerSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "SELECCIONE UN TALLER DE LA TABLA.", "ALERTA", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean nuevoEstado = !tallerSeleccionado.isActivo();
            tallerSeleccionado.setActivo(nuevoEstado);
            chkActivo.setSelected(nuevoEstado);

            JOptionPane.showMessageDialog(this, "ESTADO CAMBIADO A: " + (nuevoEstado ? "ACTIVO" : "INACTIVO"));
            cargarTabla();
        });

        // Eliminar
        btnEliminar.addActionListener(e -> {
            if (tallerSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "SELECCIONE UN TALLER PARA ELIMINAR.", "ALERTA", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int resp = JOptionPane.showConfirmDialog(
                this,
                "¿ELIMINAR TALLER: " + tallerSeleccionado.getNombre() + "?",
                "CONFIRMAR ELIMINACIÓN",
                JOptionPane.YES_NO_OPTION
            );

            if (resp == JOptionPane.YES_OPTION) {
                listaTalleres.remove(tallerSeleccionado);
                JOptionPane.showMessageDialog(this, "¡TALLER ELIMINADO!");
                cargarTabla();
                limpiarFormulario();
            }
        });

        cargarTabla();
    }

    private JTextField agregarCampo(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(EstiloBrutalista.FUENTE_LABEL);
        panel.add(lbl);

        JTextField tf = new JTextField();
        EstiloBrutalista.estilizarTextField(tf);
        panel.add(tf);
        return tf;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Taller t : listaTalleres) {
            Object[] fila = {
                t.getCodigo(),
                t.getNombre(),
                t.getInstructor(),
                t.getCupoMaximo(),
                String.format("$ %.2f", t.getCosto()),
                t.isActivo() ? "ACTIVO" : "INACTIVO"
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