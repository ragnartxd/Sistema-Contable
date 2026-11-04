package gui;

import modelo.SistemaContable;


import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AlmacenRefrigasGUI extends JFrame {
    private SistemaContable sistema;
    private Color colorPrimario = new Color(15, 76, 92);
    
    private Color colorFondo = new Color(242, 246, 248);
    
    public AlmacenRefrigasGUI() {
        sistema = new SistemaContable();
        
        // Configuración básica de la ventana
        setTitle("Sistema Contable - Almacén Refrigas");
        setSize(1100, 740);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(colorFondo);
        
        
        // Creamos un panel con margen
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(colorFondo);
        
        // Banner superior
        JPanel bannerPanel = createBanner();
        
        // Pestañas (Tabs)
        JTabbedPane tabs = createTabbedPane();
        
        // Panel de estado inferior
        JPanel statusPanel = createStatusPanel();
        
        // Agregamos componentes al panel principal
        mainPanel.add(bannerPanel, BorderLayout.NORTH);
        mainPanel.add(tabs, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        // Configuramos el cierre de la aplicación
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    AlmacenRefrigasGUI.this,
                    "¿Está seguro que desea salir del sistema?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createBanner() {
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(colorPrimario);
        banner.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        
        JLabel title = new JLabel("SISTEMA CONTABLE ALMACÉN REFRIGAS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 21));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Gestión financiera  ·  Caja, clientes y cartera");
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        subtitle.setForeground(new Color(236, 240, 241));
        
        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.setBackground(colorPrimario);
        labelPanel.add(title);
        labelPanel.add(subtitle);
        
        // Simular un logo (con un panel de color)
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(colorPrimario);
        logoPanel.setPreferredSize(new Dimension(18, 50));
        
        banner.add(logoPanel, BorderLayout.WEST);
        banner.add(labelPanel, BorderLayout.CENTER);
        
        return banner;
    }
    
    private JTabbedPane createTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabs.setBackground(colorFondo);
        
        // Añadimos las pestañas sin íconos para evitar NullPointerException
        tabs.addTab("Registrar Caja", new PanelCaja(sistema));
        tabs.addTab("Clientes y Deudas", new PanelClientes(sistema));
        
        return tabs;
    }
    
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(189, 195, 199));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel statusLabel = new JLabel("© 2025 Almacén Refrigas • Sistema listo");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(44, 62, 80));
        
        statusPanel.add(statusLabel, BorderLayout.WEST);
        
        return statusPanel;
    }
    
    public static void main(String[] args) {
        try {
            // Intentamos usar el Look & Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(AlmacenRefrigasGUI::new);
    }
}
