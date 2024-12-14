<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Producto" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle Producto - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">
<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <%
        Optional<Producto> optProd = (Optional<Producto>)request.getAttribute("producto");
        if (optProd.isPresent()) {
            Producto p = optProd.get();
    %>
    <h2>Detalle Producto</h2>
    <p><strong>ID:</strong> <%= p.getIdProducto() %></p>
    <p><strong>Nombre:</strong> <%= p.getNombre() %></p>
    <p><strong>Descripción:</strong> <%= p.getDescripcion() %></p>
    <p><strong>Precio:</strong> <%= p.getPrecio() %> €</p>
    <p><strong>Imagen:</strong> <%= p.getImagen() %></p>
    <p><strong>Categoría ID:</strong> <%= p.getIdCategoria() %></p>

    <a href="<%=request.getContextPath()%>/back/productos" class="btn btn-primary">Volver</a>
    <%
    } else {
    %>
    <p>No se ha encontrado el producto.</p>
    <a href="<%=request.getContextPath()%>/back/productos" class="btn btn-primary">Volver</a>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
