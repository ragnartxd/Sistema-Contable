package gui;

import persistencia.DataManagerText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistorialCajaFrame extends JFrame {
    private DataManagerText dataManager;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    
    // Colores del tema
    private Color colorPrimario = new Color(15, 76, 92);
    private Color colorFondo = new Color(242, 246, 248);
    private Color colorExito = new Color(22, 120, 89);
    private Color colorError = new Color(190, 67, 57);
    private Font fuenteNormal = new Font("Segoe UI", Font.PLAIN, 14);
    private Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 16);

    public HistorialCajaFrame(DataManagerText dataManager) {
        this.dataManager = dataManager;
        
        // Configuración básica de la ventana
        setTitle("Historial de Cajas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(colorFondo);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Panel de título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(colorFondo);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("Historial de Registros de Caja");
        titleLabel.setFont(fuenteTitulo);
        titleLabel.setForeground(colorPrimario);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Crear modelo de tabla
        String[] columnas = {"Fecha", "Facturado", "No Facturado", "Gastos", "Datáfono", "Total", "Balance"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex >= 1 && columnIndex <= 5) {
                    return Double.class; // Para formato numérico
                }
                return String.class;
            }
        };
        
        // Crear tabla
        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setFont(fuenteNormal);
        tablaHistorial.setRowHeight(30);
        tablaHistorial.setShowVerticalLines(false);
        tablaHistorial.setGridColor(new Color(232, 238, 241));
        tablaHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaHistorial.getTableHeader().setBackground(colorPrimario);
        tablaHistorial.getTableHeader().setForeground(colorPrimario);
        
        // Personalizar renderer para colorear celdas según valor
        tablaHistorial.setDefaultRenderer(Double.class, new CustomTableCellRenderer());
        
        // Scroll pane para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(colorFondo);
        
        JButton refreshBtn = new JButton("Actualizar");
        refreshBtn.setFont(fuenteNormal);
        refreshBtn.setBackground(colorPrimario);
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.addActionListener(e -> cargarDatos());
        
        JButton closeBtn = new JButton("Cerrar");
        closeBtn.setFont(fuenteNormal);
        closeBtn.setBackground(new Color(189, 195, 199));
        closeBtn.setForeground(Color.BLACK);
        closeBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(closeBtn);
        
        // Panel de estadísticas
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        statsPanel.setBackground(colorFondo);
        statsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        // Etiquetas para estadísticas
        JLabel totalRegistrosLabel = new JLabel("Total registros: 0");
        totalRegistrosLabel.setFont(fuenteNormal);
        
        JLabel totalIngresosLabel = new JLabel("Total ingresos: $0.00");
        totalIngresosLabel.setFont(fuenteNormal);
        
        JLabel balancePromedioLabel = new JLabel("Balance promedio: $0.00");
        balancePromedioLabel.setFont(fuenteNormal);
        
        statsPanel.add(totalRegistrosLabel);
        statsPanel.add(totalIngresosLabel);
        statsPanel.add(balancePromedioLabel);
        
        // Agregar componentes al panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);
        
        // Agregar panel principal a la ventana
        setContentPane(mainPanel);
        
        // Cargar datos
        cargarDatos();
        
        // Actualizar estadísticas
        actualizarEstadisticas(totalRegistrosLabel, totalIngresosLabel, balancePromedioLabel);
    }
    
    private void cargarDatos() {
        // Limpiar tabla actual
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
        
        try {
            // Cargar datos desde el archivo
            List<String[]> registros = dataManager.loadData();
            
            // Agregar filas a la tabla
            for (String[] registro : registros) {
                if (registro.length >= 6) {
                    Object[] fila = new Object[7];
                    fila[0] = registro[0]; // Fecha
                    
                    // Convertir valores numéricos
                    double facturado = Double.parseDouble(registro[1]);
                    double noFacturado = Double.parseDouble(registro[2]);
                    double gastos = Double.parseDouble(registro[3]);
                    double datafono = Double.parseDouble(registro[4]);
                    double total = Double.parseDouble(registro[5]);
                    
                    // Asignar valores a la fila
                    fila[1] = facturado;
                    fila[2] = noFacturado;
                    fila[3] = gastos;
                    fila[4] = datafono;
                    fila[5] = total;
                    
                    // Determinar el balance (positivo/negativo)
                    String balance = total >= 0 ? "Positivo" : "Negativo";
                    fila[6] = balance;
                    
                    modeloTabla.addRow(fila);
                }
            }
            
            // Ajustar anchos de columnas
            ajustarAnchoColumnas();
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar el historial: " + ex.getMessage(),
                "Error de Carga",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void actualizarEstadisticas(JLabel totalRegistrosLabel, JLabel totalIngresosLabel, JLabel balancePromedioLabel) {
        int totalRegistros = modeloTabla.getRowCount();
        double totalIngresos = 0;
        double totalBalances = 0;
        
        for (int i = 0; i < totalRegistros; i++) {
            double total = (double) modeloTabla.getValueAt(i, 5);
            totalIngresos += total;
            totalBalances += total;
        }
        
        double balancePromedio = totalRegistros > 0 ? totalBalances / totalRegistros : 0;
        
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        
        totalRegistrosLabel.setText("Total registros: " + totalRegistros);
        totalIngresosLabel.setText("Total ingresos: " + formatoMoneda.format(totalIngresos));
        balancePromedioLabel.setText("Balance promedio: " + formatoMoneda.format(balancePromedio));
    }
    
    private void ajustarAnchoColumnas() {
        // Ajustar anchos de columnas según su contenido
        for (int i = 0; i < tablaHistorial.getColumnCount(); i++) {
            int width = 100; // Ancho predeterminado
            
            switch (i) {
                case 0: // Fecha
                    width = 150;
                    break;
                case 6: // Balance
                    width = 80;
                    break;
                default:
                    width = 100;
            }
            
            tablaHistorial.getColumnModel().getColumn(i).setPreferredWidth(width);
        }
    }
    
    // Renderer personalizado para colorear celdas según valores
    private class CustomTableCellRenderer extends DefaultTableCellRenderer {
        private NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Formatear valores numéricos como moneda
            if (value instanceof Double) {
                double val = (Double) value;
                setText(formatoMoneda.format(val));
                setHorizontalAlignment(SwingConstants.RIGHT);
                
                // Colorear según el valor (positivo en verde, negativo en rojo)
                if (val < 0) {
                    comp.setForeground(colorError);
                } else if (val > 0) {
                    comp.setForeground(colorExito);
                } else {
                    comp.setForeground(Color.BLACK);
                }
            } else if (column == 6) { // Balance (Positivo/Negativo)
                String balance = (String) value;
                ((DefaultTableCellRenderer) comp).setHorizontalAlignment(SwingConstants.CENTER);
                
                if ("Positivo".equals(balance)) {
                    comp.setForeground(colorExito);
                } else {
                    comp.setForeground(colorError);
                }
            } else {
                setHorizontalAlignment(SwingConstants.LEFT);
                comp.setForeground(Color.BLACK);
            }
            
            // Alternar colores de fondo para filas
            if (!isSelected) {
                if (row % 2 == 0) {
                    comp.setBackground(Color.WHITE);
                } else {
                    comp.setBackground(new Color(245, 245, 245));
                }
            }
            
            return comp;
        }
    }
}
