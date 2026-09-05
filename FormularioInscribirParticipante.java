package actividad33;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormularioInscribirParticipante extends JInternalFrame {

    private JComboBox<Taller> cbTalleres;
    private JTextField txtDocumento, txtNombre, txtCorreo;
    private JLabel lblCuposDisponibles;

    public FormularioInscribirParticipante(List<Taller> listaTalleres) {
        setTitle("Inscribir Participante en Taller");
        setClosable(true);
        setIconifiable(true);
        setSize(450, 320);
        setLayout(new GridLayout(6, 2, 5, 5));

        // Selector de talleres
        add(new JLabel(" Seleccionar Taller:"));
        cbTalleres = new JComboBox<>();
        cargarTalleres(listaTalleres);
        add(cbTalleres);

        // Visualización de cupos
        add(new JLabel(" Cupos Disponibles:"));
        lblCuposDisponibles = new JLabel("-");
        lblCuposDisponibles.setFont(new Font("SansSerif", Font.BOLD, 12));
        add(lblCuposDisponibles);

        // Campos del participante
        add(new JLabel(" Documento de Identidad:"));
        txtDocumento = new JTextField();
        add(txtDocumento);

        add(new JLabel(" Nombre Completo:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel(" Correo Electrónico:"));
        txtCorreo = new JTextField();
        add(txtCorreo);

        JButton btnInscribir = new JButton("Inscribir");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnInscribir);
        add(btnCancelar);

        // Evento al cambiar el taller seleccionado en el ComboBox
        cbTalleres.addActionListener(e -> actualizarCuposLabel());
        actualizarCuposLabel();

        // Guardar inscripción
        btnInscribir.addActionListener(e -> {
            Taller tallerSeleccionado = (Taller) cbTalleres.getSelectedItem();

            if (tallerSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un taller válido.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // REQUISITO 8: Controlar disponibilidad de cupo y estado activo
            if (!tallerSeleccionado.isActivo()) {
                JOptionPane.showMessageDialog(this, "El taller seleccionado se encuentra INACTIVO.", "Taller Inactivo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tallerSeleccionado.getCuposDisponibles() <= 0) {
                JOptionPane.showMessageDialog(this, "No hay cupos disponibles en este taller.", "Cupo Agotado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String doc = txtDocumento.getText().trim();
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();

            // REQUISITO 9: Campos vacíos
            if (doc.isEmpty() || nombre.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos del participante son obligatorios.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // REQUISITO 9: Coherencia en formato de Correo
            if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "Ingrese una dirección de correo electrónico válida (ej: usuario@dominio.com).", "Correo Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // REQUISITO 7: Evitar duplicidad de participante en el mismo taller
            for (Participante p : tallerSeleccionado.getParticipantesInscritos()) {
                if (p.getDocumento().equalsIgnoreCase(doc)) {
                    JOptionPane.showMessageDialog(this, "El participante con documento " + doc + " ya está inscrito en este taller.", "Duplicidad Detectada", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Inscripción
            Participante nuevoParticipante = new Participante(doc, nombre, correo);
            boolean exito = tallerSeleccionado.inscribirParticipante(nuevoParticipante);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Participante inscrito exitosamente.");
                actualizarCuposLabel();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo realizar la inscripción por falta de cupos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarTalleres(List<Taller> listaTalleres) {
        cbTalleres.removeAllItems();
        for (Taller t : listaTalleres) {
            if (t.isActivo()) { // Filtrar solo talleres activos
                cbTalleres.addItem(t);
            }
        }
    }

    private void actualizarCuposLabel() {
        Taller taller = (Taller) cbTalleres.getSelectedItem();
        if (taller != null) {
            lblCuposDisponibles.setText(taller.getCuposDisponibles() + " de " + taller.getCupoMaximo());
        } else {
            lblCuposDisponibles.setText("Sin talleres");
        }
    }
    
    
}