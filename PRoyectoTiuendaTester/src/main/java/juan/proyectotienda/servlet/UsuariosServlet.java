package juan.proyectotienda.servlet;

import juan.proyectotienda.dao.UsuarioDAO;
import juan.proyectotienda.dao.UsuarioDAOImpl;
import juan.proyectotienda.model.Usuario;
import juan.proyectotienda.utilities.Util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "usuariosServlet", value = "/back/usuarios/*")
public class UsuariosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;

        if (pathInfo == null || "/".equals(pathInfo)) {
            // Listado
            List<Usuario> lista = usuarioDAO.getAll();
            request.setAttribute("listaUsuarios", lista);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/usuarios.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] parts = pathInfo.split("/");

            if (parts.length == 2 && "crear".equals(parts[1])) {
                // Form crear
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/crear-usuario.jsp");

            } else if (parts.length == 2) {
                // Detalle
                try {
                    int id = Integer.parseInt(parts[1]);
                    Optional<Usuario> u = usuarioDAO.find(id);
                    if (u.isPresent()) {
                        request.setAttribute("usuario", u.get());
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/detalle-usuario.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/back/usuarios");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/back/usuarios");
                    return;
                }

            } else if (parts.length == 3 && "editar".equals(parts[1])) {
                // Form editar
                try {
                    int id = Integer.parseInt(parts[2]);
                    Optional<Usuario> u = usuarioDAO.find(id);
                    if (u.isPresent()) {
                        request.setAttribute("usuario", u.get());
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/usuarios/editar-usuario.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/back/usuarios");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/back/usuarios");
                    return;
                }

            } else {
                response.sendRedirect(request.getContextPath() + "/back/usuarios");
                return;
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // Crear
            Usuario u = new Usuario();
            u.setNombre(request.getParameter("nombre"));
            try {
                u.setPassword(Util.hashPassword(request.getParameter("password")));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            u.setRol(request.getParameter("rol"));
            usuarioDAO.create(u);

        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        }

        response.sendRedirect(request.getContextPath() + "/back/usuarios");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario u = new Usuario();
            u.setIdUsuario(id);
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
            int id = Integer.parseInt(request.getParameter("idUsuario"));
            usuarioDAO.delete(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
