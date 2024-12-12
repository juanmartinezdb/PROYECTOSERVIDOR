package juan.proyectotienda.servlet;

import juan.proyectotienda.dao.CategoriaDAO;
import juan.proyectotienda.dao.CategoriaDAOImpl;
import juan.proyectotienda.dao.ProductoDAO;
import juan.proyectotienda.dao.ProductoDAOImpl;
import juan.proyectotienda.model.Categoria;
import juan.proyectotienda.model.Producto;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "ventasServlet", value = "/ventas/*")
public class VentasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
    private ProductoDAO productoDAO = new ProductoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;
        List<Categoria> categorias = categoriaDAO.getAll();
        request.setAttribute("listaCategorias", categorias);

        List<Producto> todosProductos = productoDAO.getAll();

        if (pathInfo == null || "/".equals(pathInfo)) {
            // Mostrar todos o sin filtro
            request.setAttribute("listaProductos", todosProductos);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/ventas.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] parts = pathInfo.split("/");

            if (parts.length == 2) {
                // /ventas/{idCategoria} filtrar por categoría
                try {
                    int idCat = Integer.parseInt(parts[1]);
                    List<Producto> filtrados = todosProductos.stream()
                            .filter(p -> p.getIdCategoria() == idCat)
                            .collect(Collectors.toList());
                    request.setAttribute("listaProductos", filtrados);
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/ventas.jsp");
                } catch (NumberFormatException e) {
                    request.setAttribute("listaProductos", todosProductos);
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/ventas.jsp");
                }

            } else {
                request.setAttribute("listaProductos", todosProductos);
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/ventas.jsp");
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Añadir al carrito
        HttpSession session = request.getSession(true);

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new HashMap<>();
        }

        carrito.put(idProducto, carrito.getOrDefault(idProducto, 0) + cantidad);
        session.setAttribute("carrito", carrito);

        response.sendRedirect(request.getContextPath() + "/ventas");
    }
}
