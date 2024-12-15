<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="juan.proyectotienda.model.Producto" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Producto - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">
<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <h2>Editar Producto</h2>
    <%
        Optional<Producto> optProd = (Optional<Producto>)request.getAttribute("producto");
        List<Categoria> listaCategorias = (List<Categoria>)request.getAttribute("listaCategorias");
        if (optProd.isPresent()) {
            Producto p = optProd.get();
    %>
    <form action="<%=request.getContextPath()%>/back/productos" method="post">
        <input type="hidden" name="__method__" value="put"/>
        <input type="hidden" name="idProducto" value="<%= p.getIdProducto() %>"/>
        <div class="mb-3">
            <label class="form-label">Nombre:</label>
            <input type="text" name="nombre" class="form-control" value="<%= p.getNombre() %>" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Descripción:</label>
            <textarea name="descripcion" class="form-control"><%= p.getDescripcion() %></textarea>
        </div>
        <div class="mb-3">
            <label class="form-label">Precio:</label>
            <input type="number" step="0.01" name="precio" class="form-control" value="<%= p.getPrecio() %>" required/>
        </div>
        <div class="mb-3">

            <label class="form-label">Imagen:</label>
            <input type="text" name="imagen" class="form-control" value="<%= p.getImagen() %>"/>
        </div>
        <div class="mb-3">
            <label class="form-label">Categoría:</label>
            <select name="idCategoria" class="form-select">
                <%
                    if (listaCategorias != null) {
                        for (Categoria c : listaCategorias) {
                %>
                <option value="<%=c.getIdCategoria()%>"<%=(c.getIdCategoria()==p.getIdCategoria())?"selected":"" %>><%=c.getNombre()%></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <button type="submit" class="btn btn-warning">Guardar</button>
        <a href="<%=request.getContextPath()%>/back/productos" class="btn btn-secondary">Volver</a>
    </form>
    <%
    } else {
    %>
    <p>No se ha encontrado el producto a editar.</p>
    <a href="<%=request.getContextPath()%>/back/productos" class="btn btn-secondary">Volver</a>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
