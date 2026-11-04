package gui;

import modelo.Caja;
import modelo.Gasto;
import modelo.IngresosDiarios;
import modelo.PagoDatafono;
import modelo.SistemaContable;
import persistencia.DataManagerText;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PanelCaja extends JPanel {
	private SistemaContable sistema;
	private JTextField facturadoField, noFacturadoField, gastoField, datafonoField;
	private JTextArea resultadoArea;
	private JLabel totalLabel;
	private DataManagerText dataManager;
	private static final String DATA_FILE = "cajas_registradas.txt";

	// Colores del tema
	private Color colorPrimario = new Color(15, 76, 92);
	private Color colorFondo = new Color(242, 246, 248);
	private Color colorExito = new Color(22, 120, 89);
	private Color colorError = new Color(190, 67, 57);
	private Font fuenteNormal = new Font("Segoe UI", Font.PLAIN, 14);
	private Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 16);

	public PanelCaja(SistemaContable sistema) {
		this.sistema = sistema;
		this.dataManager = new DataManagerText(DATA_FILE);
		setLayout(new BorderLayout(15, 15));
		setBackground(colorFondo);
		setBorder(new EmptyBorder(20, 20, 20, 20));

		// Panel superior con título
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(colorFondo);
		headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

		JLabel titleLabel = new JLabel("Registro de Caja Diaria");
		titleLabel.setFont(fuenteTitulo);
		titleLabel.setForeground(colorPrimario);
		headerPanel.add(titleLabel, BorderLayout.WEST);

		// Botón para ver historial
		JButton historialBtn = new JButton("Ver Historial");
		historialBtn.setFont(fuenteNormal);
		historialBtn.setBackground(colorPrimario);
		historialBtn.setForeground(Color.BLACK);
		historialBtn.addActionListener(e -> abrirHistorial());
		headerPanel.add(historialBtn, BorderLayout.EAST);

		// Formulario de entrada con scroll
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBackground(colorFondo);
		formPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10),
				BorderFactory.createLineBorder(new Color(216, 226, 231), 1, true)));

		// Sección de Ingresos
		JPanel ingresosPanel = createFormSection("INGRESOS", colorExito);
		facturadoField = createFormattedTextField();
		noFacturadoField = createFormattedTextField();

		addFormField(ingresosPanel, "Monto Facturado ($):", facturadoField);
		addFormField(ingresosPanel, "Monto No Facturado ($):", noFacturadoField);

		// Sección de Egresos
		JPanel egresosPanel = createFormSection("EGRESOS", colorError);
		gastoField = createFormattedTextField();
		datafonoField = createFormattedTextField();

		addFormField(egresosPanel, "Gastos ($):", gastoField);
		addFormField(egresosPanel, "Pagos por Datáfono ($):", datafonoField);

		// Añadimos las secciones al panel de formulario
		formPanel.add(ingresosPanel);
		formPanel.add(Box.createVerticalStrut(15));
		formPanel.add(egresosPanel);

		// Creamos un JScrollPane para contener el formulario
		JScrollPane formScrollPane = new JScrollPane(formPanel);
		formScrollPane.setBorder(BorderFactory.createEmptyBorder());
		formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		formScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		// Panel de botones
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBackground(colorFondo);

		JButton clearBtn = new JButton("Limpiar");
		clearBtn.setFont(fuenteNormal);
		clearBtn.setBackground(new Color(189, 195, 199));
		clearBtn.setForeground(Color.BLACK);
		clearBtn.addActionListener(e -> limpiarCampos());

		JButton registerBtn = new JButton("Registrar y Calcular");
		registerBtn.setFont(fuenteNormal);
		registerBtn.setBackground(colorPrimario);
		registerBtn.setForeground(Color.BLACK);
		registerBtn.addActionListener(this::registrarCaja);

		buttonPanel.add(clearBtn);
		buttonPanel.add(Box.createHorizontalStrut(10));
		buttonPanel.add(registerBtn);

		// Área de resultado
		JPanel resultPanel = new JPanel(new BorderLayout(10, 10));
		resultPanel.setBackground(Color.WHITE);
		resultPanel.setBorder(BorderFactory.createCompoundBorder(
			new TitledBorder(BorderFactory.createLineBorder(new Color(216, 226, 231)), "Resultado de Operación",
						TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, fuenteNormal, colorPrimario),
				new EmptyBorder(10, 10, 10, 10)));

		resultadoArea = new JTextArea(5, 30);
		resultadoArea.setEditable(false);
		resultadoArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		resultadoArea.setBackground(Color.WHITE);
		resultadoArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JScrollPane resultScrollPane = new JScrollPane(resultadoArea);
		resultScrollPane.setBorder(BorderFactory.createEmptyBorder());

		// Etiqueta para total
		totalLabel = new JLabel("Total: $0.00");
		totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
		totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		resultPanel.add(resultScrollPane, BorderLayout.CENTER);
		resultPanel.add(totalLabel, BorderLayout.SOUTH);

		// Panel central con formulario y botones
		JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
		centerPanel.setBackground(colorFondo);

		// Modificamos esta parte para usar un panel con CardLayout para mejor
		// organización
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(fuenteNormal);
		tabbedPane.addTab("Formulario", new JScrollPane(formPanel));

		centerPanel.add(tabbedPane, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Panel principal con todas las secciones
		add(headerPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(resultPanel, BorderLayout.SOUTH);

		// Asegurarse de que la ventana tenga un tamaño mínimo razonable
		setPreferredSize(new Dimension(900, 620));
	}

	private JPanel createFormSection(String title, Color color) {
		// Usando GridBagLayout para un mejor control sobre la disposición
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(colorFondo);
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(189, 195, 199)), new EmptyBorder(5, 5, 10, 5)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		titleLabel.setForeground(color);

		panel.add(titleLabel, gbc);

		return panel;
	}

	private JTextField createFormattedTextField() {
		JTextField field = new JTextField();
		field.setFont(fuenteNormal);
		field.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
						BorderFactory.createEmptyBorder(5, 7, 5, 7)));
		field.setPreferredSize(new Dimension(200, 30));
		return field;
	}

	private void addFormField(JPanel panel, String labelText, JComponent field) {
		// Utilizamos GridBagLayout para una mejor distribución
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel label = new JLabel(labelText);
		label.setFont(fuenteNormal);

		gbc.gridwidth = 1;
		panel.add(label, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panel.add(field, gbc);
	}

	private void registrarCaja(ActionEvent e) {
		try {
			double fact = Double.parseDouble(facturadoField.getText().replace(",", ""));
			double noFact = Double.parseDouble(noFacturadoField.getText().replace(",", ""));
			double g = Double.parseDouble(gastoField.getText().replace(",", ""));
			double d = Double.parseDouble(datafonoField.getText().replace(",", ""));

			IngresosDiarios ing = new IngresosDiarios(fact, noFact);
			Gasto gasto = new Gasto();
			gasto.setMonto(g);
			PagoDatafono pd = new PagoDatafono(d);
			Caja caja = new Caja(ing, gasto, pd);

			sistema.agregarCaja(caja);

			// Mostrar en el área de resultado
			resultadoArea.setText(caja.resumenCaja());

			// Actualizar la etiqueta del total
			double total = ing.calcularTotalIngresos() - g - d;
			NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
			totalLabel.setText("Total: " + formato.format(total));

			// Cambiar el color según si es positivo o negativo
			if (total >= 0) {
				totalLabel.setForeground(colorExito);
			} else {
				totalLabel.setForeground(colorError);
			}

			// Guardar los datos en el archivo de texto
			guardarRegistroCaja(fact, noFact, g, d, total);

			// Mostrar notificación de éxito
			JOptionPane.showMessageDialog(this, "Operación registrada y guardada correctamente", "Registro Exitoso",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (NumberFormatException ex) {
			resultadoArea.setText("Error: Todos los campos deben ser numéricos.");
			totalLabel.setText("Total: $0.00");
			totalLabel.setForeground(Color.BLACK);

			JOptionPane.showMessageDialog(this, "Todos los campos deben contener valores numéricos válidos",
					"Error de Formato", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void guardarRegistroCaja(double facturado, double noFacturado, double gastos, double datafono,
			double total) {
		try {
			// Cargar datos existentes
			List<String[]> registros = new ArrayList<>();
			try {
				registros = dataManager.loadData();
			} catch (IOException ex) {
				// Si hay error al cargar, empezamos con lista vacía
				System.err.println("No se pudieron cargar los datos existentes: " + ex.getMessage());
			}

			// Crear nuevo registro con fecha actual
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fecha = sdf.format(new Date());

			String[] nuevoRegistro = { fecha, String.valueOf(facturado), String.valueOf(noFacturado),
					String.valueOf(gastos), String.valueOf(datafono), String.valueOf(total) };

			// Añadir nuevo registro
			registros.add(nuevoRegistro);

			// Guardar todos los registros
			dataManager.guardarDatos(registros);

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Error al guardar el registro: " + ex.getMessage(), "Error de Guardado",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void abrirHistorial() {
		// Crear y mostrar la ventana de historial
		JFrame historialFrame = new HistorialCajaFrame(dataManager);
		historialFrame.setVisible(true);
	}

	private void limpiarCampos() {
		facturadoField.setText("");
		noFacturadoField.setText("");
		gastoField.setText("");
		datafonoField.setText("");
		resultadoArea.setText("");
		totalLabel.setText("Total: $0.00");
		totalLabel.setForeground(Color.BLACK);
	}
}
