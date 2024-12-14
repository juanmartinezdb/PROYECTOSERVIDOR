<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro - Tienda Digital</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>

<div class="container mt-5 flex-grow-1 " style="max-width:400px;">
    <h2 class="mb-3">Registrarse</h2>
    <form action="<%=request.getContextPath()%>/registro" method="post">
        <input type="hidden" name="__method__" value="registrar"/>
        <div class="mb-3">
            <label class="form-label">Usuario:</label>
            <input type="text" name="nombre" class="form-control"/>
        </div>
        <div class="mb-3">
            <label class="form-label">Contraseña:</label>
            <input type="password" name="password" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-success">Registrarse</button>
        <%
    //PREGUNTAR POR EL BOOLEAN.TRUE si no lo pongo asi no lo reconoce da igual como lo ponga.
            if (Boolean.TRUE.equals(request.getAttribute("existe"))) {
        %>
        <div class="badge bg-warning">Ese nombre de usuario ya existe, por favor escoge otro nombre</div>
        <%}%>

    </form>
</div>


<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
