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

    }

    @Override
    public List<Categoria> getAll() {
        return List.of();
    }

    @Override
    public Optional<Categoria> find(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Categoria categoria) {

    }

    @Override
    public void delete(int id) {

    }
}
