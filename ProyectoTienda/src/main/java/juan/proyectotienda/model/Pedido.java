package juan.proyectotienda.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private Map<Integer, Integer> articulos; //idProducto, cantidad
    private Date fecha;
    private double total;

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void agregarArticulo (int idProducto, int cantidad){
        articulos.put(idProducto, cantidad);
    }
}
