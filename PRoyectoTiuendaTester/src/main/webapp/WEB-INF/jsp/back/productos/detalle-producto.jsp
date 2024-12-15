<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="juan.proyectotienda.model.Producto" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html>
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
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-6 d-flex flex-column justify-content-center">
                <h2><%= p.getNombre() %></h2>
                <p><strong>ID:</strong> <%= p.getIdProducto() %></p>
                <p><strong>Descripción:</strong> <%= p.getDescripcion() %></p>
                <p><strong>Precio:</strong> <%= p.getPrecio() %> €</p>
                <p><strong>Imagen:</strong> <%= p.getImagen() %></p>
                <p><strong>Categoría ID:</strong> <%= p.getIdCategoria() %></p>
            </div>
            <div class="col-md-6 d-flex align-items-center justify-content-center">
                <% if (p.getImagen() != null && !p.getImagen().isEmpty()) { %>
                <img src="<%= request.getContextPath() %>/imagenes/<%= p.getImagen() %>"
                     alt="<%= p.getNombre() %>" class="img-fluid" style="max-height: 300px;">
                <% }
                 %>
            </div>
        </div>
    </div>

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
