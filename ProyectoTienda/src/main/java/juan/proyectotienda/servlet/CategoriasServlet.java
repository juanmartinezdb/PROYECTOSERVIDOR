package juan.proyectotienda.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import juan.proyectotienda.dao.CategoriaDAO;
import juan.proyectotienda.dao.CategoriaDAOImpl;
import juan.proyectotienda.model.Categoria;

@WebServlet(name = "categoriasServlet", value = "/back/categorias/*")
public class CategoriasServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
            List<Categoria> lista = categoriaDAO.getAll();
            request.setAttribute("listaCategorias", lista);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/categorias.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
                CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
                request.setAttribute("listaCategorias", categoriaDAO.getAll());
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/crear-categoria.jsp");

            } else if (pathParts.length == 2) {
                //  /categorias/{id}
                CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
                try {
                    request.setAttribute("categoria", categoriaDAO.find(Integer.parseInt(pathParts[1])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/detalle-categoria.jsp");

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/categorias.jsp");
                }

            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
                // /categorias/editar/{id}
                try {
                    request.setAttribute("categoria", categoriaDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/editar-categoria.jsp");

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/categorias.jsp");
                }

            } else {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/categorias/categorias.jsp");
            }
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // Crear
            CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
            Categoria c = new Categoria();
            c.setNombre(request.getParameter("nombre"));
            categoriaDAO.create(c);
// Actualizar
        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
        //Borrar
        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        } else {
            System.out.println("OPCION POST no soportado");
        }

        response.sendRedirect(request.getContextPath() + "/back/categorias");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
        Categoria c = new Categoria();

        try {
            int id = Integer.parseInt(request.getParameter("idCategoria"));
            String nombre = request.getParameter("nombre");
            c.setIdCategoria(id);
            c.setNombre(nombre);
            categoriaDAO.update(c);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDispatcher dispatcher;
        CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

        try {
            int id = Integer.parseInt(request.getParameter("idCategoria"));
            categoriaDAO.delete(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
