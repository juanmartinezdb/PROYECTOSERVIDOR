<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Categorías - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Categorías</h2>
        <form action="<%=request.getContextPath()%>/back/categorias/crear" method="get" style="display:inline;">
            <input type="submit" class="btn btn-primary" value="Crear Nueva Categoría" />
        </form>
    </div>

    <%
        List<Categoria> listaCategorias = (List<Categoria>)request.getAttribute("listaCategorias");
        if (listaCategorias != null && !listaCategorias.isEmpty()) {
    %>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Categoria c : listaCategorias) {
        %>
        <tr>
            <td><%= c.getIdCategoria() %></td>
            <td><%= c.getNombre() %></td>
            <td>
                <form action="<%=request.getContextPath()%>/back/categorias/<%=c.getIdCategoria()%>" method="get" style="display:inline;">
                    <input type="submit" class="btn btn-sm btn-info" value="Ver Detalle" />
                </form>
                <form action="<%=request.getContextPath()%>/back/categorias/editar/<%=c.getIdCategoria()%>" method="get" style="display:inline;">
                    <input type="submit" class="btn btn-sm btn-warning" value="Editar" />
                </form>
                <form action="<%=request.getContextPath()%>/back/categorias" method="post" style="display:inline;">
                    <input type="hidden" name="__method__" value="delete"/>
                    <input type="hidden" name="idCategoria" value="<%=c.getIdCategoria()%>"/>
                    <input type="submit" class="btn btn-sm btn-danger" value="Eliminar" />
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <p>No hay categorías registradas.</p>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
