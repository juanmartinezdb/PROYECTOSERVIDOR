<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Categoría - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <h2>Crear Nueva Categoría</h2>
    <form action="<%=request.getContextPath()%>/back/categorias" method="post">
        <div class="mb-3">
            <label class="form-label">Nombre de la Categoría:</label>
            <input type="text" name="nombre" class="form-control" required />
        </div>
        <input type="submit" class="btn btn-success" value="Crear" />
        <a href="<%=request.getContextPath()%>/back/categorias" class="btn btn-secondary">Volver</a>
    </form>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
