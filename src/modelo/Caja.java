package modelo;

public class Caja {
    private IngresosDiarios ingresos;
    private Gasto gasto;
    private PagoDatafono pagoDatafono;

    public Caja(IngresosDiarios ingresos, Gasto gasto, PagoDatafono pagoDatafono) {
        this.ingresos = ingresos;
        this.gasto = gasto;
        this.pagoDatafono = pagoDatafono;
    }

    public double calcularTotalIngresos() {
        return ingresos.getMontoFacturado() + ingresos.getMontoNoFacturado();
    }

    public double calcularTotalEgresos() {
        return gasto.getMonto() + pagoDatafono.getMonto();
    }

    public double calcularDiferenciaCaja() {
        return calcularTotalIngresos() - calcularTotalEgresos();
    }

    public String resumenCaja() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ingresos totales: $").append(calcularTotalIngresos()).append("\n");
        sb.append("Egresos totales:   $").append(calcularTotalEgresos()).append("\n");
        sb.append("Diferencia:        $").append(calcularDiferenciaCaja());
        return sb.toString();
    }
}
