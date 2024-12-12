<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle Usuario - Back</title>
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
        Usuario u = (Usuario)request.getAttribute("usuario");
        if (u != null) {
    %>
    <h2>Detalle Usuario</h2>
    <p><strong>ID:</strong> <%=u.getIdUsuario()%></p>
    <p><strong>Nombre:</strong> <%=u.getNombre()%></p>
    <p><strong>Rol:</strong> <%=u.getRol()%></p>

    <a href="<%=request.getContextPath()%>/back/usuarios" class="btn btn-primary">Volver</a>
    <%
    } else {
    %>
    <p>No se ha encontrado el usuario.</p>
    <a href="<%=request.getContextPath()%>/back/usuarios" class="btn btn-primary">Volver</a>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
