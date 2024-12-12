package juan.proyectotienda.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private Map<Integer, Integer> articulos; //idProducto, cantidad
    private LocalDate fecha;
    private BigDecimal total;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Map<Integer, Integer> getArticulos() {
        return articulos;
    }

    public void setArticulos(Map<Integer, Integer> articulos) {
        this.articulos = articulos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void agregarArticulo (int idProducto, int cantidad){
        articulos.put(idProducto, cantidad);
    }
}
