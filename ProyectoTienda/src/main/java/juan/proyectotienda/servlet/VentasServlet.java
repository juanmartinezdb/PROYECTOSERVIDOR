package juan.proyectotienda.servlet;

import jakarta.servlet.http.HttpSession;
import juan.proyectotienda.dao.CategoriaDAO;
import juan.proyectotienda.dao.CategoriaDAOImpl;
import juan.proyectotienda.dao.ProductoDAO;
import juan.proyectotienda.dao.ProductoDAOImpl;
import juan.proyectotienda.model.Categoria;
import juan.proyectotienda.model.Producto;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        RequestDispatcher dispatcher;
        String pathInfo = request.getPathInfo();

        //preguntar si es de marqueses declarar aqui las listas en lugar de en cada parte del codigo, es mas limpio que repetirlo pero tal vez innecesario.
        List<Categoria> categorias = categoriaDAO.getAll();
        List<Producto> todosProductos = productoDAO.getAll();
        request.setAttribute("listaCategorias", categorias);

        System.out.println(pathInfo);

        if (pathInfo == null || "/".equals(pathInfo)) {
            //Me va a saltar "null" tanto para el index, como para ventas.
            request.setAttribute("listaProductos", todosProductos);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/ventas.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            //el pathParts[0] no vale para nada, se nos queda una cadena vacia
            System.out.println(pathParts[0]+"--- "+pathParts[1]);

            if (pathParts.length == 2) {
                // /ventas/num salta segun la id de la categoria
                try {
                    //FILTRO POR CATEGORIA (podria hacer otros con otras cosas con el stream o irme al DAO para hacer busquedas)
                    //IDEA: ¡Hacer filtro de "mas vendidos" si da tiempo! (haria falta un ProductosDTO que guarde la cantidad acumulada en pedidos y un getProductosbyidPRoductos en Pedidos (articulos_pedidos)
                    int idCat = Integer.parseInt(pathParts[1]);
                    List<Producto> filtradosbyCategoria = todosProductos.stream()
                                                                    .filter(p -> p.getIdCategoria() == idCat)
                                                                    .collect(Collectors.toList());
                    request.setAttribute("listaProductos", filtradosbyCategoria);
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

        //me traigo el carrito que tengo en sesion primero para comprobar si hay ya productos insertados.
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");

        //si no, lo defino.
        if (carrito == null) {
            carrito = new HashMap<>();
        }

        //cuando meto un producto miro si esta, si esta pillo la cantidad con esa cantidad mas la nueva.
        /* Se puede quitar el if/else cone l getOrDefault PREGUNTAR.
                int cantidadActual = carrito.getOrDefault(idProducto, 0);
                carrito.put(idProducto, cantidadActual + cantidad);
        */
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
