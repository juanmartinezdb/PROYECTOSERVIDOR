<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle Categoría - Back</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3" style="font-family:'Courier New', monospace;">

    <%
        Categoria c = null;
        Object catObj = request.getAttribute("categoria");
        if (catObj != null && catObj instanceof Categoria) {
            c = (Categoria)catObj;
        }
    %>

    <h2>Detalle Categoría</h2>
    <%
        if (c != null) {
    %>
    <div class="mb-3">
        <label><strong>ID:</strong></label>
        <span><%= c.getIdCategoria() %></span>
    </div>
    <div class="mb-3">
        <label><strong>Nombre:</strong></label>
        <span><%= c.getNombre() %></span>
    </div>

    <a href="<%=request.getContextPath()%>/back/categorias" class="btn btn-primary">Volver</a>

    <%
    } else {
    %>
    <p>No se ha encontrado la categoría</p>
    <a href="<%=request.getContextPath()%>/back/categorias" class="btn btn-primary">Volver</a>
    <%
        }
    %>

</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
