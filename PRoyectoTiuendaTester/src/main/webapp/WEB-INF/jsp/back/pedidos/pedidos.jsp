<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Pedido" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Pedidos - Back</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="d-flex flex-column vh-100">
<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3" style="font-family:'Courier New', monospace;">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Pedidos</h2>
        <form action="<%=request.getContextPath()%>/back/pedidos/crear" method="get" style="display:inline;">
            <input type="submit" class="btn btn-primary" value="Crear Nuevo Pedido" />
        </form>
    </div>

    <%
        List<Pedido> listaPedidos = (List<Pedido>)request.getAttribute("listaPedidos");
        if (listaPedidos != null && !listaPedidos.isEmpty()) {
    %>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID Pedido</th>
            <th>ID Cliente</th>
            <th>Fecha</th>
            <th>Total</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Pedido ped : listaPedidos) {
        %>
        <tr>
            <td><%= ped.getIdPedido() %></td>
            <td><%= ped.getIdCliente() %></td>
            <td><%= ped.getFecha() %></td>
            <td><%= ped.getTotal() %> €</td>
            <td>
                <form action="<%=request.getContextPath()%>/back/pedidos/<%=ped.getIdPedido()%>" method="get" style="display:inline;">
                    <input type="submit" class="btn btn-sm btn-info" value="Ver Detalle" />
                </form>
                <form action="<%=request.getContextPath()%>/back/pedidos/editar/<%=ped.getIdPedido()%>" method="get" style="display:inline;">
                    <input type="submit" class="btn btn-sm btn-warning" value="Editar" />
                </form>
                <form action="<%=request.getContextPath()%>/back/pedidos" method="post" style="display:inline;">
                    <input type="hidden" name="__method__" value="delete"/>
                    <input type="hidden" name="idPedido" value="<%=ped.getIdPedido()%>"/>
                    <input type="submit" class="btn btn-sm btn-danger" value="Eliminar" />
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <p>No hay pedidos registrados.</p>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
