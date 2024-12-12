package juan.proyectotienda.dao;

import juan.proyectotienda.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    public void create(Usuario usuario);
    public List<Usuario> getAll();
    public Optional<Usuario> find(int id);
    public void update(Usuario usuario);
    public void delete(int id);

    public Optional<Usuario> getByPassword(String password, String usuario);

}
