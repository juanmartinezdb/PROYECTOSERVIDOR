package juan.proyectotienda.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import juan.proyectotienda.dao.ProductoDAO;
import juan.proyectotienda.dao.ProductoDAOImpl;
import juan.proyectotienda.dao.CategoriaDAO;
import juan.proyectotienda.dao.CategoriaDAOImpl;
import juan.proyectotienda.model.Producto;

@WebServlet(name = "productosServlet", value = "/back/productos/*")
public class ProductosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductoDAO productoDAO = new ProductoDAOImpl();
    private CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {

            request.setAttribute("listaProductos", productoDAO.getAll());
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/productos.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

                request.setAttribute("listaCategorias", categoriaDAO.getAll());
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/crear-producto.jsp");

            } else if (pathParts.length == 2) {
                // Detalle
                try {
                        request.setAttribute("producto", productoDAO.find(Integer.parseInt(pathParts[1])));
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/detalle-producto.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/productos.jsp");
                }

            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                // /categorias/editar/{id}
                try {
                        request.setAttribute("listaCategorias", categoriaDAO.getAll());
                        request.setAttribute("producto", productoDAO.find(Integer.parseInt(pathParts[2])));
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/editar-producto.jsp");

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/productos.jsp");
                }

            } else {
                System.out.println("Opción POST no soportada.");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/productos/productos.jsp");
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
            Producto p = new Producto();
            p.setNombre(request.getParameter("nombre"));
            p.setDescripcion(request.getParameter("descripcion"));
            p.setPrecio(new BigDecimal(request.getParameter("precio"))); //se supone que BigDecimal es mejor para esto
            p.setImagen(request.getParameter("imagen"));
            p.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
            productoDAO.create(p);
    //actualizar
        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
        //borrar
        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        }

        response.sendRedirect(request.getContextPath() + "/back/productos");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
            Producto p = new Producto();
        try {
            p.setIdProducto(Integer.parseInt(request.getParameter("idProducto")));
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
        RequestDispatcher dispatcher; //preguntar porque se pone si no se usa, no entiendo

        try {
            productoDAO.delete(Integer.parseInt(request.getParameter("idProducto")));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
