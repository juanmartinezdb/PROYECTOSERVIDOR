<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="juan.proyectotienda.model.Producto" %>
<%@ page import="juan.proyectotienda.model.Categoria" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Productos - Back</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">
<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/backbar.jspf" %>

<div class="container flex-grow-1 mt-3">
    <h2>Productos</h2>


    <form action="<%=request.getContextPath()%>/back/productos" method="get" class="mb-4">
        <div class="row">
            <div class="col-md-4">
                <input type="text" name="busqueda" class="form-control" placeholder="Buscar por nombre" value=" " />
            </div>
            <div class="col-md-4">
                <select name="idCategoria" class="form-select">
                    <option value="">Filtrar por categoría</option>
                    <%
                        List<Categoria> listaCategorias = (List<Categoria>) request.getAttribute("listaCategorias");
                        if (listaCategorias != null) {
                            for (Categoria cat : listaCategorias) {
                    %>
                    <option value="<%=cat.getIdCategoria()%>"><%=cat.getNombre()%></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-primary">Filtrar</button>
            </div>

        </div>
    </form>

    <div clas="col-md-4 offset-md-8 mb-3">
        <form action="<%= request.getContextPath() %>/back/productos/crear" method="get" style="display:inline;">
            <input type="submit" class="btn btn-primary" value="Crear Nuevo Producto" />
        </form>
    </div>

    <%
        List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");
        if (listaProductos != null && !listaProductos.isEmpty()) {
    %>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-warning">
        <tr>
            <th>ID Producto</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>ID Categoría</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Producto p : listaProductos) {
        %>
        <tr>
            <td><%= p.getIdProducto() %></td>
            <td><%= p.getNombre() %></td>
            <td><%= p.getDescripcion() %></td>
            <td><%= p.getPrecio() %> €</td>
            <td><%= p.getIdCategoria() %></td>
            <td>
                <form action="<%=request.getContextPath()%>/back/productos/<%=p.getIdProducto()%>" method="get" style="display:inline;">
                    <input type="submit" class="btn btn-sm btn-info" value="Ver Detalle" />
                </form>
                <form action="<%=request.getContextPath()%>/back/productos/editar/<%=p.getIdProducto()%>" method="get" style="display:inline;">
                    <input type="submit" class="btn btn-sm btn-warning" value="Editar" />
                </form>
                <form action="<%=request.getContextPath()%>/back/productos" method="post" style="display:inline;">
                    <input type="hidden" name="__method__" value="delete"/>
                    <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>"/>
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
    <p>No hay productos disponibles.</p>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
</body>
</html>
