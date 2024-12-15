<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="juan.proyectotienda.model.Pedido"%>
<%@ page import="juan.proyectotienda.model.Producto"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalle Pedido - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <%
        Optional<Pedido> optPed = (Optional<Pedido>)request.getAttribute("pedido");
        List<Producto> productosPedido = (List<Producto>)request.getAttribute("productosPedido");
        List<Integer> cantidades = (List<Integer>)request.getAttribute("cantidadesPedido");

        if (optPed.isPresent()) {
            Pedido ped = optPed.get();
    %>
    <h2>Detalle Pedido</h2>
    <p><strong>ID Pedido:</strong> <%=ped.getIdPedido()%></p>
    <p><strong>ID Cliente:</strong> <%=ped.getIdCliente()%></p>
    <p><strong>Fecha:</strong> <%=ped.getFecha()%></p>
    <p><strong>Total:</strong> <%=ped.getTotal()%> €</p>

    <h3>Productos del Pedido</h3>
    <%
        if (productosPedido != null && !productosPedido.isEmpty()) {
    %>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-warning">
        <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio la unidad</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < productosPedido.size(); i++) {
                Producto pr = productosPedido.get(i);
                int cant = cantidades.get(i);
                BigDecimal subtotal = pr.getPrecio().multiply((BigDecimal.valueOf(cant)));
        %>
        <tr>
            <td><%=pr.getNombre()%></td>
            <td><%=cant%></td>
            <td><%=pr.getPrecio()%> €</td>
            <td><%=subtotal%> €</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <p>Este pedido no tiene productos.</p>
    <%
        }
    %>

    <a href="<%=request.getContextPath()%>/back/pedidos" class="btn btn-primary">Volver</a>
    <%
    } else {
    %>
    <p>No se ha encontrado el pedido.</p>
    <a href="<%=request.getContextPath()%>/back/pedidos" class="btn btn-primary">Volver</a>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>

</body>
</html>
