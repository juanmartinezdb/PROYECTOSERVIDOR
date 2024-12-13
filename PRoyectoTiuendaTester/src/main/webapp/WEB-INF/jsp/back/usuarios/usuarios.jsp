<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Usuario" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Usuarios - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Usuarios</h2>
        <form action="<%=request.getContextPath()%>/back/usuarios/crear" method="get" style="display:inline;">
            <input type="submit" class="btn btn-primary" value="Crear Nuevo Usuario" />
        </form>
    </div>

    <%
        List<Usuario> listaUsuarios = (List<Usuario>)request.getAttribute("listaUsuarios");
        if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
    %>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Rol</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Usuario u : listaUsuarios) {
        %>
        <tr>
            <td><%= u.getIdUsuario() %></td>
            <td><%= u.getNombre() %></td>
            <td><%= u.getRol() %></td>
            <td>
                <form action="<%=request.getContextPath()%>/back/usuarios/<%=u.getIdUsuario()%>" method="get" style="display:inline;">
                    <input type="submit" class="btn btn-sm btn-info" value="Ver Detalle" />
                </form>
                <form action="<%=request.getContextPath()%>/back/usuarios/editar/<%=u.getIdUsuario()%>" method="get" style="display:inline;">
                    <input type="submit" class="btn btn-sm btn-warning" value="Editar" />
                </form>
                <form action="<%=request.getContextPath()%>/back/usuarios" method="post" style="display:inline;">
                    <input type="hidden" name="__method__" value="delete"/>
                    <input type="hidden" name="idUsuario" value="<%=u.getIdUsuario()%>"/>
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
    <p>No hay usuarios registrados.</p>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
