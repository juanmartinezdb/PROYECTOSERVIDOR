<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="juan.proyectotienda.model.Producto" %>
<%@ page import="java.math.BigDecimal" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Carrito - Tienda Digital</title>
    <%@ include file="/WEB-INF/jsp/comunes/bootstrap.jspf" %>
    <%@ include file="/WEB-INF/jsp/comunes/css.jspf" %>
</head>
<body class="d-flex flex-column vh-100">

<%@ include file="/WEB-INF/jsp/comunes/header.jspf" %>
<%@ include file="/WEB-INF/jsp/comunes/navbar.jspf" %>

<%
    List<Producto> productosCarrito = (List<Producto>) request.getAttribute("productosCarrito");
    Map<Integer,Integer> carritoMap = (Map<Integer,Integer>) request.getAttribute("carritoMap");
    BigDecimal total = (BigDecimal) request.getAttribute("total");
    if (carritoMap == null) {
        carritoMap = new HashMap<>();
    }
    if (total == null) {
        total = BigDecimal.ZERO;
    }
%>

<div class="container mt-3 d-flex flex-grow-1 ">
    <div class="flex-grow-1  me-3">
        <%
            if (productosCarrito != null && !productosCarrito.isEmpty()) {
                for (Producto p : productosCarrito) {
                    int cantidad = carritoMap.get(p.getIdProducto());
        %>
        <div class="card mb-3" style="border:2px solid #444;">
            <div class="row g-0 d-flex align-items-center justify-content-center">
                <div class="col-md-3 d-flex align-items-center justify-content-center" style="height:100px; width:100px;">
                    <% if (p.getImagen()!=null && !p.getImagen().isEmpty()) { %>
                    <img src="<%=request.getContextPath()%>/imagenes/<%=p.getImagen()%>" class="img-fluid" alt="<%=p.getNombre()%>">
                    <% } else { %>
                    <div style="height:100px; width:100px; background:#888;"></div>
                    <% } %>
                </div>
                <div class="col-md-9">
                    <div class="card-body">
                        <h5 class="card-title"><%=p.getNombre()%></h5>
                        <p class="card-text text-success"><strong><%=p.getPrecio()%> € c/u</strong></p>
                        <p>Cantidad:</p>
                        <form action="<%=request.getContextPath()%>/carrito" method="post" class="d-inline">
                            <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>"/>
                            <input type="number" name="cantidad" value="<%=cantidad%>" min="1" class="form-control d-inline-block" style="width:80px;vertical-align:middle;"/>
                            <input type="hidden" name="accion" value="actualizar"/>
                            <button type="submit" class="btn btn-sm btn-warning ms-2">Actualizar</button>
                        </form>
                        <form action="<%=request.getContextPath()%>/carrito" method="post" class="d-inline ms-2">
                            <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>"/>
                            <input type="hidden" name="accion" value="eliminar"/>
                            <button type="submit" class="btn btn-sm btn-danger">Eliminar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <p>No hay productos en el carrito.</p>
        <%
            }
        %>
    </div>
    <div style="width:300px;">
        <div class="card p-3" style="border:2px solid #444;">
            <h5>Total: <%= total %> €</h5>
            <form action="<%=request.getContextPath()%>/carrito" method="post">
                <input type="hidden" name="accion" value="confirmar"/>
                <button type="submit" class="btn btn-success">Confirmar pedido</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/comunes/footer.jspf" %>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
