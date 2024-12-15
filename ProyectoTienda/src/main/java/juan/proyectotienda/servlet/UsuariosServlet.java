package juan.proyectotienda.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import juan.proyectotienda.dao.UsuarioDAO;
import juan.proyectotienda.dao.UsuarioDAOImpl;
import juan.proyectotienda.model.Usuario;
import juan.proyectotienda.utilities.Util;


@WebServlet(name = "usuariosServlet", value = "/back/usuarios/*")
public class UsuariosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;

        if (pathInfo == null || "/".equals(pathInfo)) {

            List<Usuario> lista = usuarioDAO.getAll();
            request.setAttribute("listaUsuarios", lista);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/usuarios.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/crear-usuario.jsp");

            } else if (pathParts.length == 2) {
//  /usuarios/{id}
                try {
                        request.setAttribute("usuario", usuarioDAO.find(Integer.parseInt(pathParts[1])));
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/detalle-usuario.jsp");

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/usuarios.jsp");
                }

            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                // /usuarios/editar/{id}
                try {
                        request.setAttribute("usuario", usuarioDAO.find(Integer.parseInt(pathParts[2])));
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/editar-usuario.jsp");

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/usuarios.jsp");
                }

            } else {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/usuarios.jsp");
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = null;
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // Crear
            Usuario u = new Usuario();
            String nombre = request.getParameter("nombre");
            u.setNombre(nombre);

            Optional<Usuario> existe = usuarioDAO.getByNombre(nombre);

            if (existe.isPresent()) {
                request.setAttribute("existe", true);
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/crear-usuario.jsp");
            } else {
                try {
                    u.setPassword(Util.hashPassword(request.getParameter("password")));
                    u.setRol(request.getParameter("rol"));
                    usuarioDAO.create(u);

                    //para que me cargue los usuarios tengo que llamar la lista aqui, si no sale vacio
                    List<Usuario> lista = usuarioDAO.getAll();
                    request.setAttribute("listaUsuarios", lista);
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/usuarios.jsp");

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/crear-usuario.jsp");
                }
            }

        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);

            //para que me cargue los usuarios tengo que llamar la lista aqui, si no sale vacio
            List<Usuario> lista = usuarioDAO.getAll();
            request.setAttribute("listaUsuarios", lista);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/usuarios.jsp");

        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);

            //para que me cargue los usuarios tengo que llamar la lista aqui, si no sale vacio
            List<Usuario> lista = usuarioDAO.getAll();
            request.setAttribute("listaUsuarios", lista);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/usuarios.jsp");
        }

        dispatcher.forward(request, response);
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
            Usuario u = new Usuario();
        try {

            u.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
            u.setNombre(request.getParameter("nombre"));
            u.setRol(request.getParameter("rol"));
            u.setPassword(Util.hashPassword(request.getParameter("password")));
            usuarioDAO.update(u);

        } catch (NumberFormatException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {

            usuarioDAO.delete(Integer.parseInt(request.getParameter("idUsuario")));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
