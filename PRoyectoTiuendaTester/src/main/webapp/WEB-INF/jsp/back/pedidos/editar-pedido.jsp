<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Pedido" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Pedido - Back</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="d-flex flex-column vh-100">
<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3" style="font-family:'Courier New', monospace;">
    <h2>Editar Pedido</h2>
    <%
        Pedido ped = (Pedido)request.getAttribute("pedido");
        if (ped != null) {
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
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
