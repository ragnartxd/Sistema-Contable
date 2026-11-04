package modelo;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Deuda {
    private Date fecha;
    private double montoPendiente;
    private String periodicidad;
    private Date fechaProximoAbono;
    
    /**
     * Constructor por defecto
     */
    public Deuda() {
        this.fecha = new Date();
        this.montoPendiente = 0.0;
        this.periodicidad = "mensual";
        // Calcular fecha del próximo abono sin usar fechaProximoAbono
        this.fechaProximoAbono = calcularFechaProximoAbono();
    }
    
    /**
     * Constructor con parámetros
     * @param montoPendiente Monto pendiente de la deuda
     * @param periodicidad Periodicidad de los pagos (diario, semanal, mensual, etc)
     */
    public Deuda(double montoPendiente, String periodicidad) {
        this.fecha = new Date();
        this.montoPendiente = montoPendiente;
        this.periodicidad = periodicidad;
        // Calcular fecha del próximo abono sin usar fechaProximoAbono
        this.fechaProximoAbono = calcularFechaProximoAbono();
    }
    
    /**
     * Método para programar los abonos a la deuda
     */
    public void programarAbonos() {
        // Lógica para programar los abonos
        System.out.println("Abonos programados con periodicidad " + periodicidad);
        System.out.println("Próximo abono: " + fechaProximoAbono);
    }
    
    /**
     * Calcula la fecha del próximo abono según la periodicidad
     * @return Fecha del próximo abono
     */
    private Date calcularFechaProximoAbono() {
        // Usamos la fecha actual como base para el cálculo
        // en lugar de fechaProximoAbono que podría ser null
        Date fechaBase = this.fecha;
        if (fechaBase == null) {
            fechaBase = new Date(); // Por seguridad
        }
        
        // 1) Convertimos el Date actual al LocalDate de la zona del sistema
        Instant instantActual = fechaBase.toInstant();
        LocalDate fechaLocal = instantActual
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

        // 2) Calculamos la siguiente fecha según periodicidad
        LocalDate siguiente;
        
        // Verificación de periodicidad para evitar NullPointerException
        String periodoLower = (periodicidad != null) ? periodicidad.toLowerCase() : "mensual";
        
        switch (periodoLower) {
            case "diario":
                siguiente = fechaLocal.plusDays(1);
                break;
            case "semanal":
                siguiente = fechaLocal.plusWeeks(1);
                break;
            case "quincenal":
                siguiente = fechaLocal.plusDays(15);
                break;
            case "mensual":
            default:
                siguiente = fechaLocal.plusMonths(1);
                break;
        }

        // 3) Convertimos el LocalDate resultante de vuelta a Date
        return Date.from(
            siguiente.atStartOfDay(ZoneId.systemDefault())
                     .toInstant()
        );
    }
    
    // Getters y Setters
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(double montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
        this.fechaProximoAbono = calcularFechaProximoAbono();
    }

    public Date getFechaProximoAbono() {
        return fechaProximoAbono;
    }

    public void setFechaProximoAbono(Date fechaProximoAbono) {
        this.fechaProximoAbono = fechaProximoAbono;
    }
    
    @Override
    public String toString() {
        return "Deuda [fecha=" + fecha + ", montoPendiente=" + montoPendiente + 
                ", periodicidad=" + periodicidad + ", fechaProximoAbono=" + fechaProximoAbono + "]";
    }
}