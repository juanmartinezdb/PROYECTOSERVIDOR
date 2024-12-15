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
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO pedido (idCliente, fecha, total) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            int idx = 1;
            ps.setInt(idx++, pedido.getIdCliente());
            ps.setDate(idx++, Date.valueOf(pedido.getFecha()));
            ps.setBigDecimal(idx++, pedido.getTotal());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("INSERT de pedido con 0 filas insertadas.");
            }

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) {
                pedido.setIdPedido(rsGenKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
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
                int idx = 1;
                p.setIdPedido(rs.getInt(idx++));
                p.setIdCliente(rs.getInt(idx++));
                p.setFecha(rs.getDate(idx++).toLocalDate());
                p.setTotal(rs.getBigDecimal(idx++));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
            int idx = 1;
            ps.setInt(idx, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Pedido p = new Pedido();
                idx = 1;
                p.setIdPedido(rs.getInt(idx++));
                p.setIdCliente(rs.getInt(idx++));
                p.setFecha(rs.getDate(idx++).toLocalDate());
                p.setTotal(rs.getBigDecimal(idx++));
                return Optional.of(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        ResultSet rs = null;
        try {
            conn = connectDB();
            ps = conn.prepareStatement("UPDATE pedido SET idCliente = ?, fecha = ?, total = ? WHERE idPedido = ?");
            int idx = 1;
            ps.setInt(idx++, pedido.getIdCliente());
            ps.setDate(idx++, Date.valueOf(pedido.getFecha()));
            ps.setBigDecimal(idx++, pedido.getTotal());
            ps.setInt(idx, pedido.getIdPedido());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("Update de pedido con 0 registros actualizados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null; //para el PEDIDO
        PreparedStatement psArticulos = null; //para los ARTICULOS

        try {
            conn = connectDB();
//ARTICULOS (PRIMERO QUE SON LOS QUE DAN EL FALLO)
            psArticulos = conn.prepareStatement("DELETE FROM articulos_pedido WHERE idPedido = ?");
            int idx = 1;
            psArticulos.setInt(idx, id);
            int rows = psArticulos.executeUpdate();
            if (rows == 0) {
                System.out.println("Delete de articulos con 0 registros eliminados.");
            }
            //EL PEDIDO (ULTIMO)
            ps = conn.prepareStatement("DELETE FROM pedido WHERE idPedido = ?");
            idx = 1;
            ps.setInt(idx, id);
            rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("Delete de pedido con 0 registros eliminados.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, psArticulos, null);
            closeDb(conn, ps, null);
        }
    }


    @Override
    public void createConArticulos(Pedido pedido, List<Producto> productos, List<Integer> cantidades) {
        // Llamamos al crear pedido de base normal
        create(pedido);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO articulos_pedido (idPedido, idProducto, cantidad) VALUES (?, ?, ?)");
            int idx = 1;
            for (int i = 0; i < productos.size(); i++) {
                idx=1;
                ps.setInt(idx++, pedido.getIdPedido());
                ps.setInt(idx++, productos.get(i).getIdProducto());
                ps.setInt(idx, cantidades.get(i));
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }


    @Override
    public List<Pedido> getPedidosByCliente(int idCliente) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pedido> pedidos = new ArrayList<>();

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT * FROM pedido WHERE idCliente = ?");
            int idx = 1;
            ps.setInt(idx, idCliente);
            rs = ps.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                idx = 1;

                pedido.setIdPedido(rs.getInt(idx++));
                pedido.setIdCliente(rs.getInt(idx++));
                pedido.setFecha(rs.getDate(idx++).toLocalDate());
                pedido.setTotal(rs.getBigDecimal(idx++));
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return pedidos;
    }

    @Override
    public List<Producto> getProductosDePedido(int idPedido) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Producto> lista = new ArrayList<>();

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT p.idProducto, p.nombre, p.descripcion, p.precio, p.imagen, p.idCategoria FROM articulos_pedido ap JOIN producto p ON ap.idProducto = p.idProducto WHERE ap.idPedido = ?");
            int idx = 1;
            ps.setInt(idx, idPedido);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto pr = new Producto();
                idx =1;
                pr.setIdProducto(rs.getInt(idx++));
                pr.setNombre(rs.getString(idx++));
                pr.setDescripcion(rs.getString(idx++));
                pr.setPrecio(rs.getBigDecimal(idx++));
                pr.setImagen(rs.getString(idx++));
                pr.setIdCategoria(rs.getInt(idx++));
                lista.add(pr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return lista;
    }

    @Override
    public List<Integer> getCantidadesDePedido(int idPedido) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> cantidades = new ArrayList<>();

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT cantidad FROM articulos_pedido WHERE idPedido = ?");
            int idx = 1;
            ps.setInt(idx, idPedido);
            rs = ps.executeQuery();
            while (rs.next()) {
                idx =1;
                cantidades.add(rs.getInt(idx));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return cantidades;
    }

}
