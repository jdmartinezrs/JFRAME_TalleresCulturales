package actividad33;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class talleresCulturales extends JFrame {

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane;
	private List<Taller> listaTalleres;

	public static void main(String[] args) {
		// Personalizar Popups / JOptionPane con estilo Neobrutalista
		UIManager.put("OptionPane.background", EstiloBrutalista.COLOR_FONDO_DESK);
		UIManager.put("Panel.background", EstiloBrutalista.COLOR_FONDO_DESK);
		UIManager.put("OptionPane.messageFont", EstiloBrutalista.FUENTE_LABEL);
		UIManager.put("OptionPane.border", EstiloBrutalista.BORDE_GROSERO);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					talleresCulturales frame = new talleresCulturales();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public talleresCulturales() {
		listaTalleres = new ArrayList<>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650);
		setTitle("[ GESTIÓN DE TALLERES CULTURALES ]");

		// Configurar DesktopPane con color Crema Neobrutalista
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(EstiloBrutalista.COLOR_FONDO_DESK);
		setContentPane(desktopPane);

		// Barra de Menú Brutalista
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(EstiloBrutalista.COLOR_AMARILLO);
		menuBar.setBorder(new LineBorder(EstiloBrutalista.COLOR_NEGRO, 3));

		JMenu menuTalleres = crearMenuBrutalista("TALLERES");
		JMenu menuInscripciones = crearMenuBrutalista("INSCRIPCIONES");
		JMenu menuReportes = crearMenuBrutalista("REPORTES");

		menuBar.add(menuTalleres);
		menuBar.add(menuInscripciones);
		menuBar.add(menuReportes);
		setJMenuBar(menuBar);

		JMenuItem itemRegistrar = crearItemBrutalista("REGISTRAR TALLER");
		JMenuItem itemListarActivos = crearItemBrutalista("LISTAR ACTIVOS");
		JMenuItem itemGestionar = crearItemBrutalista("GESTIONAR / EDITAR");

		menuTalleres.add(itemRegistrar);
		menuTalleres.add(itemListarActivos);
		menuTalleres.add(itemGestionar);

		JMenuItem itemInscribir = crearItemBrutalista("INSCRIBIR PARTICIPANTE");
		menuInscripciones.add(itemInscribir);

		JMenuItem itemReporteParticipacion = crearItemBrutalista("REPORTE DE PARTICIPACIÓN");
		menuReportes.add(itemReporteParticipacion);

		// Acciones
		itemRegistrar.addActionListener(e -> {
			FormularioRegistrarTaller form = new FormularioRegistrarTaller(listaTalleres);
			desktopPane.add(form);
			form.setVisible(true);
		});

		itemListarActivos.addActionListener(e -> {
			VentanaListarTalleresActivos lista = new VentanaListarTalleresActivos(listaTalleres);
			desktopPane.add(lista);
			lista.setVisible(true);
		});

		itemGestionar.addActionListener(e -> {
			VentanaGestionTalleres gestion = new VentanaGestionTalleres(listaTalleres);
			desktopPane.add(gestion);
			gestion.setVisible(true);
		});

		itemInscribir.addActionListener(e -> {
			FormularioInscribirParticipante formInscripcion = new FormularioInscribirParticipante(listaTalleres);
			desktopPane.add(formInscripcion);
			formInscripcion.setVisible(true);
		});

		itemReporteParticipacion.addActionListener(e -> {
			VentanaReporteParticipacion reporte = new VentanaReporteParticipacion(listaTalleres);
			desktopPane.add(reporte);
			reporte.setVisible(true);
		});
	}

	private JMenu crearMenuBrutalista(String titulo) {
		JMenu menu = new JMenu(titulo);
		menu.setFont(EstiloBrutalista.FUENTE_BOTON);
		menu.setForeground(EstiloBrutalista.COLOR_NEGRO);
		return menu;
	}

	private JMenuItem crearItemBrutalista(String texto) {
		JMenuItem item = new JMenuItem(texto);
		item.setFont(EstiloBrutalista.FUENTE_LABEL);
		item.setBackground(EstiloBrutalista.COLOR_BLANCO);
		item.setForeground(EstiloBrutalista.COLOR_NEGRO);
		item.setBorder(new LineBorder(EstiloBrutalista.COLOR_NEGRO, 1));
		return item;
	}
}