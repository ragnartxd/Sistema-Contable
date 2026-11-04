package gui;

import modelo.Cliente;
import modelo.Deuda;
import modelo.SistemaContable;
import persistencia.DataManagerText;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PanelClientes extends JPanel {
    private SistemaContable sistema;
    private JTextField idField, nombreField, emailField;
    private JTextField deudaField, periodicidadField;
    private JTable clientesTable;
    private DefaultTableModel tableModel;
    private JTextArea detalleArea;
    private DataManagerText dataManager;
    private static final String DATA_FILE = "clientes_registrados.txt";
    private static final String DEUDAS_FILE = "deudas_registradas.txt";
    
    // Colores del tema
    private Color colorPrimario = new Color(15, 76, 92);
    private Color colorSecundario = new Color(219, 237, 239);
    private Color colorFondo = new Color(242, 246, 248);

    private Font fuenteNormal = new Font("Segoe UI", Font.PLAIN, 14);
    

    public PanelClientes(SistemaContable sistema) {
        this.sistema = sistema;
        this.dataManager = new DataManagerText(DATA_FILE);
        setLayout(new BorderLayout(15, 15));
        setBackground(colorFondo);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel izquierdo (formularios)
        JPanel leftPanel = crearPanelIzquierdo();
        
        // Panel derecho (lista y detalles)
        JPanel rightPanel = crearPanelDerecho();
        
        // Panel divisor
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            leftPanel,
            rightPanel
        );
        splitPane.setDividerLocation(440);
        splitPane.setMinimumSize(new Dimension(0, 0));
        splitPane.setResizeWeight(0.46);
        splitPane.setBackground(colorFondo);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerSize(8);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Inicializar con los datos existentes
        cargarClientesGuardados();
    }
    
    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(colorFondo);
        
        // Panel registro cliente
        JPanel pnlCliente = crearPanelRegistroCliente();
        
        // Panel deuda
        JPanel pnlDeuda = crearPanelDeuda();
        
        // Panel de botones adicionales
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBotones.setBackground(colorFondo);
        
        JButton btnHistorial = new JButton("Ver Historial de Clientes y Deudas");
        btnHistorial.setFont(fuenteNormal);
        btnHistorial.setBackground(colorPrimario);
        btnHistorial.setForeground(colorPrimario);
        btnHistorial.setOpaque(true);
        btnHistorial.addActionListener(e -> abrirHistorial());
        
        pnlBotones.add(btnHistorial);
        
        panel.add(pnlCliente, BorderLayout.NORTH);
        panel.add(pnlDeuda, BorderLayout.CENTER);
        panel.add(pnlBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelRegistroCliente() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(BorderFactory.createLineBorder(colorPrimario), 
                             "Registro de Cliente", 
                             TitledBorder.DEFAULT_JUSTIFICATION, 
                             TitledBorder.DEFAULT_POSITION, 
                             fuenteNormal, 
                             colorPrimario),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        
        idField = crearCampoTexto();
        nombreField = crearCampoTexto();
        emailField = crearCampoTexto();
        
        formPanel.add(crearEtiqueta("ID Cliente:"));
        formPanel.add(idField);
        formPanel.add(crearEtiqueta("Nombre:"));
        formPanel.add(nombreField);
        formPanel.add(crearEtiqueta("Email:"));
        formPanel.add(emailField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(fuenteNormal);
        btnLimpiar.setBackground(new Color(189, 195, 199));
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.addActionListener(e -> limpiarCamposCliente());
        
        JButton btnAgregar = new JButton("Agregar Cliente");
        btnAgregar.setFont(fuenteNormal);
        btnAgregar.setBackground(colorPrimario);
        btnAgregar.setForeground(colorPrimario);
        btnAgregar.setOpaque(true);
        btnAgregar.addActionListener(this::agregarCliente);
        
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(btnAgregar);
        
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelDeuda() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(BorderFactory.createLineBorder(colorPrimario), 
                             "Registro de Deuda", 
                             TitledBorder.DEFAULT_JUSTIFICATION, 
                             TitledBorder.DEFAULT_POSITION, 
                             fuenteNormal, 
                             colorPrimario),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        
        deudaField = crearCampoTexto();
        periodicidadField = crearCampoTexto();
        
        formPanel.add(crearEtiqueta("Monto Deuda ($):"));
        formPanel.add(deudaField);
        formPanel.add(crearEtiqueta("Periodicidad:"));
        formPanel.add(periodicidadField);
        
        // Panel con opciones predefinidas para periodicidad
        JPanel periodicidadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        periodicidadPanel.setBackground(Color.WHITE);
        
        JLabel labelOpciones = new JLabel("Opciones:");
        labelOpciones.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        
        JButton btnMensual = new JButton("Mensual");
        btnMensual.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnMensual.addActionListener(e -> periodicidadField.setText("MENSUAL"));
        
        JButton btnQuincenal = new JButton("Quincenal");
        btnQuincenal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnQuincenal.addActionListener(e -> periodicidadField.setText("QUINCENAL"));
        
        periodicidadPanel.add(labelOpciones);
        periodicidadPanel.add(btnMensual);
        periodicidadPanel.add(btnQuincenal);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(fuenteNormal);
        btnLimpiar.setBackground(new Color(189, 195, 199));
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.addActionListener(e -> limpiarCamposDeuda());
        
        JButton btnAgregar = new JButton("Agregar Deuda");
        btnAgregar.setFont(fuenteNormal);
        btnAgregar.setBackground(colorPrimario);
        btnAgregar.setForeground(colorPrimario);
        btnAgregar.setOpaque(true);
        btnAgregar.addActionListener(this::agregarDeuda);
        
        
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(btnAgregar);
        
        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(periodicidadPanel, BorderLayout.SOUTH);
        periodicidadPanel.setBorder(new EmptyBorder(0, 0, 2, 0));
        
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(colorFondo);
        
        // Panel con la tabla de clientes
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(BorderFactory.createLineBorder(colorPrimario), 
                             "Lista de Clientes", 
                             TitledBorder.DEFAULT_JUSTIFICATION, 
                             TitledBorder.DEFAULT_POSITION, 
                             fuenteNormal, 
                             colorPrimario),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        // Crear modelo de tabla
        String[] columnas = {"ID", "Nombre", "Email", "Deuda Total"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        clientesTable = new JTable(tableModel);
        clientesTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        clientesTable.setRowHeight(25);
        clientesTable.setShowVerticalLines(false);
        clientesTable.setGridColor(new Color(232, 238, 241));
        clientesTable.setFillsViewportHeight(true);
        clientesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        clientesTable.getTableHeader().setBackground(colorSecundario);
        clientesTable.getTableHeader().setForeground(colorPrimario);
        
        // Listener para selección
        clientesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalleClienteSeleccionado();
            }
        });
        
        JScrollPane scrollTable = new JScrollPane(clientesTable);
        scrollTable.setBorder(BorderFactory.createEmptyBorder());
        
        tablePanel.add(scrollTable, BorderLayout.CENTER);
        
        // Panel con detalles de deudas
        JPanel detallePanel = new JPanel(new BorderLayout());
        detallePanel.setBackground(Color.WHITE);
        detallePanel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(BorderFactory.createLineBorder(colorPrimario), 
                             "Detalle de Deudas", 
                             TitledBorder.DEFAULT_JUSTIFICATION, 
                             TitledBorder.DEFAULT_POSITION, 
                             fuenteNormal, 
                             colorPrimario),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        detalleArea = new JTextArea(5, 30);
        detalleArea.setEditable(false);
        detalleArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detalleArea.setBackground(Color.WHITE);
        detalleArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollDetalle = new JScrollPane(detalleArea);
        scrollDetalle.setBorder(BorderFactory.createEmptyBorder());
        
        detallePanel.add(scrollDetalle, BorderLayout.CENTER);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(detallePanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(fuenteNormal);
        return label;
    }
    
    private JTextField crearCampoTexto() {
        JTextField field = new JTextField();
        field.setFont(fuenteNormal);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return field;
    }
    
    private void limpiarCamposCliente() {
        idField.setText("");
        nombreField.setText("");
        emailField.setText("");
    }
    
    private void limpiarCamposDeuda() {
        deudaField.setText("");
        periodicidadField.setText("");
    }
    
    private void mostrarDetalleClienteSeleccionado() {
        int selectedRow = clientesTable.getSelectedRow();
        if (selectedRow >= 0) {
            String nombre = (String) tableModel.getValueAt(selectedRow, 1);
            Cliente cliente = sistema.buscarClientePorNombre(nombre);
            if (cliente != null) {
                // Rellenar los campos de texto con la información del cliente seleccionado
                idField.setText(String.valueOf(cliente.getId()));
                nombreField.setText(cliente.getNombre());
                emailField.setText(cliente.getEmail());
                
                // Mostrar detalles de deudas
                StringBuilder sb = new StringBuilder();
                sb.append("Cliente: ").append(cliente.getNombre()).append("\n");
                sb.append("Email: ").append(cliente.getEmail()).append("\n\n");
                
                if (cliente.getDeudas().isEmpty()) {
                    sb.append("No tiene deudas registradas.");
                } else {
                    sb.append("DEUDAS REGISTRADAS:\n");
                    sb.append("--------------------------------------------------\n");
                    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
                    
                    for (int i = 0; i < cliente.getDeudas().size(); i++) {
                        Deuda d = cliente.getDeudas().get(i);
                        sb.append(i + 1).append(". ");
                        sb.append("Monto: ").append(formatoMoneda.format(d.getMontoPendiente()));
                        sb.append(" - Periodicidad: ").append(d.getPeriodicidad()).append("\n");
                    }
                    
                    sb.append("--------------------------------------------------\n");
                    sb.append("TOTAL PENDIENTE: ").append(formatoMoneda.format(cliente.calcularTotalPendiente()));
                }
                
                detalleArea.setText(sb.toString());
            }
        }
    }

    private void agregarCliente(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nom = nombreField.getText().trim();
            String mail = emailField.getText().trim();
            
            // Validaciones básicas
            if (nom.isEmpty() || mail.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Todos los campos son obligatorios",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            Cliente c = new Cliente(id, nom, mail);
            sistema.agregarCliente(c);
            
            // Guardar el cliente en el archivo
            guardarCliente(id, nom, mail);
            
            // Limpiar campos
            limpiarCamposCliente();
            actualizarTablaClientes();
            
            JOptionPane.showMessageDialog(
                this,
                "Cliente registrado correctamente",
                "Registro Exitoso",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "El ID debe ser un número entero",
                "Error de Formato",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void agregarDeuda(ActionEvent e) {
        try {
            String nom = nombreField.getText().trim();
            Cliente c = sistema.buscarClientePorNombre(nom);
            if (c == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Cliente no encontrado. Por favor, seleccione un cliente de la lista.",
                    "Cliente no encontrado",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            double monto = Double.parseDouble(deudaField.getText().trim().replace(",", ""));
            String per = periodicidadField.getText().trim();
            
            // Validación de periodicidad
            if (!per.equals("MENSUAL") && !per.equals("QUINCENAL")) {
                JOptionPane.showMessageDialog(
                    this,
                    "La periodicidad debe ser MENSUAL o QUINCENAL",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            Deuda d = new Deuda(monto, per);
            c.agregarDeuda(d);
            
            // Guardar la deuda en el archivo
            guardarDeuda(c.getId(), monto, per);
            
            // Limpiar campos
            limpiarCamposDeuda();
            actualizarTablaClientes();
            mostrarDetalleClienteSeleccionado();
            
            JOptionPane.showMessageDialog(
                this,
                "Deuda registrada correctamente",
                "Registro Exitoso",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "El monto debe ser un valor numérico válido",
                "Error de Formato",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void actualizarTablaClientes() {
        // Limpiar la tabla
        tableModel.setRowCount(0);
        
        // Actualizar con los clientes actuales
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        
        for (Cliente c : sistema.getClientes()) {
            Object[] fila = {
                c.getId(),
                c.getNombre(),
                c.getEmail(),
                formatoMoneda.format(c.calcularTotalPendiente())
            };
            tableModel.addRow(fila);
        }
    }
    
    private void guardarCliente(int id, String nombre, String email) {
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
            
            String[] nuevoRegistro = {
                fecha,
                String.valueOf(id),
                nombre,
                email
            };
            
            // Añadir nuevo registro
            registros.add(nuevoRegistro);
            
            // Guardar todos los registros
            dataManager.guardarDatos(registros);
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error al guardar el cliente: " + ex.getMessage(),
                "Error de Guardado",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void guardarDeuda(int clienteId, double monto, String periodicidad) {
        DataManagerText deudaManager = new DataManagerText(DEUDAS_FILE);
        try {
            // Cargar datos existentes
            List<String[]> registros = new ArrayList<>();
            try {
                registros = deudaManager.loadData();
            } catch (IOException ex) {
                // Si hay error al cargar, empezamos con lista vacía
                System.err.println("No se pudieron cargar los datos existentes: " + ex.getMessage());
            }
            
            // Crear nuevo registro con fecha actual
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha = sdf.format(new Date());
            
            String[] nuevoRegistro = {
                fecha,
                String.valueOf(clienteId),
                String.valueOf(monto),
                periodicidad
            };
            
            // Añadir nuevo registro
            registros.add(nuevoRegistro);
            
            // Guardar todos los registros
            deudaManager.guardarDatos(registros);
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error al guardar la deuda: " + ex.getMessage(),
                "Error de Guardado",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void cargarClientesGuardados() {
        try {
            List<String[]> registros = dataManager.loadData();
            for (String[] registro : registros) {
                // Verificar que el registro tenga al menos 4 campos
                if (registro.length >= 4) {
                    try {
                        int id = Integer.parseInt(registro[1]);
                        String nombre = registro[2];
                        String email = registro[3];
                        
                        // Verificar si el cliente ya existe
                        if (sistema.buscarClientePorNombre(nombre) == null) {
                            Cliente c = new Cliente(id, nombre, email);
                            sistema.agregarCliente(c);
                        }
                    } catch (NumberFormatException ex) {
                        System.err.println("Error al convertir ID: " + ex.getMessage());
                    }
                }
            }
            
            // Cargar las deudas
            cargarDeudasGuardadas();
            
            // Actualizar la tabla
            actualizarTablaClientes();
            
        } catch (IOException ex) {
            System.err.println("Error al cargar clientes: " + ex.getMessage());
        }
    }
    
    private void cargarDeudasGuardadas() {
        DataManagerText deudaManager = new DataManagerText(DEUDAS_FILE);
        try {
            List<String[]> registros = deudaManager.loadData();
            for (String[] registro : registros) {
                // Verificar que el registro tenga al menos 4 campos
                if (registro.length >= 4) {
                    try {
                        int clienteId = Integer.parseInt(registro[1]);
                        double monto = Double.parseDouble(registro[2]);
                        String periodicidad = registro[3];
                        
                        // Buscar el cliente por ID
                        Cliente cliente = buscarClientePorId(clienteId);
                        if (cliente != null) {
                            Deuda d = new Deuda(monto, periodicidad);
                            cliente.agregarDeuda(d);
                        }
                    } catch (NumberFormatException ex) {
                        System.err.println("Error al convertir datos de deuda: " + ex.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al cargar deudas: " + ex.getMessage());
        }
    }
    
    private Cliente buscarClientePorId(int id) {
        for (Cliente c : sistema.getClientes()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    
    private void abrirHistorial() {
        // Crear ventana para mostrar el historial
        JFrame historialFrame = new JFrame("Historial de Clientes y Deudas");
        historialFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        historialFrame.setSize(800, 600);
        historialFrame.setLocationRelativeTo(this);
        
        // Crear panel con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña de clientes
        JPanel clientesPanel = crearPanelHistorialClientes();
        tabbedPane.addTab("Clientes", clientesPanel);
        
        // Pestaña de deudas
        JPanel deudasPanel = crearPanelHistorialDeudas();
        tabbedPane.addTab("Deudas", deudasPanel);
        
        historialFrame.add(tabbedPane);
        historialFrame.setVisible(true);
    }
    
    private JPanel crearPanelHistorialClientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Crear modelo de tabla para clientes
        String[] columnas = {"Fecha de Registro", "ID", "Nombre", "Email"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(colorSecundario);
        tabla.getTableHeader().setForeground(colorPrimario);
        
        // Cargar datos en la tabla
        try {
            List<String[]> registros = dataManager.loadData();
            for (String[] registro : registros) {
                if (registro.length >= 4) {
                    modeloTabla.addRow(registro);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, 
                "Error al cargar historial de clientes: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelHistorialDeudas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Crear modelo de tabla para deudas
        String[] columnas = {"Fecha de Registro", "ID Cliente", "Monto", "Periodicidad"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(colorSecundario);
        tabla.getTableHeader().setForeground(colorPrimario);
        
        // Cargar datos en la tabla
        DataManagerText deudaManager = new DataManagerText(DEUDAS_FILE);
        try {
            List<String[]> registros = deudaManager.loadData();
            for (String[] registro : registros) {
                if (registro.length >= 4) {
                    modeloTabla.addRow(registro);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, 
                "Error al cargar historial de deudas: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
}
