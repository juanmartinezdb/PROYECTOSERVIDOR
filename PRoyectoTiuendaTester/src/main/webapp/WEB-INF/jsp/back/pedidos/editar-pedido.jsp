<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Pedido" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Pedido - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">
<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <h2>Editar Pedido</h2>
    <%
        Optional<Pedido> optPed = (Optional<Pedido>)request.getAttribute("pedido");
        if (optPed.isPresent()) {
            Pedido ped = optPed.get();
    %>
    <form action="<%=request.getContextPath()%>/back/pedidos" method="post">
        <input type="hidden" name="__method__" value="put"/>
        <input type="hidden" name="idPedido" value="<%= ped.getIdPedido() %>"/>
        <div class="mb-3">
            <label class="form-label">ID Cliente:</label>
            <input type="number" name="idCliente" class="form-control" value="<%=ped.getIdCliente()%>" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Fecha (YYYY-MM-DD):</label>
            <input type="text" name="fecha" class="form-control" value="<%=ped.getFecha()%>" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Total:</label>
            <input type="number" step="0.01" name="total" class="form-control" value="<%=ped.getTotal()%>" required/>
        </div>
        <button type="submit" class="btn btn-warning">Guardar</button>
        <a href="<%=request.getContextPath()%>/back/pedidos" class="btn btn-secondary">Volver</a>
    </form>
    <%
    } else {
    %>
    <p>No se ha encontrado el pedido a editar.</p>
    <a href="<%=request.getContextPath()%>/back/pedidos" class="btn btn-secondary">Volver</a>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
