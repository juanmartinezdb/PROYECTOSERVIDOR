package juan.proyectotienda.servlet;

import juan.proyectotienda.dao.CategoriaDAO;
import juan.proyectotienda.dao.CategoriaDAOImpl;
import juan.proyectotienda.model.Categoria;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "categoriasServlet", value = "/back/categorias/*")
public class CategoriasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;

        if (pathInfo == null || "/".equals(pathInfo)) {
            // Listado
            List<Categoria> lista = categoriaDAO.getAll();
            request.setAttribute("listaCategorias", lista);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/categorias.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] parts = pathInfo.split("/");

            if (parts.length == 2 && "crear".equals(parts[1])) {
                // Formulario crear
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/crear-categoria.jsp");

            } else if (parts.length == 2) {
                // Detalle
                try {
                    int id = Integer.parseInt(parts[1]);
                    Optional<Categoria> cat = categoriaDAO.find(id);
                    if (cat.isPresent()) {
                        request.setAttribute("categoria", cat.get());
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/detalle-categoria.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/back/categorias");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/back/categorias");
                    return;
                }

            } else if (parts.length == 3 && "editar".equals(parts[1])) {
                // Formulario editar
                try {
                    int id = Integer.parseInt(parts[2]);
                    Optional<Categoria> cat = categoriaDAO.find(id);
                    if (cat.isPresent()) {
                        request.setAttribute("categoria", cat.get());
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/editar-categoria.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/back/categorias");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/back/categorias");
                    return;
                }

            } else {
                response.sendRedirect(request.getContextPath() + "/back/categorias");
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
            Categoria c = new Categoria();
            c.setNombre(request.getParameter("nombre"));
            categoriaDAO.create(c);

        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        }

        response.sendRedirect(request.getContextPath() + "/back/categorias");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("idCategoria"));
            String nombre = request.getParameter("nombre");

            Categoria c = new Categoria();
            c.setIdCategoria(id);
            c.setNombre(nombre);
            categoriaDAO.update(c);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("idCategoria"));
            categoriaDAO.delete(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
