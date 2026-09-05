package actividad33;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class EstiloBrutalista {

    // Paleta Neobrutalista
    public static final Color COLOR_FONDO_DESK = new Color(245, 243, 238); // Crema Neóbrutal
    public static final Color COLOR_AMARILLO = new Color(255, 222, 89);  // Amarillo Neón
    public static final Color COLOR_ROSA = new Color(255, 105, 180);     // Magenta Neón
    public static final Color COLOR_CIAN = new Color(0, 240, 255);       // Cian Neón
    public static final Color COLOR_NEGRO = new Color(10, 10, 10);
    public static final Color COLOR_BLANCO = new Color(255, 255, 255);

    // Fuentes
    public static final Font FUENTE_TITULO = new Font("Arial Black", Font.BOLD, 16);
    public static final Font FUENTE_BOTON = new Font("Arial Black", Font.BOLD, 12);
    public static final Font FUENTE_LABEL = new Font("SansSerif", Font.BOLD, 12);
    public static final Font FUENTE_TEXTO = new Font("SansSerif", Font.PLAIN, 12);

    // Bordes
    public static final Border BORDE_GROSERO = new LineBorder(COLOR_NEGRO, 3);

    public static void aplicarAFormulario(JInternalFrame frame) {
        frame.getContentPane().setBackground(COLOR_FONDO_DESK);
        frame.setBorder(BORDE_GROSERO);
    }

    // --- NUEVO: ESTILIZAR TABLA BRUTALISTA ---
    public static void estilizarTabla(JTable tabla) {
        // Encabezados con amarillo neón y fuentes bold
        JTableHeader header = tabla.getTableHeader();
        header.setFont(FUENTE_BOTON);
        header.setBackground(COLOR_AMARILLO);
        header.setForeground(COLOR_NEGRO);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        header.setBorder(BORDE_GROSERO);

        // Filas, rejilla y selección en CIAN
        tabla.setFont(FUENTE_TEXTO);
        tabla.setRowHeight(30);
        tabla.setBackground(COLOR_BLANCO);
        tabla.setForeground(COLOR_NEGRO);
        tabla.setSelectionBackground(COLOR_CIAN);
        tabla.setSelectionForeground(COLOR_NEGRO);
        tabla.setGridColor(COLOR_NEGRO);
        tabla.setShowGrid(true);

        // Centrado de contenido
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static JButton crearBotonBrutalista(String texto, Color colorFondo) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

                // Sombra solida desplazada
                g2.setColor(COLOR_NEGRO);
                g2.fillRect(4, 4, getWidth() - 4, getHeight() - 4);

                // Fondo del botón
                g2.setColor(getModel().isPressed() ? colorFondo.darker() : colorFondo);
                g2.fillRect(0, 0, getWidth() - 4, getHeight() - 4);

                // Borde negro grueso
                g2.setColor(COLOR_NEGRO);
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(0, 0, getWidth() - 4, getHeight() - 4);

                // Texto del botón
                g2.setFont(FUENTE_BOTON);
                g2.setColor(COLOR_NEGRO);
                FontMetrics fm = g2.getFontMetrics();
                int x = ((getWidth() - 4) - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - 4) + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
            }
        };

        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 42));
        return btn;
    }

    public static void estilizarTextField(JTextField textField) {
        textField.setFont(FUENTE_TEXTO);
        textField.setBackground(COLOR_BLANCO);
        textField.setForeground(COLOR_NEGRO);
        textField.setCaretColor(COLOR_NEGRO);
        Border bordeNegro = new LineBorder(COLOR_NEGRO, 2);
        Border relleno = new EmptyBorder(4, 6, 4, 6);
        textField.setBorder(new CompoundBorder(bordeNegro, relleno));
    }
}