<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Producto" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Producto - Back</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="d-flex flex-column vh-100">
<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3" style="font-family:'Courier New', monospace;">
    <h2>Editar Producto</h2>
    <%
        Producto p = (Producto)request.getAttribute("producto");
        List<Categoria> listaCategorias = (List<Categoria>)request.getAttribute("listaCategorias");
        if (p != null) {
    %>
    <form action="<%=request.getContextPath()%>/back/productos" method="post">
        <input type="hidden" name="__method__" value="put"/>
        <input type="hidden" name="idProducto" value="<%= p.getIdProducto() %>"/>
        <div class="mb-3">
            <label class="form-label">Nombre:</label>
            <input type="text" name="nombre" class="form-control" value="<%= p.getNombre() %>" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Descripción:</label>
            <textarea name="descripcion" class="form-control"><%= p.getDescripcion() %></textarea>
        </div>
        <div class="mb-3">
            <label class="form-label">Precio:</label>
            <input type="number" step="0.01" name="precio" class="form-control" value="<%= p.getPrecio() %>" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Imagen:</label>
            <input type="text" name="imagen" class="form-control" value="<%= p.getImagen() %>"/>
        </div>
        <div class="mb-3">
            <label class="form-label">Categoría:</label>
            <select name="idCategoria" class="form-select">
                <%
                    if (listaCategorias != null) {
                        for (Categoria c : listaCategorias) {
                            String selected = (c.getIdCategoria() == p.getIdCategoria()) ? "selected" : "";
                %>
                <option value="<%=c.getIdCategoria()%>" <%=selected%>><%=c.getNombre()%></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <button type="submit" class="btn btn-warning">Guardar</button>
        <a href="<%=request.getContextPath()%>/back/productos" class="btn btn-secondary">Volver</a>
    </form>
    <%
    } else {
    %>
    <p>No se ha encontrado el producto a editar.</p>
    <a href="<%=request.getContextPath()%>/back/productos" class="btn btn-secondary">Volver</a>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
