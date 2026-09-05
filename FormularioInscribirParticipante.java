package actividad33;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class FormularioInscribirParticipante extends JInternalFrame {

    private JComboBox<Taller> cbTalleres;
    private JTextField txtDocumento, txtNombre, txtCorreo;
    private JLabel lblCuposDisponibles;

    public FormularioInscribirParticipante(List<Taller> listaTalleres) {
        setTitle("INSCRIBIR PARTICIPANTE");
        setClosable(true);
        setIconifiable(true);
        setSize(480, 380);

        // Estilo del frame contenedor
        EstiloBrutalista.aplicarAFormulario(this);

        JPanel panelPrincipal = new JPanel(new GridLayout(6, 2, 8, 8));
        panelPrincipal.setBackground(EstiloBrutalista.COLOR_FONDO_DESK);
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Combo Box estilizado
        panelPrincipal.add(crearEtiqueta("SELECCIONAR TALLER:"));
        cbTalleres = new JComboBox<>();
        cbTalleres.setFont(EstiloBrutalista.FUENTE_TEXTO);
        cbTalleres.setBackground(EstiloBrutalista.COLOR_BLANCO);
        cbTalleres.setForeground(EstiloBrutalista.COLOR_NEGRO);
        cbTalleres.setBorder(new LineBorder(EstiloBrutalista.COLOR_NEGRO, 2));
        cargarTalleres(listaTalleres);
        panelPrincipal.add(cbTalleres);

        // Indicador de Cupos con resalte Neón
        panelPrincipal.add(crearEtiqueta("CUPOS DISPONIBLES:"));
        lblCuposDisponibles = new JLabel("-", SwingConstants.CENTER);
        lblCuposDisponibles.setFont(EstiloBrutalista.FUENTE_BOTON);
        lblCuposDisponibles.setOpaque(true);
        lblCuposDisponibles.setBackground(EstiloBrutalista.COLOR_CIAN);
        lblCuposDisponibles.setForeground(EstiloBrutalista.COLOR_NEGRO);
        lblCuposDisponibles.setBorder(new LineBorder(EstiloBrutalista.COLOR_NEGRO, 2));
        panelPrincipal.add(lblCuposDisponibles);

        // Campos de Texto
        txtDocumento = agregarCampo(panelPrincipal, "DOCUMENTO:");
        txtNombre = agregarCampo(panelPrincipal, "NOMBRE COMPLETO:");
        txtCorreo = agregarCampo(panelPrincipal, "CORREO ELECTRONICO:");

        // Botones Neobrutalistas
        JButton btnInscribir = EstiloBrutalista.crearBotonBrutalista("INSCRIBIR", EstiloBrutalista.COLOR_AMARILLO);
        JButton btnCancelar = EstiloBrutalista.crearBotonBrutalista("CANCELAR", EstiloBrutalista.COLOR_ROSA);

        panelPrincipal.add(btnInscribir);
        panelPrincipal.add(btnCancelar);

        add(panelPrincipal);

        cbTalleres.addActionListener(e -> actualizarCuposLabel());
        actualizarCuposLabel();

        // Evento Inscribir con validaciones
        btnInscribir.addActionListener(e -> {
            Taller tallerSeleccionado = (Taller) cbTalleres.getSelectedItem();

            if (tallerSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "DEBE SELECCIONAR UN TALLER VÁLIDO.", "ALERTA", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!tallerSeleccionado.isActivo()) {
                JOptionPane.showMessageDialog(this, "EL TALLER SELECCIONADO SE ENCUENTRA INACTIVO.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tallerSeleccionado.getCuposDisponibles() <= 0) {
                JOptionPane.showMessageDialog(this, "NO HAY CUPOS DISPONIBLES EN ESTE TALLER.", "CUPO AGOTADO", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String doc = txtDocumento.getText().trim();
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();

            if (doc.isEmpty() || nombre.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "TODOS LOS CAMPOS SON OBLIGATORIOS.", "ALERTA", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "CORREO ELECTRÓNICO NO VÁLIDO.", "CORREO INVÁLIDO", JOptionPane.WARNING_MESSAGE);
                return;
            }

            for (Participante p : tallerSeleccionado.getParticipantesInscritos()) {
                if (p.getDocumento().equalsIgnoreCase(doc)) {
                    JOptionPane.showMessageDialog(this, "EL PARTICIPANTE YA SE ENCUENTRA INSCRITO.", "DUPLICIDAD", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            Participante nuevoParticipante = new Participante(doc, nombre, correo);
            boolean exito = tallerSeleccionado.inscribirParticipante(nuevoParticipante);

            if (exito) {
                JOptionPane.showMessageDialog(this, "¡PARTICIPANTE INSCRITO CON ÉXITO!");
                actualizarCuposLabel();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL REALIZAR LA INSCRIPCIÓN.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(EstiloBrutalista.FUENTE_LABEL);
        lbl.setForeground(EstiloBrutalista.COLOR_NEGRO);
        return lbl;
    }

    private JTextField agregarCampo(JPanel panel, String etiqueta) {
        panel.add(crearEtiqueta(etiqueta));
        JTextField tf = new JTextField();
        EstiloBrutalista.estilizarTextField(tf);
        panel.add(tf);
        return tf;
    }

    private void cargarTalleres(List<Taller> listaTalleres) {
        cbTalleres.removeAllItems();
        for (Taller t : listaTalleres) {
            if (t.isActivo()) {
                cbTalleres.addItem(t);
            }
        }
    }

    private void actualizarCuposLabel() {
        Taller taller = (Taller) cbTalleres.getSelectedItem();
        if (taller != null) {
            lblCuposDisponibles.setText(taller.getCuposDisponibles() + " LIBRES DE " + taller.getCupoMaximo());
        } else {
            lblCuposDisponibles.setText("SIN TALLERES ACTIVOS");
        }
    }
}