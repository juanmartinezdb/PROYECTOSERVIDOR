<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Index - Tienda Digital</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>

<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
%>

<div class="container">
    <h1>Categorías</h1>
    <div class="row">
        <% if (categorias != null) { %>
        <% for (Categoria categoria : categorias) { %>
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title"><%= categoria.getNombre() %></h5>
                </div>
            </div>
        </div>
        <% } %>
        <% } else { %>
        <p>No hay categorías disponibles.</p>
        <% } %>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>

</body>
</html>
