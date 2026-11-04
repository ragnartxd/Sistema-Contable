package modelo;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int id;
    private String nombre;
    private String email;
    
    // Relaciones
    private List<Deuda> deudas;
    
    /**
     * Constructor por defecto
     */
    public Cliente() {
        this.id = 0;
        this.nombre = "";
        this.email = "";
        this.deudas = new ArrayList<>();
    }
    
    /**
     * Constructor con parámetros
     * @param id Identificador del cliente
     * @param nombre Nombre del cliente
     * @param email Email del cliente
     */
    public Cliente(int id, String nombre, String email) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }
    
    /**
     * Método para consultar las deudas del cliente
     * @return Lista de deudas del cliente
     */
    public List<Deuda> consultarDeudas() {
        return this.deudas;
    }
    
    /**
     * Método para añadir una deuda al cliente
     * @param deuda Deuda a añadir
     */
    public void agregarDeuda(Deuda deuda) {
        this.deudas.add(deuda);
        System.out.println("Deuda agregada al cliente " + nombre);
    }
    
    // Getters y Setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Deuda> getDeudas() {
        return deudas;
    }

    public void setDeudas(List<Deuda> deudas) {
        this.deudas = deudas;
    }
    
    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + ", email=" + email + 
                ", totalDeudas=" + deudas.size() + "]";
    }
    
     public double calcularTotalPendiente() {
        return deudas.stream()
                     .mapToDouble(Deuda::getMontoPendiente)
                     .sum();
    }
}