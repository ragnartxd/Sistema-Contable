package modelo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Usuario.Rol;
/**
 * Clase SistemaAutenticacion que gestiona la autenticación y autorización
 */
public class SistemaAutenticacion {
    private Map<String, Usuario> usuarios;
    private Usuario usuarioActual;
    private List<String> logAccesos;
    private boolean modoRegistro;  // Flag para permitir registro sin ser admin
    
    /**
     * Constructor
     */
    public SistemaAutenticacion() {
        this.usuarios = new HashMap<>();
        this.usuarioActual = null;
        this.logAccesos = new ArrayList<>();
        this.modoRegistro = true;  // Por defecto habilitamos el modo registro para usuarios nuevos
        inicializarUsuarioAdmin(); // Crear usuario administrador por defecto
    }
    
    /**
     * Inicializa un usuario administrador por defecto
     */
    private void inicializarUsuarioAdmin() {
        if (!usuarios.containsKey("admin")) {
            Usuario admin = new Usuario(
                1, 
                "admin", 
                "Administrador Sistema", 
                "admin@refrigas.com", 
                "admin123", 
                Rol.ADMINISTRADOR
            );
            usuarios.put("admin", admin);
        }
    }
    
    /**
     * Intenta autenticar a un usuario con sus credenciales
     * @param username Nombre de usuario
     * @param password Contraseña
     * @return true si la autenticación es exitosa, false en caso contrario
     */
    public boolean login(String username, String password) {
        Usuario usuario = usuarios.get(username);
        
        if (usuario != null && usuario.isActivo() && usuario.verificarPassword(password)) {
            usuarioActual = usuario;
            usuario.registrarAcceso();
            registrarAccesoLog(username, true, "Login exitoso");
            return true;
        }
        
        registrarAccesoLog(username, false, "Intento fallido de login");
        return false;
    }
    
    /**
     * Cierra la sesión del usuario actual
     */
    public void logout() {
        if (usuarioActual != null) {
            registrarAccesoLog(usuarioActual.getNombreUsuario(), true, "Logout");
            usuarioActual = null;
        }
    }
    
    /**
     * Verifica si hay un usuario con sesión activa
     * @return true si hay un usuario autenticado, false en caso contrario
     */
    public boolean estaAutenticado() {
        return usuarioActual != null;
    }
    
    /**
     * Verifica si el usuario actual tiene el rol especificado
     * @param rol Rol a verificar
     * @return true si el usuario tiene el rol, false en caso contrario
     */
    public boolean tieneRol(Rol rol) {
        return usuarioActual != null && usuarioActual.getRol() == rol;
    }
    
    /**
     * Verifica si el usuario actual es administrador
     * @return true si es administrador, false en caso contrario
     */
    public boolean esAdministrador() {
        return tieneRol(Rol.ADMINISTRADOR);
    }
    
    /**
     * Verifica si el usuario actual es cajero
     * @return true si es cajero, false en caso contrario
     */
    public boolean esCajero() {
        return tieneRol(Rol.CAJERO);
    }
    
    /**
     * Registra un intento de acceso en el log
     * @param username Nombre de usuario
     * @param exitoso Si el intento fue exitoso
     * @param detalle Detalle adicional
     */
    private void registrarAccesoLog(String username, boolean exitoso, String detalle) {
        String registro = new java.util.Date() + " | Usuario: " + username + 
                         " | Exitoso: " + exitoso + " | " + detalle;
        logAccesos.add(registro);
        // En una implementación real, este log se guardaría en un archivo o base de datos
    }
    
    /**
     * Crea un nuevo usuario en el sistema
     * @param nuevoUsuario Usuario a crear
     * @return true si se creó correctamente, false si ya existe el nombre de usuario
     */
    public boolean crearUsuario(Usuario nuevoUsuario) {
        // Si estamos en modo registro o el usuario actual es administrador
        if (modoRegistro || esAdministrador()) {
            if (usuarios.containsKey(nuevoUsuario.getNombreUsuario())) {
                return false;
            }
            
            usuarios.put(nuevoUsuario.getNombreUsuario(), nuevoUsuario);
            registrarAccesoLog(nuevoUsuario.getNombreUsuario(), true, "Usuario creado " + 
                              (estaAutenticado() ? "por " + usuarioActual.getNombreUsuario() : "por registro"));
            return true;
        }
        
        return false;
    }
    
    /**
     * Actualiza un usuario existente
     * @param usuario Usuario con los datos actualizados
     * @return true si se actualizó correctamente, false si no existe el usuario
     */
    public boolean actualizarUsuario(Usuario usuario) {
        // Solo administradores pueden actualizar usuarios
        if (!esAdministrador()) {
            return false;
        }
        
        if (!usuarios.containsKey(usuario.getNombreUsuario())) {
            return false;
        }
        
        usuarios.put(usuario.getNombreUsuario(), usuario);
        return true;
    }
    
    /**
     * Deshabilita un usuario (no lo elimina)
     * @param username Nombre del usuario a deshabilitar
     * @return true si se deshabilitó correctamente, false si no existe
     */
    public boolean deshabilitarUsuario(String username) {
        // Solo administradores pueden deshabilitar usuarios
        if (!esAdministrador()) {
            return false;
        }
        
        Usuario usuario = usuarios.get(username);
        if (usuario == null) {
            return false;
        }
        
        usuario.setActivo(false);
        return true;
    }
    
    /**
     * Obtiene una lista de todos los usuarios (solo para administradores)
     * @return Lista de usuarios o null si no tiene permisos
     */
    public List<Usuario> listarUsuarios() {
        if (!esAdministrador()) {
            return null;
        }
        
        return new ArrayList<>(usuarios.values());
    }
    
    /**
     * Obtiene el usuario actual
     * @return Usuario actual o null si no hay sesión activa
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Obtiene el log de accesos (solo para administradores)
     * @return Lista con el historial de accesos o null si no tiene permisos
     */
    public List<String> getLogAccesos() {
        if (!esAdministrador()) {
            return null;
        }
        
        return new ArrayList<>(logAccesos);
    }
    
    /**
     * Habilita o deshabilita el modo registro (permite crear usuarios sin ser admin)
     * @param habilitado true para permitir registro público, false para restringir
     */
    public void setModoRegistro(boolean habilitado) {
        this.modoRegistro = habilitado;
    }
    
    /**
     * Verifica si el modo registro está habilitado
     * @return true si el registro público está permitido
     */
    public boolean isModoRegistro() {
        return modoRegistro;
    }
    
    /**
     * Obtiene el próximo ID disponible para un nuevo usuario
     * @return ID único para el nuevo usuario
     */
    public int getProximoIdUsuario() {
        int maxId = 0;
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getId() > maxId) {
                maxId = usuario.getId();
            }
        }
        return maxId + 1;
    }
    
    /**
     * Verifica si existe un usuario con el nombre especificado
     * @param nombreUsuario Nombre de usuario a verificar
     * @return true si el usuario ya existe
     */
    public boolean existeUsuario(String nombreUsuario) {
        return usuarios.containsKey(nombreUsuario);
    }
}