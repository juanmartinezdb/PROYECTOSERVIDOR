<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Producto - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <h2>Crear Nuevo Producto</h2>
    <%
        List<Categoria> listaCategorias = (List<Categoria>)request.getAttribute("listaCategorias");
    %>
    <form action="<%=request.getContextPath()%>/back/productos" method="post">
        <div class="mb-3">
            <label class="form-label">Nombre:</label>
            <input type="text" name="nombre" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Descripción:</label>
            <textarea name="descripcion" class="form-control"></textarea>
        </div>
        <div class="mb-3">
            <label class="form-label">Precio:</label>
            <input type="number" step="0.01" name="precio" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Imagen (nombre de archivo):</label>
            <input type="text" name="imagen" class="form-control" />
        </div>
        <div class="mb-3">
            <label class="form-label">Categoría:</label>
            <select name="idCategoria" class="form-select">
                <%
                    if (listaCategorias != null) {
                        for (Categoria c : listaCategorias) {
                %>
                <option value="<%=c.getIdCategoria()%>"><%=c.getNombre()%></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <button type="submit" class="btn btn-success">Crear</button>
        <a href="<%=request.getContextPath()%>/back/productos" class="btn btn-secondary">Volver</a>
    </form>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
