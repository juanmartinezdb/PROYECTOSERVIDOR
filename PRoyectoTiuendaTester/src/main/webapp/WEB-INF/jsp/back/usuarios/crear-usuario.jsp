<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Usuario - Back</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3" style="font-family:'Courier New', monospace;">
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
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
