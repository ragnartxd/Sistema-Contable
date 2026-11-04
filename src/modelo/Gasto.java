package modelo;
import java.util.Date;


public class Gasto {
    private Date fecha;
    private double monto;
    private boolean facturado; // Campo adicional para determinar si es facturado o no
    
    // Relaciones
    private Cliente cliente; // Relación con cliente (podría ser null)
    
    /**
     * Constructor por defecto
     */
    public Gasto() {
        this.fecha = new Date();
        this.monto = 0.0;
        this.facturado = false;
    }
    
    /**
     * Constructor con parámetros
     * @param monto Monto del gasto
     * @param facturado Indica si el gasto está facturado
     */
    public Gasto(double monto, boolean facturado) {
        this();
        this.monto = monto;
        this.facturado = facturado;
    }
    
    /**
     * Constructor completo
     * @param monto Monto del gasto
     * @param facturado Indica si el gasto está facturado
     * @param cliente Cliente asociado al gasto
     */
    public Gasto(double monto, boolean facturado, Cliente cliente) {
        this(monto, facturado);
        this.cliente = cliente;
    }
    
    /**
     * Método para registrar un gasto
     */
    public void registrar() {
        // Lógica para registrar el gasto
        String tipoGasto = facturado ? "facturado" : "no facturado";
        System.out.println("Gasto " + tipoGasto + " registrado el " + fecha + " con monto " + monto);
        if (cliente != null) {
            System.out.println("Cliente asociado: " + cliente.getNombre());
        }
    }
    
    // Getters y Setters
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public boolean esFacturado() {
        return facturado;
    }
    
    public void setFacturado(boolean facturado) {
        this.facturado = facturado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public String toString() {
        return "Gasto [fecha=" + fecha + ", monto=" + monto + ", facturado=" + facturado + ", cliente=" + 
                (cliente != null ? cliente.getNombre() : "N/A") + "]";
    }
}

