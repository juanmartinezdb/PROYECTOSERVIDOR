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

    }

    @Override
    public List<Producto> getAll() {
        return List.of();
    }

    @Override
    public Optional<Producto> find(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Producto producto) {

    }

    @Override
    public void delete(int id) {

    }
}
