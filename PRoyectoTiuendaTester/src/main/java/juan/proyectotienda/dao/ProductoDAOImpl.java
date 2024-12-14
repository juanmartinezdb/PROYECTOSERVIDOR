package juan.proyectotienda.dao;

import juan.proyectotienda.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAOImpl extends AbstractDAOImpl implements ProductoDAO {

    @Override
    public void create(Producto producto) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO producto (nombre, descripcion, precio, imagen, idCategoria) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setString(idx++, producto.getNombre());
            ps.setString(idx++, producto.getDescripcion());
            ps.setBigDecimal(idx++, producto.getPrecio());
            ps.setString(idx++, producto.getImagen());
            ps.setInt(idx++, producto.getIdCategoria());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("INSERT de producto con 0 filas afectadas.");
            }

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) {
                producto.setIdProducto(rsGenKeys.getInt(1));
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
    public List<Producto> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List<Producto> lista = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM producto");

            while (rs.next()) {
                Producto p = new Producto();
                int idx = 1;
                p.setIdProducto(rs.getInt(idx++));
                p.setNombre(rs.getString(idx++));
                p.setDescripcion(rs.getString(idx++));
                p.setPrecio(rs.getBigDecimal(idx++));
                p.setImagen(rs.getString(idx++));
                p.setIdCategoria(rs.getInt(idx));
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
    public Optional<Producto> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT * FROM producto WHERE idProducto = ?");
            int idx = 1;
            ps.setInt(idx, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Producto p = new Producto();
                idx = 1;
                p.setIdProducto(rs.getInt(idx++));
                p.setNombre(rs.getString(idx++));
                p.setDescripcion(rs.getString(idx++));
                p.setPrecio(rs.getBigDecimal(idx++));
                p.setImagen(rs.getString(idx++));
                p.setIdCategoria(rs.getInt(idx++));
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
    public void update(Producto producto) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connectDB();
            ps = conn.prepareStatement("UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, imagen = ?, idCategoria = ? WHERE idProducto = ?");
            int idx = 1;
            ps.setString(idx++, producto.getNombre());
            ps.setString(idx++, producto.getDescripcion());
            ps.setBigDecimal(idx++, producto.getPrecio());
            ps.setString(idx++, producto.getImagen());
            ps.setInt(idx++, producto.getIdCategoria());
            ps.setInt(idx++, producto.getIdProducto());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Update de producto con 0 registros actualizadas.");
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
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("DELETE FROM producto WHERE idProducto = ?");
            int idx = 1;
            ps.setInt(idx, id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Delete de producto con 0 registros eliminadas.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }
}
