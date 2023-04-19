<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) {
		response.sendRedirect("./catalogo_control?action=retrieveAllBeers");
		return;
	}
	
	Beer product = (Beer) request.getAttribute("product");
%>

<!DOCTYPE html>
<html lang="it">
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.Beer,it.beermeup.model.Cart,it.beermeup.model.CartProduct"%>

<head>
	<%@ include file="head.jsp"%>
</head>

<body>
	<%@ include file="header.jsp" %>
	<h1>Catalogo</h1>
	<div class="product_table">
		<h2>Prodotti</h2>
		<table>
			<tr>
				<th>Codice <a href="catalogo_control?action=retrieveAllBeers&sort=id">Sort</a></th>
				<th>Nome <a href="catalogo_control?action=retrieveAllBeers&sort=nome">Sort</a></th>
				<th>Prezzo <a href="catalogo_control?action=retrieveAllBeers&sort=prezzo">Sort</a></th>
				<th>Action</th>
			</tr>
			<%
				if (products != null && products.size() != 0) {
					Iterator<?> it = products.iterator();
					while (it.hasNext()) {
						Beer bean = (Beer) it.next();
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><%=bean.getNome()%></td>
				<td><%=bean.getPrezzo()%></td>
				<td>
					<p><a href="catalogo_control?action=addToCart&id=<%=bean.getId()%>">Add to cart</a></p>
					<p><a href="productDetail_control?action=showProductDetails&id=<%=bean.getId()%>">Dettagli</a></p>
				</td>
			</tr>
			<%
					}
				} else {
			%>
			<tr>
				<td colspan="6">No products available</td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<%@ include file="footer.jsp" %>
</body>
</html>