package juan.proyectotienda.dao;

import juan.proyectotienda.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaDAOImpl extends AbstractDAOImpl implements CategoriaDAO {

    @Override
    public void create(Categoria categoria) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO categoria (nombre) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            int idx = 1;
            ps.setString(idx++, categoria.getNombre());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("INSERT de categoria con 0 filas insertadas.");
            }

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) {
                categoria.setIdCategoria(rsGenKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rsGenKeys);
        }
    }

    @Override
    public List<Categoria> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List<Categoria> lista = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM categoria");

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("idCategoria"));
                c.setNombre(rs.getString("nombre"));
                lista.add(c);
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
    public Optional<Categoria> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT * FROM categoria WHERE idCategoria = ?");
            int idx = 1;
            ps.setInt(idx++, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Categoria c = new Categoria();
                idx =1;
                c.setIdCategoria(rs.getInt(idx++));
                c.setNombre(rs.getString(idx++));
                return Optional.of(c);
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
    public void update(Categoria categoria) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connectDB();
            ps = conn.prepareStatement("UPDATE categoria SET nombre = ? WHERE idCategoria = ?");
            int idx = 1;
            ps.setString(idx++, categoria.getNombre());
            ps.setInt(idx, categoria.getIdCategoria());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("Update de categoria con 0 registros actualizadas.");
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
            ps = conn.prepareStatement("DELETE FROM categoria WHERE idCategoria = ?");
            int idx = 1;
            ps.setInt(idx, id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Delete de categoria con 0 registros eliminadas.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }
}
