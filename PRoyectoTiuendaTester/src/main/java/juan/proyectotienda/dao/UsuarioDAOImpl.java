package juan.proyectotienda.dao;

import juan.proyectotienda.model.Pedido;
import juan.proyectotienda.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO {

    @Override
    public void create(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO usuario (nombre, password, rol) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            int idx =1;
            ps.setString(idx++, usuario.getNombre());
            ps.setString(idx++, usuario.getPassword());
            ps.setString(idx++, usuario.getRol());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("INSERT de usuario con 0 filas afectadas.");
            }

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) {
                usuario.setIdUsuario(rsGenKeys.getInt(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public List<Usuario> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List<Usuario> lista = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM usuario");

            while (rs.next()) {
                Usuario u = new Usuario();
                int idx = 1;
                u.setIdUsuario(rs.getInt(idx++));
                u.setNombre(rs.getString(idx++));
                u.setPassword(rs.getString(idx++));
                u.setRol(rs.getString(idx++));
                lista.add(u);
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
    public Optional<Usuario> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT * FROM usuario WHERE idUsuario = ?");
            int idx = 1;
            ps.setInt(idx++, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                idx = 1;
                u.setIdUsuario(rs.getInt(idx++));
                u.setNombre(rs.getString(idx++));
                u.setPassword(rs.getString(idx++));
                u.setRol(rs.getString(idx++));
                return Optional.of(u);
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
    public void update(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("UPDATE usuario SET nombre = ?, password = ?, rol = ? WHERE idUsuario = ?");
            int idx = 1;
            ps.setString(idx++, usuario.getNombre());
            ps.setString(idx++, usuario.getPassword());
            ps.setString(idx++, usuario.getRol());
            ps.setInt(idx++, usuario.getIdUsuario());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Update de usuario con 0 registros actualizadas.");
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
    PedidoDAO pdao = new PedidoDAOImpl();
        try {
            conn = connectDB();

            //BORRADO DE LOS PEDIDOS
            List<Pedido> lista = pdao.getPedidosByCliente(id);
            for (Pedido p : lista) {
                pdao.delete(p.getIdPedido());
            }

            //BORRADO DE CLIENTE
            ps = conn.prepareStatement("DELETE FROM usuario WHERE idUsuario = ?");
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Delete de usuario con 0 filas eliminadas.");
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
    public Optional<Usuario> getByPassword(String password, String usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT * FROM usuario WHERE password = ? AND nombre = ?");
            int idx = 1;
            ps.setString(idx++, password);
            ps.setString(idx, usuario);

            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                idx = 1;
                u.setIdUsuario(rs.getInt(idx++));
                u.setNombre(rs.getString(idx++));
                u.setPassword(rs.getString(idx++));
                u.setRol(rs.getString(idx++));
                return Optional.of(u);
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
    public Optional<Usuario> getByNombre(String usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT * FROM usuario WHERE nombre = ?");
            int idx = 1;
            ps.setString(idx, usuario);

            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                idx = 1;
                u.setIdUsuario(rs.getInt(idx++));
                u.setNombre(rs.getString(idx++));
                u.setPassword(rs.getString(idx++));
                u.setRol(rs.getString(idx++));
                return Optional.of(u);
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
}
