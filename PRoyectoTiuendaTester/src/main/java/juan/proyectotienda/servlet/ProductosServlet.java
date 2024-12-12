package juan.proyectotienda.servlet;

import juan.proyectotienda.dao.ProductoDAO;
import juan.proyectotienda.dao.ProductoDAOImpl;
import juan.proyectotienda.dao.CategoriaDAO;
import juan.proyectotienda.dao.CategoriaDAOImpl;
import juan.proyectotienda.model.Producto;
import juan.proyectotienda.model.Categoria;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "productosServlet", value = "/back/productos/*")
public class ProductosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductoDAO productoDAO = new ProductoDAOImpl();
    private CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;

        if (pathInfo == null || "/".equals(pathInfo)) {
            // Listado
            List<Producto> lista = productoDAO.getAll();
            request.setAttribute("listaProductos", lista);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/productos.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] parts = pathInfo.split("/");

            if (parts.length == 2 && "crear".equals(parts[1])) {
                // Formulario crear
                // Necesitamos las categorías para un select
                List<Categoria> categorias = categoriaDAO.getAll();
                request.setAttribute("listaCategorias", categorias);
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/crear-producto.jsp");

            } else if (parts.length == 2) {
                // Detalle
                try {
                    int id = Integer.parseInt(parts[1]);
                    Optional<Producto> prod = productoDAO.find(id);
                    if (prod.isPresent()) {
                        request.setAttribute("producto", prod);
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/detalle-producto.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/back/productos");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/back/productos");
                    return;
                }

            } else if (parts.length == 3 && "editar".equals(parts[1])) {
                // Formulario editar
                try {
                    int id = Integer.parseInt(parts[2]);
                    Optional<Producto> prod = productoDAO.find(id);
                    if (prod.isPresent()) {
                        List<Categoria> categorias = categoriaDAO.getAll();
                        request.setAttribute("listaCategorias", categorias);
                        request.setAttribute("producto", prod);
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/editar-producto.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/back/productos");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/back/productos");
                    return;
                }

            } else {
                response.sendRedirect(request.getContextPath() + "/back/productos");
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
            Producto p = new Producto();
            p.setNombre(request.getParameter("nombre"));
            p.setDescripcion(request.getParameter("descripcion"));
            p.setPrecio(new BigDecimal(request.getParameter("precio")));
            p.setImagen(request.getParameter("imagen"));
            p.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
            productoDAO.create(p);

        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        }

        response.sendRedirect(request.getContextPath() + "/back/productos");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("idProducto"));
            Producto p = new Producto();
            p.setIdProducto(id);
            p.setNombre(request.getParameter("nombre"));
            p.setDescripcion(request.getParameter("descripcion"));
            p.setPrecio(new BigDecimal(request.getParameter("precio")));
            p.setImagen(request.getParameter("imagen"));
            p.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));

            productoDAO.update(p);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("idProducto"));
            productoDAO.delete(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
