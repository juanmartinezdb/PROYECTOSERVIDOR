<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
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
            <!--cambiar por lo de subir directamente el archivo y que lo nombre segun corresponda si da tiemp.-->
            <label class="form-label">Imagen (idCategoria_idProducto.jpg):</label>
            <input type="text" name="imagen" class="form-control" />
        </div>
        <div class="mb-3">
            <label class="form-label">Categoría:</label>
            <select name="idCategoria" class="form-select">
                <%
                    List<Categoria> listaCategorias = (List<Categoria>)request.getAttribute("listaCategorias");
                    if (listaCategorias != null) {
                        for (Categoria c : listaCategorias) {
                %>
                <option value="<%=c.getIdCategoria()%>"><%=c.getNombre()%></option>
                <%
                        } }
                %>
            </select>
        </div>
        <button type="submit" class="btn btn-success">Crear</button>
        <a href="<%=request.getContextPath()%>/back/productos" class="btn btn-secondary">Volver</a>
    </form>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
