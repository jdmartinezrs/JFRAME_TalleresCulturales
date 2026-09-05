package actividad33;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListarTalleresActivos extends JInternalFrame {

    private JTable tablaTalleres;
    private DefaultTableModel modeloTabla;

    public VentanaListarTalleresActivos(List<Taller> listaTalleres) {
        setTitle("Listado de Talleres Activos");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize(550, 350);
        setLayout(new BorderLayout());

        // Definir columnas de la tabla
        String[] columnas = {"Código", "Nombre", "Instructor", "Cupo Max.", "Inscritos", "Costo ($)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };

        tablaTalleres = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaTalleres);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar datos
        cargarDatos(listaTalleres);
    }

    private void cargarDatos(List<Taller> listaTalleres) {
        modeloTabla.setRowCount(0); // Limpiar filas previas

        for (Taller t : listaTalleres) {
            // Filtrar únicamente los talleres en estado ACTIVO
            if (t.isActivo()) {
                Object[] fila = {
                    t.getCodigo(),
                    t.getNombre(),
                    t.getInstructor(),
                    t.getCupoMaximo(),
                    t.getParticipantesInscritos().size(),
                    String.format("%.2f", t.getCosto())
                };
                modeloTabla.addRow(fila);
            }
        }
    }
}