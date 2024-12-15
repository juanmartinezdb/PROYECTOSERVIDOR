<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="juan.proyectotienda.model.Categoria" %>
<%@ page import="juan.proyectotienda.model.Producto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ventas</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>

<%
    List<Categoria> listaCategorias = (List<Categoria>) request.getAttribute("listaCategorias");
    List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");
%>

<div class="container-fluid">
    <div class="d-flex flex-wrap">
        <a href="<%=request.getContextPath()%>/ventas" class="btn btn-outline-primary me-2">Todas</a>
        <%
            if (listaCategorias != null) {
                for (Categoria c : listaCategorias) {
        %>
        <a href="<%=request.getContextPath()%>/ventas/<%=c.getIdCategoria()%>" class="btn btn-outline-primary me-2">
            <%= c.getNombre() %>
        </a>
        <%}}%>
    </div>
</div>

<div class="container mt-3 flex-grow-1 ">
    <div class="row">
        <%
            if (!listaProductos.isEmpty()) {
            int catSelect = 0;
            //esto creo que es una chapuza pero con las prisas no saco de otra, basicamente comparamos categoria de la primera y la ultimay si son diferentes pinta todas. (si meto el filtro por "mas vendidos" ya me fallaria seguro)
            if (listaProductos.get(0).getIdCategoria()!=listaProductos.get(listaProductos.size() - 1).getIdCategoria()){ %>
        <h2>Todas</h2>
<%} else {
    catSelect = listaProductos.get(0).getIdCategoria();
%>
        <h2><%=listaCategorias.get(catSelect-1).getNombre()%></h2>
        <%} for (Producto p : listaProductos) {
        %>
        <div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-3">
            <div class="card h-100 d-flex flex-column">
                <div class="card-body d-flex flex-column justify-content-between">
                    <!--Meter lo de la imagen generica generico.jpg y buscar como se hace si directamente no la encuentra aunque este declarada-->
                <img src="<%=request.getContextPath()%>/imagenes/<%=p.getImagen()%>" class="" alt="<%=p.getNombre()%>">
                    <div>
                        <h5 class="card-title mt-2"><%=p.getNombre()%></h5>
                        <p class="card-text"><%=p.getDescripcion()%></p>
                    </div>
                    <div class="mt-auto d-flex justify-content-between align-items-center">
                        <p class="card-text text-success mb-0">
                            <strong><%=p.getPrecio()%> €</strong>
                        </p>
                        <form action="<%=request.getContextPath()%>/ventas" method="post" class="d-flex align-items-center">
                            <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>"/>
                            <input type="hidden" name="idCategoria" value="<%=catSelect%>"/>
                            <input type="number" name="cantidad" value="1" min="1" class="form-control me-2" style="width:80px;"/>
                            <button type="submit" class="btn btn-sm btn-primary">Añadir</button>
                        </form>
                    </div>
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
</body>
</html>
