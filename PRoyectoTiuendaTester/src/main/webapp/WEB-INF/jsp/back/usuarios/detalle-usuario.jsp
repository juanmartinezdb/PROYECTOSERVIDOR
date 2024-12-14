<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="juan.proyectotienda.model.Usuario" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle Usuario - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <%
        Optional<Usuario> optUsu = (Optional<Usuario>) request.getAttribute("usuario");
        if (optUsu.isPresent()) {
            Usuario u = optUsu.get();
    %>
    <h2>Detalle Usuario</h2>
    <p><strong>ID:</strong> <%=u.getIdUsuario()%></p>
    <p><strong>Nombre:</strong> <%=u.getNombre()%></p>
    <p><strong>Rol:</strong> <%=u.getRol()%></p>

    <a href="<%=request.getContextPath()%>/back/usuarios" class="btn btn-primary">Volver</a>
    <%
    } else {
    %>
    <p>No se ha encontrado el usuario.</p>
    <a href="<%=request.getContextPath()%>/back/usuarios" class="btn btn-primary">Volver</a>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
