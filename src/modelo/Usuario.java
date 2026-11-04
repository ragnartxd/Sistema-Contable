package modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Usuario {
    
    private int id;
    private String nombreUsuario;
    private String nombreCompleto;
    private String email;
    private String passwordHash;
    private String salt;
    private Rol rol;
    private boolean activo;
    private java.util.Date ultimoAcceso;
    
    /**
     * Enumeración de roles disponibles en el sistema (ahora como clase anidada)
     */
    public enum Rol {
        ADMINISTRADOR(1, "Administrador", "Acceso completo al sistema"),
        CAJERO(2, "Cajero", "Acceso a operaciones de caja y consultas básicas");
        
        private final int id;
        private final String nombre;
        private final String descripcion;
        
        Rol(int id, String nombre, String descripcion) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
        }
        
        public int getId() {
            return id;
        }
        
        public String getNombre() {
            return nombre;
        }
        
        public String getDescripcion() {
            return descripcion;
        }
        
        public static Rol rolSeleccionado(String rol) {
            for (Rol r : Rol.values()) {
                if (r.getNombre().equalsIgnoreCase(rol)) {
                    return r;
                }
            }
            return null; // o lanzar una excepción
        }
    }
    
    /**
     * Constructor por defecto
     */
    public Usuario() {
        this.id = 0;
        this.nombreUsuario = "";
        this.nombreCompleto = "";
        this.email = "";
        this.passwordHash = "";
        this.salt = generarSalt();
        this.rol = null;
        this.activo = true;
        this.ultimoAcceso = null;
    }
    
    /**
     * Constructor con parámetros básicos
     * @param nombreUsuario Nombre de usuario único
     * @param password Contraseña sin encriptar
     * @param rol Rol asignado al usuario
     */
    public Usuario(String nombreUsuario, String password, Rol rol) {
        this();
        this.nombreUsuario = nombreUsuario;
        this.salt = generarSalt();
        this.passwordHash = hashPassword(password, this.salt);
        this.rol = rol;
    }
    
    /**
     * Constructor completo
     * @param id ID único del usuario
     * @param nombreUsuario Nombre de usuario único
     * @param nombreCompleto Nombre completo del usuario
     * @param email Email del usuario
     * @param password Contraseña sin encriptar
     * @param rol Rol asignado al usuario
     */
    public Usuario(int id, String nombreUsuario, String nombreCompleto, String email, String password, Rol rol) {
        this(nombreUsuario, password, rol);
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
    }
    
    /**
     * Genera un salt aleatorio para mayor seguridad en el hash
     * @return String con salt generado
     */
    private String generarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    /**
     * Crea un hash de la contraseña usando SHA-256 y un salt
     * @param password Contraseña en texto plano
     * @param salt Salt para aumentar seguridad
     * @return Hash de la contraseña
     */
    private String hashPassword(String password, String salt) {
        String passwordWithSalt = password + salt;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(passwordWithSalt.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash de contraseña", e);
        }
    }
    
    /**
     * Verifica si la contraseña proporcionada coincide con la almacenada
     * @param passwordIntento Contraseña que se intenta verificar
     * @return true si la contraseña es correcta, false en caso contrario
     */
    public boolean verificarPassword(String passwordIntento) {
        String hashedPassword = hashPassword(passwordIntento, this.salt);
        return this.passwordHash.equals(hashedPassword);
    }
    
    /**
     * Cambia la contraseña del usuario
     * @param oldPassword Contraseña actual
     * @param newPassword Nueva contraseña
     * @return true si se cambió correctamente, false si la contraseña actual es incorrecta
     */
    public boolean cambiarPassword(String oldPassword, String newPassword) {
        if (verificarPassword(oldPassword)) {
            this.passwordHash = hashPassword(newPassword, this.salt);
            return true;
        }
        return false;
    }
    
    /**
     * Actualiza la fecha de último acceso al sistema
     */
    public void registrarAcceso() {
        this.ultimoAcceso = new java.util.Date();
    }
    
    // Getters y Setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public java.util.Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + 
               ", nombreCompleto=" + nombreCompleto + ", rol=" + rol + 
               ", activo=" + activo + "]";
    }
}