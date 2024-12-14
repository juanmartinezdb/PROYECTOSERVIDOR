<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Usuario - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <h2>Crear Nuevo Usuario</h2>
    <form action="<%=request.getContextPath()%>/back/usuarios" method="post">
        <div class="mb-3">
            <label class="form-label">Nombre de Usuario:</label>
            <input type="text" name="nombre" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Contraseña:</label>
            <input type="password" name="password" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Rol (cliente/admin):</label>
            <select name="rol" class="form-select">
                <option value="cliente">cliente</option>
                <option value="admin">admin</option>
            </select>
        </div>
        <input type="submit" class="btn btn-success" value="Crear" />
        <a href="<%=request.getContextPath()%>/back/usuarios" class="btn btn-secondary">Volver</a>
    </form>
    <%
        //PREGUNTAR POR EL BOOLEAN.TRUE si no lo pongo asi no lo reconoce da igual como lo ponga.
        if (Boolean.TRUE.equals(request.getAttribute("existe"))) {
    %>
    <div class="badge bg-warning">Ese nombre de usuario ya existe, por favor escoge otro nombre</div>
    <%}%>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
