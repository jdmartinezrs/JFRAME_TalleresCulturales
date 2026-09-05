package actividad33;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaReporteParticipacion extends JInternalFrame {

    private JTable tablaResumen;
    private DefaultTableModel modeloTabla;
    private List<Taller> listaTalleres;

    public VentanaReporteParticipacion(List<Taller> listaTalleres) {
        this.listaTalleres = listaTalleres;

        setTitle("REPORTE DE PARTICIPACIÓN Y OCUPACIÓN");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize(800, 560);
        setLayout(new BorderLayout(10, 10));

        EstiloBrutalista.aplicarAFormulario(this);

        // --- TABLA RESUMEN CON ESTILO ---
        String[] columnas = {"CÓDIGO", "TALLER", "CUPO MAX", "INSCRITOS", "LIBRES", "% OCUPACIÓN"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaResumen = new JTable(modeloTabla);
        EstiloBrutalista.estilizarTabla(tablaResumen);

        JScrollPane scrollTabla = new JScrollPane(tablaResumen);
        scrollTabla.setPreferredSize(new Dimension(760, 180));
        scrollTabla.setBorder(EstiloBrutalista.BORDE_GROSERO);
        add(scrollTabla, BorderLayout.NORTH);

        // --- PANEL DE GRÁFICO BRUTALISTA ---
        PanelGraficoBrutalista panelGrafico = new PanelGraficoBrutalista();
        TitledBorder border = BorderFactory.createTitledBorder(EstiloBrutalista.BORDE_GROSERO, " OCUPACIÓN POR TALLER (BARRAS) ");
        border.setTitleFont(EstiloBrutalista.FUENTE_BOTON);
        border.setTitleColor(EstiloBrutalista.COLOR_NEGRO);
        panelGrafico.setBorder(border);
        panelGrafico.setBackground(EstiloBrutalista.COLOR_FONDO_DESK);

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

    // --- GRÁFICO ESTADÍSTICO DE BARRAS SOLIDAD Y CONTORNOS NEGROS ---
    private class PanelGraficoBrutalista extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (listaTalleres == null || listaTalleres.isEmpty()) {
                g.setFont(EstiloBrutalista.FUENTE_BOTON);
                g.drawString("NO HAY DATOS REGISTRADOS", 30, 40);
                return;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

            int width = getWidth();
            int height = getHeight();
            int padding = 50;
            int barWidth = Math.max(25, (width - (padding * 2)) / (listaTalleres.size() * 2 + 1));
            int maxHeight = height - padding * 2 - 20;

            int maxCupo = 1;
            for (Taller t : listaTalleres) {
                if (t.getCupoMaximo() > maxCupo) maxCupo = t.getCupoMaximo();
            }

            int x = padding;
            int baseY = height - padding;

            // Línea de Eje Gruesa
            g2.setColor(EstiloBrutalista.COLOR_NEGRO);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(padding - 10, baseY, width - padding + 10, baseY);

            // Leyenda Neobrutalista
            g2.setColor(EstiloBrutalista.COLOR_ROSA);
            g2.fillRect(width - 180, 15, 16, 16);
            g2.setColor(EstiloBrutalista.COLOR_NEGRO);
            g2.drawRect(width - 180, 15, 16, 16);
            g2.setFont(EstiloBrutalista.FUENTE_LABEL);
            g2.drawString("INSCRITOS", width - 158, 28);

            g2.setColor(EstiloBrutalista.COLOR_CIAN);
            g2.fillRect(width - 80, 15, 16, 16);
            g2.setColor(EstiloBrutalista.COLOR_NEGRO);
            g2.drawRect(width - 80, 15, 16, 16);
            g2.drawString("LIBRES", width - 58, 28);

            // Dibujar Bloques de Barras
            for (Taller t : listaTalleres) {
                int inscritos = t.getParticipantesInscritos().size();
                int disponibles = t.getCuposDisponibles();

                int hInscritos = (int) (((double) inscritos / maxCupo) * maxHeight);
                int hDisponibles = (int) (((double) disponibles / maxCupo) * maxHeight);

                // Barra Inscritos (Rosa Neón)
                if (hInscritos > 0) {
                    g2.setColor(EstiloBrutalista.COLOR_ROSA);
                    g2.fillRect(x, baseY - hInscritos, barWidth, hInscritos);
                    g2.setColor(EstiloBrutalista.COLOR_NEGRO);
                    g2.drawRect(x, baseY - hInscritos, barWidth, hInscritos);
                }

                // Barra Disponibles (Cian Neón)
                if (hDisponibles > 0) {
                    g2.setColor(EstiloBrutalista.COLOR_CIAN);
                    g2.fillRect(x + barWidth, baseY - hDisponibles, barWidth, hDisponibles);
                    g2.setColor(EstiloBrutalista.COLOR_NEGRO);
                    g2.drawRect(x + barWidth, baseY - hDisponibles, barWidth, hDisponibles);
                }

                // Etiqueta Nombre del Taller
                g2.setColor(EstiloBrutalista.COLOR_NEGRO);
                g2.setFont(EstiloBrutalista.FUENTE_LABEL);
                String label = t.getNombre().length() > 6 ? t.getNombre().substring(0, 5) + "." : t.getNombre();
                g2.drawString(label, x, baseY + 20);

                x += (barWidth * 2) + 20;
            }
        }
    }
}