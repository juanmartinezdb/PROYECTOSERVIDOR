package juan.proyectotienda.dao;

import juan.proyectotienda.model.Pedido;
import juan.proyectotienda.model.Producto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoDAOImpl extends AbstractDAOImpl implements PedidoDAO {

    @Override
    public void create(Pedido pedido) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO pedido (idCliente, fecha, total) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pedido.getIdCliente());
            ps.setDate(2, Date.valueOf(pedido.getFecha()));
            ps.setBigDecimal(3, pedido.getTotal());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("INSERT de pedido sin filas afectadas.");
            }

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) {
                pedido.setIdPedido(rsGenKeys.getInt(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rsGenKeys);
        }
    }

    @Override
    public List<Pedido> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List<Pedido> lista = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM pedido");

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setIdCliente(rs.getInt("idCliente"));
                p.setFecha(rs.getDate("fecha").toLocalDate());
                p.setTotal(rs.getBigDecimal("total"));
                lista.add(p);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }

        return lista;
    }

    @Override
    public Optional<Pedido> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT * FROM pedido WHERE idPedido = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setIdCliente(rs.getInt("idCliente"));
                p.setFecha(rs.getDate("fecha").toLocalDate());
                p.setTotal(rs.getBigDecimal("total"));
                return Optional.of(p);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return Optional.empty();
    }

    @Override
    public void update(Pedido pedido) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectDB();
            ps = conn.prepareStatement("UPDATE pedido SET idCliente = ?, fecha = ?, total = ? WHERE idPedido = ?");
            ps.setInt(1, pedido.getIdCliente());
            ps.setDate(2, Date.valueOf(pedido.getFecha()));
            ps.setBigDecimal(3, pedido.getTotal());
            ps.setInt(4, pedido.getIdPedido());

            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, null);
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("DELETE FROM pedido WHERE idPedido = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, null);
        }
    }

    @Override
    public void createWithItems(Pedido pedido, List<Producto> productos, List<Integer> cantidades) {
        // Primero creamos el pedido
        create(pedido);

        // Luego insertamos las líneas en articulos_pedido
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO articulos_pedido (idPedido, idProducto, cantidad) VALUES (?, ?, ?)");

            for (int i = 0; i < productos.size(); i++) {
                ps.setInt(1, pedido.getIdPedido());
                ps.setInt(2, productos.get(i).getIdProducto());
                ps.setInt(3, cantidades.get(i));
                ps.executeUpdate();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, null);
        }
    }



    @Override
    public List<Producto> getProductosDePedido(int idPedido) {
        List<Producto> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT p.idProducto, p.nombre, p.descripcion, p.precio, p.imagen, p.idCategoria FROM articulos_pedido ap JOIN producto p ON ap.idProducto = p.idProducto WHERE ap.idPedido = ?");
            ps.setInt(1, idPedido);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto pr = new Producto();
                pr.setIdProducto(rs.getInt("idProducto"));
                pr.setNombre(rs.getString("nombre"));
                pr.setDescripcion(rs.getString("descripcion"));
                pr.setPrecio(rs.getBigDecimal("precio"));
                pr.setImagen(rs.getString("imagen"));
                pr.setIdCategoria(rs.getInt("idCategoria"));
                lista.add(pr);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return lista;
    }

    @Override
    public List<Integer> getCantidadesDePedido(int idPedido) {
        List<Integer> cantidades = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT cantidad FROM articulos_pedido WHERE idPedido = ?");
            ps.setInt(1, idPedido);
            rs = ps.executeQuery();
            while (rs.next()) {
                cantidades.add(rs.getInt("cantidad"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return cantidades;
    }
}
