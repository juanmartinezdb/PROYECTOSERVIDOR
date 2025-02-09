<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.Optional" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalle Categoría - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">

    <%
        Optional<Categoria> optCat = (Optional<Categoria>) request.getAttribute("categoria");
        if (optCat.isPresent()) {
            Categoria c = optCat.get();
    %>
    <h2><%= c.getNombre() %></h2>
        <p><strong>ID:</strong> <%= c.getIdCategoria() %></p>
    <%
    } else {
    %>
    <p>No se ha encontrado la categoría</p>
    <%
        }
    %>
    <a href="<%=request.getContextPath()%>/back/categorias" class="btn btn-primary">Volver</a>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
