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

@WebServlet(name = "ventasServlet", value = {"/index.jsp", "/ventas/*"})
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
            // El index que lo voy a redireccionar a VENTAS (para que salte la mquina directamente)
            // "/" la barra vacia
            request.setAttribute("listaProductos", todosProductos);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/ventas.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] parts = pathInfo.split("/");

            if (parts.length == 2) {
                // /ventas/num salta segun la id de la categoria
                try {
                    int idCat = Integer.parseInt(parts[1]);
                    List<Producto> filtrados = todosProductos.stream()
                                                                    .filter(p -> p.getIdCategoria() == idCat)
                                                                    .collect(Collectors.toList());
                    request.setAttribute("listaProductos", filtrados);
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/ventas.jsp");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
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

        //Solo hay uno que lo que hace es aádir al carrito.
        HttpSession session = request.getSession(true);

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new HashMap<>();
        }

        if (carrito.containsKey(idProducto)) {
            int cantidadActual = carrito.get(idProducto);
            carrito.put(idProducto, cantidadActual + cantidad);
        } else {
            carrito.put(idProducto, cantidad);
        }

        session.setAttribute("carrito", carrito);

        if (idCategoria > 0 ) {
            response.sendRedirect(request.getContextPath() + "/ventas/" + idCategoria);
        } else {
            response.sendRedirect(request.getContextPath() + "/ventas");
        }
    }
}
