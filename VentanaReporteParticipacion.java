package actividad33;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaReporteParticipacion extends JInternalFrame {

    private JTable tablaResumen;
    private DefaultTableModel modeloTabla;
    private List<Taller> listaTalleres;

    public VentanaReporteParticipacion(List<Taller> listaTalleres) {
        this.listaTalleres = listaTalleres;

        setTitle("Reporte de Participación y Ocupación");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize(750, 520);
        setLayout(new BorderLayout(10, 10));

        // --- TABLA DE RESUMEN DE PARTICIPACIÓN ---
        String[] columnas = {"Código", "Taller", "Cupo Total", "Inscritos", "Disponibles", "% Ocupación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaResumen = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaResumen);
        scrollTabla.setPreferredSize(new Dimension(720, 180));
        add(scrollTabla, BorderLayout.NORTH);

        // --- PANEL DEL GRÁFICO PERSONALIZADO ---
        PanelGraficoGrafico panelGrafico = new PanelGraficoGrafico();
        panelGrafico.setBorder(BorderFactory.createTitledBorder("Visualización de Ocupación por Taller"));
        add(panelGrafico, BorderLayout.CENTER);

        cargarReporte();
    }

    private void cargarReporte() {
        modeloTabla.setRowCount(0);
        for (Taller t : listaTalleres) {
            int inscritos = t.getParticipantesInscritos().size();
            int disponibles = t.getCuposDisponibles();
            int total = t.getCupoMaximo();
            double porcentaje = total > 0 ? ((double) inscritos / total) * 100 : 0.0;

            Object[] fila = {
                t.getCodigo(),
                t.getNombre(),
                total,
                inscritos,
                disponibles,
                String.format("%.1f%%", porcentaje)
            };
            modeloTabla.addRow(fila);
        }
    }

    // --- SUBCLASE INTERNA PARA DIBUJAR EL GRÁFICO EN SWING ---
    private class PanelGraficoGrafico extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (listaTalleres == null || listaTalleres.isEmpty()) {
                g.drawString("No hay datos de talleres para generar el gráfico.", 20, 30);
                return;
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int padding = 50;
            int barWidth = Math.max(20, (width - (padding * 2)) / (listaTalleres.size() * 2));
            int maxHeight = height - padding * 2 - 30;

            // Encontrar el valor máximo de cupos para escalar las barras
            int maxCupo = 1;
            for (Taller t : listaTalleres) {
                if (t.getCupoMaximo() > maxCupo) {
                    maxCupo = t.getCupoMaximo();
                }
            }

            int x = padding;
            int baseY = height - padding;

            // Dibujar Eje Base
            g2d.setColor(Color.GRAY);
            g2d.drawLine(padding - 10, baseY, width - padding + 10, baseY);

            // Leyenda
            g2d.setColor(new Color(41, 128, 185)); // Azul
            g2d.fillRect(width - 160, 15, 12, 12);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Inscritos", width - 142, 26);

            g2d.setColor(new Color(189, 195, 199)); // Gris
            g2d.fillRect(width - 80, 15, 12, 12);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Libres", width - 62, 26);

            // Dibujar Barras por cada taller
            for (Taller t : listaTalleres) {
                int inscritos = t.getParticipantesInscritos().size();
                int disponibles = t.getCuposDisponibles();

                int hInscritos = (int) (((double) inscritos / maxCupo) * maxHeight);
                int hDisponibles = (int) (((double) disponibles / maxCupo) * maxHeight);

                // Barra Inscritos (Azul)
                g2d.setColor(new Color(41, 128, 185));
                g2d.fillRect(x, baseY - hInscritos, barWidth, hInscritos);

                // Barra Disponibles (Gris)
                g2d.setColor(new Color(189, 195, 199));
                g2d.fillRect(x + barWidth, baseY - hDisponibles, barWidth, hDisponibles);

                // Etiqueta Nombre del Taller
                g2d.setColor(Color.BLACK);
                String nombreCorto = t.getNombre().length() > 8 ? t.getNombre().substring(0, 7) + ".." : t.getNombre();
                g2d.drawString(nombreCorto, x, baseY + 15);

                x += (barWidth * 2) + 15; // Desplazar al siguiente bloque
            }
        }
    }
}