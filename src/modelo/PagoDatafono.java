package modelo;
import java.util.Date;

public class PagoDatafono {
    private Date fecha;
    private double monto;
    
    // Relaciones
    private Deuda deuda; // Relación con la deuda (podría ser null)
    
    /**
     * Constructor por defecto
     */
    public PagoDatafono() {
        this.fecha = new Date();
        this.monto = 0.0;
    }
    
    /**
     * Constructor con parámetros
     * @param monto Monto del pago
     */
    public PagoDatafono(double monto) {
        this();
        this.monto = monto;
    }
    
    /**
     * Constructor completo
     * @param monto Monto del pago
     * @param deuda Deuda asociada al pago
     */
    public PagoDatafono(double monto, Deuda deuda) {
        this(monto);
        this.deuda = deuda;
    }
    
    /**
     * Método para registrar un pago
     */
    public void registrar() {
        // Lógica para registrar el pago
        System.out.println("Pago con datafono registrado el " + fecha + " con monto " + monto);
        if (deuda != null) {
            // Actualizar la deuda asociada
            deuda.setMontoPendiente(deuda.getMontoPendiente() - monto);
            System.out.println("Deuda actualizada. Monto pendiente: " + deuda.getMontoPendiente());
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

    public Deuda getDeuda() {
        return deuda;
    }

    public void setDeuda(Deuda deuda) {
        this.deuda = deuda;
    }
    
    @Override
    public String toString() {
        return "PagoDatafono [fecha=" + fecha + ", monto=" + monto + ", deuda=" + 
                (deuda != null ? "Sí" : "No") + "]";
    }
}
