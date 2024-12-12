<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<%@ page import="juan.proyectotienda.model.Producto" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ventas - Tienda Digital</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>

<%
    List<Categoria> listaCategorias = (List<Categoria>) request.getAttribute("listaCategorias");
    List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");
%>

<div class="container-fluid bg-dark text-light py-2" style="font-family:'Courier New', monospace;">
    <div class="d-flex flex-wrap">
        <a href="<%=request.getContextPath()%>/ventas" class="text-light text-decoration-none me-3">Todas</a>
        <%
            if (listaCategorias != null) {
                for (Categoria c : listaCategorias) {
        %>
        <a href="<%=request.getContextPath()%>/ventas/<%=c.getIdCategoria()%>" class="text-light text-decoration-none me-3"><%= c.getNombre() %></a>
        <%
                }
            }
        %>
    </div>
</div>

<div class="container flex-grow-1 mt-3">
    <div class="row">
        <%
            if (listaProductos != null && !listaProductos.isEmpty()) {
                for (Producto p : listaProductos) {
        %>
        <div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-3">
            <div class="card h-100" style="border:2px solid #444;">
                <% if(p.getImagen() != null && !p.getImagen().isEmpty()) { %>
                <img src="<%=request.getContextPath()%>/images/<%=p.getImagen()%>" class="card-img-top" alt="<%=p.getNombre()%>">
                <% } %>
                <div class="card-body">
                    <h5 class="card-title" style="font-family:'Courier New', monospace;"><%=p.getNombre()%></h5>
                    <p class="card-text"><%=p.getDescripcion()%></p>
                    <p class="card-text text-success"><strong><%=p.getPrecio()%> €</strong></p>
                    <form action="<%=request.getContextPath()%>/ventas" method="post" class="d-flex align-items-center">
                        <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>"/>
                        <input type="number" name="cantidad" value="1" min="1" class="form-control me-2" style="width:80px;"/>
                        <button type="submit" class="btn btn-sm btn-primary">Añadir</button>
                    </form>
                </div>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <div class="col-12">
            <p>No hay productos disponibles.</p>
        </div>
        <%
            }
        %>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
