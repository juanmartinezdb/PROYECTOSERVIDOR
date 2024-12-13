<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Categoría - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <h2>Editar Categoría</h2>

    <%
        Categoria c = (Categoria)request.getAttribute("categoria");
        if (c != null) {
    %>
    <form action="<%=request.getContextPath()%>/back/categorias" method="post">
        <input type="hidden" name="__method__" value="put"/>
        <input type="hidden" name="idCategoria" value="<%= c.getIdCategoria() %>" />
        <div class="mb-3">
            <label class="form-label">Nombre de la Categoría:</label>
            <input type="text" name="nombre" class="form-control" value="<%= c.getNombre() %>" required/>
        </div>
        <button type="submit" class="btn btn-warning">Guardar</button>
        <a href="<%=request.getContextPath()%>/back/categorias" class="btn btn-secondary">Volver</a>
    </form>
    <%
    } else {
    %>
    <p>No se ha encontrado la categoría a editar.</p>
    <a href="<%=request.getContextPath()%>/back/categorias" class="btn btn-secondary">Volver</a>
    <%
        }
    %>

</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
