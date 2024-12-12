package juan.proyectotienda.dao;

import juan.proyectotienda.model.Pedido;
import juan.proyectotienda.model.Producto;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO {
    void create(Pedido pedido);
    List<Pedido> getAll();
    Optional<Pedido> find(int id);
    void update(Pedido pedido);
    void delete(int id);

    // Método para crear pedido con sus artículos
    void createWithItems(Pedido pedido, List<Producto> productos, List<Integer> cantidades);

    // Obtener productos (y cantidades) de un pedido
    List<Producto> getProductosDePedido(int idPedido);
    List<Integer> getCantidadesDePedido(int idPedido);
}
