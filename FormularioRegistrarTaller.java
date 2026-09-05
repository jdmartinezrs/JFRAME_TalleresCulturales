package actividad33;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormularioRegistrarTaller extends JInternalFrame {

    private JTextField txtCodigo, txtNombre, txtInstructor, txtCupo, txtCosto;

    public FormularioRegistrarTaller(List<Taller> listaTalleres) {
        setTitle("Registrar Nuevo Taller");
        setClosable(true);
        setIconifiable(true);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel(" Código:"));
        txtCodigo = new JTextField();
        add(txtCodigo);

        add(new JLabel(" Nombre del Taller:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel(" Instructor:"));
        txtInstructor = new JTextField();
        add(txtInstructor);

        add(new JLabel(" Cupo Máximo:"));
        txtCupo = new JTextField();
        add(txtCupo);

        add(new JLabel(" Costo ($):"));
        txtCosto = new JTextField();
        add(txtCosto);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnGuardar);
        add(btnCancelar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Sanitización de entradas (Seguridad y limpieza de datos)
                    String codigo = txtCodigo.getText().trim();
                    String nombre = txtNombre.getText().trim();
                    String instructor = txtInstructor.getText().trim();
                    String strCupo = txtCupo.getText().trim();
                    String strCosto = txtCosto.getText().trim();

                    // REQUISITO 9: Campos vacíos
                    if (codigo.isEmpty() || nombre.isEmpty() || instructor.isEmpty() || strCupo.isEmpty() || strCosto.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Atención", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // REQUISITO 7: Evitar duplicidad de código de taller
                    for (Taller t : listaTalleres) {
                        if (t.getCodigo().equalsIgnoreCase(codigo)) {
                            JOptionPane.showMessageDialog(null, "Ya existe un taller registrado con el código: " + codigo, "Duplicidad Detectada", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    int cupo = Integer.parseInt(strCupo);
                    double costo = Double.parseDouble(strCosto);

                    // REQUISITO 9: Coherencia de datos (No valores negativos ni cero en cupos)
                    if (cupo <= 0) {
                        JOptionPane.showMessageDialog(null, "El cupo máximo debe ser mayor a 0.", "Dato Incoherente", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (costo < 0) {
                        JOptionPane.showMessageDialog(null, "El costo no puede ser un valor negativo.", "Dato Incoherente", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Registro seguro
                    Taller nuevoTaller = new Taller(codigo, nombre, instructor, cupo, costo);
                    listaTalleres.add(nuevoTaller);

                    JOptionPane.showMessageDialog(null, "Taller registrado exitosamente.");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Cupo y Costo deben ser numéricos válidos (ej. Cupo: 20, Costo: 15000.0).", "Error de Tipo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}