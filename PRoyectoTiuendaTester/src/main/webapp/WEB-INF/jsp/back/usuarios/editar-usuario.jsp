<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario - Back</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="d-flex flex-column vh-100">
<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3" style="font-family:'Courier New', monospace;">
    <h2>Editar Usuario</h2>
    <%
        Usuario u = (Usuario)request.getAttribute("usuario");
        if (u != null) {
    %>
    <form action="<%=request.getContextPath()%>/back/usuarios" method="post">
        <input type="hidden" name="__method__" value="put"/>
        <input type="hidden" name="idUsuario" value="<%= u.getIdUsuario() %>"/>
        <div class="mb-3">
            <label class="form-label">Nombre de Usuario:</label>
            <input type="text" name="nombre" class="form-control" value="<%=u.getNombre()%>" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Nueva Contraseña (opcional, si pones se cambia):</label>
            <input type="password" name="password" class="form-control"/>
        </div>
        <div class="mb-3">
            <label class="form-label">Rol (cliente/admin):</label>
            <select name="rol" class="form-select">
                <option value="cliente" <%= "cliente".equals(u.getRol())?"selected":"" %>>cliente</option>
                <option value="admin" <%= "admin".equals(u.getRol())?"selected":"" %>>admin</option>
            </select>
        </div>
        <button type="submit" class="btn btn-warning">Guardar</button>
        <a href="<%=request.getContextPath()%>/back/usuarios" class="btn btn-secondary">Volver</a>
    </form>
    <%
    } else {
    %>
    <p>No se ha encontrado el usuario a editar.</p>
    <a href="<%=request.getContextPath()%>/back/usuarios" class="btn btn-secondary">Volver</a>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
