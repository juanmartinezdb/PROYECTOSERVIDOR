package juan.proyectotienda.servlet;

import juan.proyectotienda.dao.ProductoDAO;
import juan.proyectotienda.dao.ProductoDAOImpl;
import juan.proyectotienda.dao.PedidoDAO;
import juan.proyectotienda.dao.PedidoDAOImpl;
import juan.proyectotienda.model.Producto;
import juan.proyectotienda.model.Pedido;
import juan.proyectotienda.model.Usuario;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

        List<Integer> ids = new ArrayList<>(carrito.keySet());
        List<Producto> productos = productoDAO.getAll().stream()
                .filter(p -> ids.contains(p.getIdProducto()))
                .collect(Collectors.toList());

        BigDecimal total = BigDecimal.ZERO;
        for (Producto prod : productos) {
            int cant = carrito.get(prod.getIdProducto());
            BigDecimal subtotal = prod.getPrecio().multiply(new BigDecimal(cant));
            total = total.add(subtotal);
        }

        request.setAttribute("productosCarrito", productos);
        request.setAttribute("carritoMap", carrito);
        request.setAttribute("total", total);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/front/carrito.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new HashMap<>();
        }

        String accion = request.getParameter("accion");
        if ("actualizar".equalsIgnoreCase(accion)) {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int nuevaCantidad = Integer.parseInt(request.getParameter("cantidad"));
            if (nuevaCantidad <= 0) {
                carrito.remove(idProducto);
            } else {
                carrito.put(idProducto, nuevaCantidad);
            }

        } else if ("eliminar".equalsIgnoreCase(accion)) {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            carrito.remove(idProducto);

        } else if ("confirmar".equalsIgnoreCase(accion)) {
            // Confirmar pedido requiere usuario logueado
            Usuario userlog = (Usuario) session.getAttribute("usuario");
            if (userlog == null) {
                // No hay usuario, redirigir a login
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            // Crear el pedido en la base de datos
            // Recalcular total:
            List<Integer> ids = new ArrayList<>(carrito.keySet());
            List<Producto> productos = productoDAO.getAll().stream()
                    .filter(p -> ids.contains(p.getIdProducto()))
                    .collect(Collectors.toList());

            BigDecimal total = BigDecimal.ZERO;
            List<Integer> cantidades = new ArrayList<>();
            for (Producto prod : productos) {
                int cant = carrito.get(prod.getIdProducto());
                BigDecimal subtotal = prod.getPrecio().multiply(new BigDecimal(cant));
                total = total.add(subtotal);
                cantidades.add(cant);
            }

            Pedido p = new Pedido();
            p.setIdCliente(userlog.getIdUsuario());
            p.setFecha(LocalDate.now());
            p.setTotal(total);

            pedidoDAO.createWithItems(p, productos, cantidades);

            // Vaciar carrito
            carrito.clear();
        }

        session.setAttribute("carrito", carrito);
        response.sendRedirect(request.getContextPath() + "/carrito");
    }
}
