package juan.proyectotienda.dao;

import juan.proyectotienda.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO {
    @Override
    public void create(Usuario usuario) {

    }

    @Override
    public List<Usuario> getAll() {
        return List.of();
    }

    @Override
    public Optional<Usuario> find(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Usuario usuario) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Optional<Usuario> getByPassword(String password, String usuario) {
        return Optional.empty();
    }
}
