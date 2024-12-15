package juan.proyectotienda.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import juan.proyectotienda.dao.ProductoDAO;
import juan.proyectotienda.dao.ProductoDAOImpl;
import juan.proyectotienda.dao.PedidoDAO;
import juan.proyectotienda.dao.PedidoDAOImpl;
import juan.proyectotienda.model.Producto;
import juan.proyectotienda.model.Pedido;
import juan.proyectotienda.model.Usuario;

@WebServlet(name = "carritoServlet", value = "/carrito")
public class CarritoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductoDAO productoDAO = new ProductoDAOImpl();
    private PedidoDAO pedidoDAO = new PedidoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new HashMap<>();
        }
        List<Producto> todosProd = productoDAO.getAll();
        Map<Integer, Integer> finalCarrito = carrito; //no me deja usar carrito en el filtro, el IDE me obliga a hacer una copia

        List<Producto> productos = todosProd.stream()
                                                    .filter(p -> finalCarrito.containsKey(p.getIdProducto()))
                                                    .collect(Collectors.toList());

        BigDecimal total = BigDecimal.valueOf(0);
        for (Producto prod : productos) {
            BigDecimal precioxCantidad = prod.getPrecio().multiply(BigDecimal.valueOf(carrito.get(prod.getIdProducto()))); //no se puede usar un * hay que usar multiply por narices para los BigDecimal
            total = total.add(precioxCantidad);
        }

        request.setAttribute("productosCarrito", productos);
        request.setAttribute("carritoMap", carrito);
        request.setAttribute("total", total);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/carrito.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new HashMap<>();
        }

        String __method__ = request.getParameter("__method__");

        if (__method__ == null || "confirmar".equalsIgnoreCase(__method__)) {
            if (carrito.isEmpty()) {
                //PREGUNTAR SI ESTO ESTA BIEN ASI O MEJOR PERMITIR LOS PEDIDOS VACIOS Y QUE REDIRIJA AL LOGIN DIRECTAMENTE
                response.sendRedirect(request.getContextPath() + "/carrito");
                return;
            }

            Usuario userlog = (Usuario) session.getAttribute("usuario");
            if (userlog == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            List<Producto> todosProd = productoDAO.getAll();
            Map<Integer, Integer> finalCarrito = carrito; //no me deja usar carrito en el filtro, el IDE me obliga a hacer una copia

            List<Producto> productos = todosProd.stream()
                                                        .filter(p -> finalCarrito.containsKey(p.getIdProducto()))
                                                        .collect(Collectors.toList());

            BigDecimal total = BigDecimal.valueOf(0);
            List<Integer> cantidades = new ArrayList<>();

            for (Producto prod : productos) {
                int cant = carrito.get(prod.getIdProducto());
                BigDecimal subtotal = prod.getPrecio().multiply(BigDecimal.valueOf(cant));
                total = total.add(subtotal);
                cantidades.add(cant);
            }

            Pedido p = new Pedido();
            p.setIdCliente(userlog.getIdUsuario());
            p.setFecha(LocalDate.now());
            p.setTotal(total);

            pedidoDAO.createConArticulos(p, productos, cantidades);

            // Vaciar carrito
            carrito.clear();
        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        }

        session.setAttribute("carrito", carrito);
        response.sendRedirect(request.getContextPath() + "/carrito");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int nuevaCantidad = Integer.parseInt(request.getParameter("cantidad"));
    //aqui si no lo controlara con el input puedo limitar que si va a 0 elimine o si quiero que solo se puedan sacar segun stock etc...
            carrito.put(idProducto, nuevaCantidad);

        session.setAttribute("carrito", carrito);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        carrito.remove(idProducto);

        session.setAttribute("carrito", carrito);
    }
}
