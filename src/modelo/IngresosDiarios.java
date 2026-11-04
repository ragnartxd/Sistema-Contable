package modelo;
import java.util.Date;

public class IngresosDiarios {
    private Date fecha;
    private Gasto gasto; // Relación 1:1 con Gasto
    private double montoFacturado;
    private double montoNoFacturado;
    
    /**
         * Constructor por defecto
         */
        public IngresosDiarios() {
            this.fecha = new Date();
            this.montoFacturado = 0.0;
            this.montoNoFacturado = 0.0;
        }
    
        /**
         * Constructor con parámetros
         * @param montoFacturado Monto facturado
         * @param montoNoFacturado Monto no facturado
         */
        public IngresosDiarios(double montoFacturado, double montoNoFacturado) {
            this.fecha = new Date();
            this.montoFacturado = montoFacturado;
            this.montoNoFacturado = montoNoFacturado;
        }
        
        /**
         * Constructor con parámetros
         * @param gasto Gasto asociado al ingreso
         */
        public IngresosDiarios(Gasto gasto) {
            this();
            this.gasto = gasto;
        }
    
    /**
     * Método para registrar un ingreso
     */
    public void registrar() {
        // Lógica para registrar el ingreso
        System.out.println("Ingreso registrado el " + fecha + " con monto " + gasto.getMonto());
    }
    
    // Getters y Setters
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }
    
    @Override
    public String toString() {
        return "IngresosDiarios [fecha=" + fecha + ", gasto=" + gasto + "]";
    }
    public double getMontoFacturado() {
        return montoFacturado;
    }

    public double getMontoNoFacturado() {
        return montoNoFacturado;
    }

    public double calcularTotalIngresos() {
        return montoFacturado + montoNoFacturado;
    }
}