package actividad33;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class talleresCulturales extends JFrame {

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane;
	private List<Taller> listaTalleres;

	public static void main(String[] args) {
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
		setBounds(100, 100, 800, 600);
		setTitle("Gestión de Talleres Culturales");

		desktopPane = new JDesktopPane();
		setContentPane(desktopPane);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuTalleres = new JMenu("Talleres");
		JMenu menuInscripciones = new JMenu("Inscripciones");
		JMenu menuReportes = new JMenu("Reportes");

		menuBar.add(menuTalleres);
		menuBar.add(menuInscripciones);
		menuBar.add(menuReportes);

		JMenuItem itemRegistrar = new JMenuItem("Registrar Taller");
		JMenuItem itemListarActivos = new JMenuItem("Listar Talleres Activos");
		JMenuItem itemGestionar = new JMenuItem("Gestionar Talleres (Editar/Eliminar)");

		menuTalleres.add(itemRegistrar);
		menuTalleres.add(itemListarActivos);
		menuTalleres.add(itemGestionar);

		JMenuItem itemInscribir = new JMenuItem("Inscribir Participante");
		menuInscripciones.add(itemInscribir);

		JMenuItem itemReporteParticipacion = new JMenuItem("Reporte de Participación");
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
}