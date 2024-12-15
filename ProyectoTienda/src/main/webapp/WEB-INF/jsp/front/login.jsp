<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>

<div class="container mt-5 flex-grow-1 " style="max-width:400px;">
    <h2 class="mb-3">Iniciar Sesión</h2>
    <form action="<%=request.getContextPath()%>/login" method="post">
        <input type="hidden" name="__method__" value="login"/>
        <div class="mb-3">
            <label class="form-label">Usuario:</label>
            <input type="text" name="nombre" class="form-control"/>
        </div>
        <div class="mb-3">
            <label class="form-label">Contraseña:</label>
            <input type="password" name="password" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-primary">Entrar</button>
    </form>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
