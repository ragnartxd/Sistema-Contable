package gui;

import modelo.SistemaAutenticacion;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

/**
 * Ventana de registro de nuevos usuarios para el sistema contable
 */
public class RegistroUsuarioGUI extends JFrame {
    
    private SistemaAutenticacion sistemaAuth;
    private JTextField txtNombreUsuario;
    private JTextField txtNombreCompleto;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JComboBox<String> comboRol;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private Color colorPrimario = new Color(41, 128, 185); // Azul
    private Color colorFondo = new Color(236, 240, 241);   // Gris claro
    
    
    public RegistroUsuarioGUI(SistemaAutenticacion sistemaAuth) {
        this.sistemaAuth = sistemaAuth;
        
        // Configuración básica
        setTitle("Registro de Usuario - Almacén Refrigas");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(colorFondo);
        
        // Panel superior con título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorPrimario);
        panelSuperior.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("REGISTRO DE USUARIO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        
        // Panel de formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(colorFondo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre de Usuario
        JLabel lblUsuario = new JLabel("Nombre de Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(lblUsuario, gbc);
        
        txtNombreUsuario = new JTextField(20);
        txtNombreUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(txtNombreUsuario, gbc);
        
        // Nombre Completo
        JLabel lblNombreCompleto = new JLabel("Nombre Completo:");
        lblNombreCompleto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(lblNombreCompleto, gbc);
        
        txtNombreCompleto = new JTextField(20);
        txtNombreCompleto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelForm.add(txtNombreCompleto, gbc);
        
        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelForm.add(lblEmail, gbc);
        
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelForm.add(txtEmail, gbc);
        
        // Contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelForm.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        panelForm.add(txtPassword, gbc);
        
        // Confirmar Contraseña
        JLabel lblConfirmPassword = new JLabel("Confirmar Contraseña:");
        lblConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 8;
        panelForm.add(lblConfirmPassword, gbc);
        
        txtConfirmPassword = new JPasswordField(20);
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 9;
        panelForm.add(txtConfirmPassword, gbc);
        
        // Rol (solo visible si es administrador)
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 10;
        panelForm.add(lblRol, gbc);
        
        comboRol = new JComboBox<>(new String[]{"Cajero", "Administrador"});
        comboRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 11;
        panelForm.add(comboRol, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setBackground(colorFondo);
        
        // Botón Registrar
        btnRegistrar = new JButton("Registrar Usuario");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRegistrar.setBackground(colorPrimario);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        
        // Añadir efecto hover
        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRegistrar.setBackground(new Color(52, 152, 219));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnRegistrar.setBackground(colorPrimario);
            }
        });
        
        // Añadir acción de registro
        btnRegistrar.addActionListener(e -> registrarUsuario());
        
        // Botón Cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);
        
        // Agregar componentes al panel principal
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelForm, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        // Añadir al frame
        add(panel);
    }
    
    /**
     * Método que maneja el registro de un nuevo usuario
     */
    private void registrarUsuario() {
        // Recopilar datos del formulario
        String nombreUsuario = txtNombreUsuario.getText().trim();
        String nombreCompleto = txtNombreCompleto.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        
        // Validación de campos vacíos
        if (nombreUsuario.isEmpty() || nombreCompleto.isEmpty() || 
            email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Debe completar todos los campos",
                "Error de validación",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(
                this,
                "Las contraseñas no coinciden",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
            );
            txtPassword.setText("");
            txtConfirmPassword.setText("");
            txtPassword.requestFocus();
            return;
        }
        
        // Validar formato de email básico
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(
                this,
                "Formato de email inválido",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
            );
            txtEmail.requestFocus();
            return;
        }
        
        // Determinar el próximo ID (en una implementación real se haría en la base de datos)
        int nuevoId = sistemaAuth.getProximoIdUsuario();
        
        // Determinar el rol seleccionado
        Usuario.Rol rolSeleccionado = comboRol.getSelectedIndex() == 0 ? Usuario.Rol.CAJERO : Usuario.Rol.ADMINISTRADOR;
        
        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario(
            nuevoId, 
            nombreUsuario, 
            nombreCompleto, 
            email,
            password,
            rolSeleccionado
        );
        
        // Intentar registrar el usuario
        boolean registroExitoso = sistemaAuth.crearUsuario(nuevoUsuario);
        
        if (registroExitoso) {
            JOptionPane.showMessageDialog(
                this,
                "Usuario registrado exitosamente",
                "Registro Exitoso",
                JOptionPane.INFORMATION_MESSAGE
            );
            dispose(); // Cerrar ventana
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Error al registrar usuario. El nombre de usuario ya existe o no tienes permiso.",
                "Error de Registro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}