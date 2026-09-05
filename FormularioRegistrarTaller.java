package actividad33;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormularioRegistrarTaller extends JInternalFrame {

    private JTextField txtCodigo, txtNombre, txtInstructor, txtCupo, txtCosto;

    public FormularioRegistrarTaller(List<Taller> listaTalleres) {
        setTitle("REGISTRAR TALLER CULTURAL");
        setClosable(true);
        setIconifiable(true);
        setSize(420, 360);
        EstiloBrutalista.aplicarAFormulario(this);

        JPanel panelPrincipal = new JPanel(new GridLayout(6, 2, 8, 8));
        panelPrincipal.setBackground(EstiloBrutalista.COLOR_FONDO_DESK);
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        txtCodigo = agregarCampo(panelPrincipal, "CÓDIGO TALLER:");
        txtNombre = agregarCampo(panelPrincipal, "NOMBRE:");
        txtInstructor = agregarCampo(panelPrincipal, "INSTRUCTOR:");
        txtCupo = agregarCampo(panelPrincipal, "CUPO MÁXIMO:");
        txtCosto = agregarCampo(panelPrincipal, "COSTO ($):");

        JButton btnGuardar = EstiloBrutalista.crearBotonBrutalista("GUARDAR", EstiloBrutalista.COLOR_AMARILLO);
        JButton btnCancelar = EstiloBrutalista.crearBotonBrutalista("CANCELAR", EstiloBrutalista.COLOR_ROSA);

        panelPrincipal.add(btnGuardar);
        panelPrincipal.add(btnCancelar);

        add(panelPrincipal);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String codigo = txtCodigo.getText().trim();
                    String nombre = txtNombre.getText().trim();
                    String instructor = txtInstructor.getText().trim();
                    String strCupo = txtCupo.getText().trim();
                    String strCosto = txtCosto.getText().trim();

                    if (codigo.isEmpty() || nombre.isEmpty() || instructor.isEmpty() || strCupo.isEmpty() || strCosto.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "¡TODOS LOS CAMPOS SON OBLIGATORIOS!", "ALERTA", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    for (Taller t : listaTalleres) {
                        if (t.getCodigo().equalsIgnoreCase(codigo)) {
                            JOptionPane.showMessageDialog(null, "CÓDIGO DUPLICADO: " + codigo, "ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    int cupo = Integer.parseInt(strCupo);
                    double costo = Double.parseDouble(strCosto);

                    if (cupo <= 0 || costo < 0) {
                        JOptionPane.showMessageDialog(null, "VALORES NUMÉRICOS INCOHERENTES", "ALERTA", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Taller nuevoTaller = new Taller(codigo, nombre, instructor, cupo, costo);
                    listaTalleres.add(nuevoTaller);

                    JOptionPane.showMessageDialog(null, "¡TALLER REGISTRADO CON ÉXITO!");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "INGRESE VALORES NUMÉRICOS VÁLIDOS", "ERROR DE TIPO", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private JTextField agregarCampo(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(EstiloBrutalista.FUENTE_LABEL);
        lbl.setForeground(EstiloBrutalista.COLOR_NEGRO);

        JTextField tf = new JTextField();
        EstiloBrutalista.estilizarTextField(tf);

        panel.add(lbl);
        panel.add(tf);
        return tf;
    }
}