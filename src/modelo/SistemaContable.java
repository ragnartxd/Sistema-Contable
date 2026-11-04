package modelo;

import java.util.ArrayList;
import java.util.List;

public class SistemaContable {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Caja> cajas = new ArrayList<>();

    public void agregarCliente(Cliente c) {
        clientes.add(c);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void agregarCaja(Caja c) {
        cajas.add(c);
    }

    public List<Caja> getCajas() {
        return cajas;
    }

    public Cliente buscarClientePorNombre(String nombre) {
        return clientes.stream()
                       .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                       .findFirst()
                       .orElse(null);
    }
}
