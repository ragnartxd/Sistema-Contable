package gui;

import modelo.SistemaAutenticacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

/**
 * Ventana de inicio de sesión para el sistema contable
 */
public class LoginGUI extends JFrame {
    
    private SistemaAutenticacion sistemaAuth;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistro;
    private JButton btnSalir;
    private Color colorPrimario = new Color(41, 128, 185); // Azul
    private Color colorFondo = new Color(236, 240, 241);   // Gris claro
    
    /**
     * Constructor de la ventana de login
     * @param sistemaAuth Sistema de autenticación
     */
    public LoginGUI(SistemaAutenticacion sistemaAuth) {
        this.sistemaAuth = sistemaAuth;
        
        // Configuración básica
        setTitle("Acceso al Sistema - Almacén Refrigas");
        setSize(400, 450);  // Aumentamos un poco el tamaño
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(colorFondo);
        
        // Panel superior con logo/título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorPrimario);
        panelSuperior.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("ALMACÉN REFRIGAS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblSubtitulo = new JLabel("Sistema de Gestión Contable");
        lblSubtitulo.setForeground(new Color(236, 240, 241));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setBackground(colorPrimario);
        panelTextos.add(lblTitulo);
        panelTextos.add(lblSubtitulo);
        
        panelSuperior.add(panelTextos, BorderLayout.CENTER);
        
        // Panel de formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(colorFondo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Etiqueta Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelForm.add(lblUsuario, gbc);
        
        // Campo Usuario
        txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelForm.add(txtUsuario, gbc);
        
        // Etiqueta Contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panelForm.add(lblPassword, gbc);
        
        // Campo Contraseña
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelForm.add(txtPassword, gbc);
        
        // Panel de botones principal
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 5, 10));
        panelBotones.setBackground(colorFondo);
        
        // Panel para botones de acción
        JPanel panelBotonesPrincipales = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotonesPrincipales.setBackground(colorFondo);
        
        // Botón Login
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogin.setBackground(colorPrimario);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        
        // Añadir efecto hover
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(52, 152, 219));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(colorPrimario);
            }
        });
        
        // Añadir acción de login
        btnLogin.addActionListener(e -> iniciarSesion());
        
        // Botón Salir
        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(e -> System.exit(0));
        
        panelBotonesPrincipales.add(btnLogin);
        panelBotonesPrincipales.add(btnSalir);
        
        // Panel para botón de registro
        JPanel panelBotonesSecundarios = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonesSecundarios.setBackground(colorFondo);
        
        // Botón de Registro
        btnRegistro = new JButton("Registrarse");
        btnRegistro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRegistro.setBackground(new Color(46, 204, 113)); // Verde
        btnRegistro.setForeground(Color.WHITE);
        btnRegistro.setFocusPainted(false);
        
        // Añadir efecto hover
        btnRegistro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRegistro.setBackground(new Color(60, 219, 127));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnRegistro.setBackground(new Color(46, 204, 113));
            }
        });
        
        // Añadir acción de registro
        btnRegistro.addActionListener(e -> abrirRegistro());
        
        panelBotonesSecundarios.add(btnRegistro);
        
        // Añadir los dos paneles de botones al panel principal
        panelBotones.add(panelBotonesPrincipales);
        panelBotones.add(panelBotonesSecundarios);
        
        // Atajos de teclado
        // Enter en campo de contraseña ejecuta login
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    iniciarSesion();
                }
            }
        });
        
        // Agregar componentes al panel principal
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelForm, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        // Añadir al frame
        add(panel);
    }
    
    /**
     * Método que maneja la autenticación del usuario
     */
    private void iniciarSesion() {
        String username = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Validación de campos vacíos
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Debe completar todos los campos",
                "Error de validación",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Intentar autenticación
        if (sistemaAuth.login(username, password)) {
            // Autenticación exitosa
            JOptionPane.showMessageDialog(
                this,
                "Bienvenido " + sistemaAuth.getUsuarioActual().getNombreCompleto(),
                "Acceso correcto",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            // Cerrar ventana de login y abrir ventana principal
            dispose();
            // Aquí se debería abrir la ventana principal del sistema
            new AlmacenRefrigasGUI().setVisible(true);
        } else {
            // Autenticación fallida
            JOptionPane.showMessageDialog(
                this,
                "Usuario o contraseña incorrectos",
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE
            );
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
    
    /**
     * Método que abre la ventana de registro de usuario
     */
    private void abrirRegistro() {
        // Aquí puedes abrir la ventana de registro de usuario
        JOptionPane.showMessageDialog(
            this,
            "Funcionalidad de registro no implementada.",
            "Registro",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Método principal para iniciar la aplicación
     */
    public static void main(String[] args) {
        try {
            // Intentamos usar el Look & Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            SistemaAutenticacion sistemaAuth = new SistemaAutenticacion();
            LoginGUI login = new LoginGUI(sistemaAuth);
            login.setVisible(true);
        });
    }
}