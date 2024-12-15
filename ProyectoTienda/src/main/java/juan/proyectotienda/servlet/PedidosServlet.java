package juan.proyectotienda.servlet;

import juan.proyectotienda.dao.PedidoDAO;
import juan.proyectotienda.dao.PedidoDAOImpl;
import juan.proyectotienda.model.Pedido;
import juan.proyectotienda.model.Producto;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

            List<Pedido> lista = pedidoDAO.getAll();
            request.setAttribute("listaPedidos", lista);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/pedidos.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/crear-pedido.jsp");

            } else if (pathParts.length == 2) {
    //Pedidos/{id}
                try {

                    int id = Integer.parseInt(pathParts[1]);
                        request.setAttribute("pedido",pedidoDAO.find(id));
                        request.setAttribute("productosPedido", pedidoDAO.getProductosDePedido(id));
                        request.setAttribute("cantidadesPedido", pedidoDAO.getCantidadesDePedido(id));
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/detalle-pedido.jsp");
                    System.out.println("ENTRA PEDIDO");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("NO ENTRA PEDIDO");
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/pedidos.jsp");
                }

            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                // /categorias/editar/{id}
                try {

                        request.setAttribute("pedido", pedidoDAO.find(Integer.parseInt(pathParts[2])));
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/editar-pedido.jsp");

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/pedidos.jsp");
                }

            } else {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/back/pedidos/pedidos.jsp");
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // HACERLO PARA METERLE LOS PRODUCTOS ES COMPLICADO, PREGUNTAR SI HAY QUE HACERLO, HACERLO SI DA TIEMPO
            Pedido p = new Pedido();
            p.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
            p.setFecha(java.time.LocalDate.parse(request.getParameter("fecha")));
            p.setTotal(new java.math.BigDecimal(request.getParameter("total")));
            pedidoDAO.create(p);

            //actualizar
        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
            //borrar
        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        }

        response.sendRedirect(request.getContextPath() + "/back/pedidos");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Pedido p = new Pedido();

        try {
        p.setIdPedido(Integer.parseInt(request.getParameter("idPedido")));
        p.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
        p.setFecha(java.time.LocalDate.parse(request.getParameter("fecha")));
        p.setTotal(new java.math.BigDecimal(request.getParameter("total")));
        pedidoDAO.update(p);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    RequestDispatcher dispatcher;

        try {
        pedidoDAO.delete(Integer.parseInt(request.getParameter("idPedido")));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
