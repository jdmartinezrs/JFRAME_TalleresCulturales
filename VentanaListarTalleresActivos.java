package actividad33;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListarTalleresActivos extends JInternalFrame {

    private JTable tablaTalleres;
    private DefaultTableModel modeloTabla;

    public VentanaListarTalleresActivos(List<Taller> listaTalleres) {
        setTitle("TALLERES CULTURALES ACTIVOS");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize(620, 380);
        setLayout(new BorderLayout());

        EstiloBrutalista.aplicarAFormulario(this);

        String[] columnas = {"CÓDIGO", "NOMBRE", "INSTRUCTOR", "CUPO MAX.", "INSCRITOS", "COSTO ($)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaTalleres = new JTable(modeloTabla);
        EstiloBrutalista.estilizarTabla(tablaTalleres); // Apariencia Neobrutalista a la tabla

        JScrollPane scrollPane = new JScrollPane(tablaTalleres);
        scrollPane.getViewport().setBackground(EstiloBrutalista.COLOR_FONDO_DESK);
        scrollPane.setBorder(EstiloBrutalista.BORDE_GROSERO);
        add(scrollPane, BorderLayout.CENTER);

        cargarDatos(listaTalleres);
    }

    private void cargarDatos(List<Taller> listaTalleres) {
        modeloTabla.setRowCount(0);

        for (Taller t : listaTalleres) {
            if (t.isActivo()) {
                Object[] fila = {
                    t.getCodigo(),
                    t.getNombre(),
                    t.getInstructor(),
                    t.getCupoMaximo(),
                    t.getParticipantesInscritos().size(),
                    String.format("$ %.2f", t.getCosto())
                };
                modeloTabla.addRow(fila);
            }
        }
    }
}