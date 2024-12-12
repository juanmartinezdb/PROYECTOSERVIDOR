package juan.proyectotienda.servlet;

import juan.proyectotienda.dao.PedidoDAO;
import juan.proyectotienda.dao.PedidoDAOImpl;
import juan.proyectotienda.model.Pedido;
import juan.proyectotienda.model.Producto;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "pedidosServlet", value = "/back/pedidos/*")
public class PedidosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PedidoDAO pedidoDAO = new PedidoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;

        if (pathInfo == null || "/".equals(pathInfo)) {
            // Listar pedidos
            List<Pedido> lista = pedidoDAO.getAll();
            request.setAttribute("listaPedidos", lista);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/pedidos.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] parts = pathInfo.split("/");

            if (parts.length == 2 && "crear".equals(parts[1])) {
                // Form crear
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/crear-pedido.jsp");

            } else if (parts.length == 2) {
                // Detalle
                try {
                    int id = Integer.parseInt(parts[1]);
                    Optional<Pedido> ped = pedidoDAO.find(id);
                    if (ped.isPresent()) {
                        request.setAttribute("pedido", ped.get());

                        List<Producto> productosPedido = pedidoDAO.getProductosDePedido(id);
                        List<Integer> cantidades = pedidoDAO.getCantidadesDePedido(id);
                        request.setAttribute("productosPedido", productosPedido);
                        request.setAttribute("cantidadesPedido", cantidades);

                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/detalle-pedido.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/back/pedidos");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/back/pedidos");
                    return;
                }

            } else if (parts.length == 3 && "editar".equals(parts[1])) {
                // Form editar
                try {
                    int id = Integer.parseInt(parts[2]);
                    Optional<Pedido> ped = pedidoDAO.find(id);
                    if (ped.isPresent()) {
                        request.setAttribute("pedido", ped.get());
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/editar-pedido.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/back/pedidos");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/back/pedidos");
                    return;
                }

            } else {
                response.sendRedirect(request.getContextPath() + "/back/pedidos");
                return;
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // Crear
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            String fechaStr = request.getParameter("fecha");
            String totalStr = request.getParameter("total");

            Pedido p = new Pedido();
            p.setIdCliente(idCliente);
            p.setFecha(java.time.LocalDate.parse(fechaStr));
            p.setTotal(new java.math.BigDecimal(totalStr));
            pedidoDAO.create(p);

        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        }

        response.sendRedirect(request.getContextPath() + "/back/pedidos");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idPedido"));
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String fechaStr = request.getParameter("fecha");
        String totalStr = request.getParameter("total");

        Pedido p = new Pedido();
        p.setIdPedido(id);
        p.setIdCliente(idCliente);
        p.setFecha(java.time.LocalDate.parse(fechaStr));
        p.setTotal(new java.math.BigDecimal(totalStr));
        pedidoDAO.update(p);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idPedido"));
        pedidoDAO.delete(id);
    }
}
