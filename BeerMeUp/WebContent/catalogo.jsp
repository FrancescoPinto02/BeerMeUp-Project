<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> productsList = (Collection<?>) request.getAttribute("productsList");
	if(productsList == null) {
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
				<th>Immagine</th>
				<th>Nome <a href="catalogo_control?action=retrieveAllBeers&sort=beer_name">Sort</a></th>
				<th>Prezzo <a href="catalogo_control?action=retrieveAllBeers&sort=price">Sort</a></th>
				<th>Action</th>
			</tr>
			<%
				if (productsList != null && productsList.size() != 0) {
					Iterator<?> it = productsList.iterator();
					while (it.hasNext()) {
						Beer bean = (Beer) it.next();
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><img src="data:image/png;base64,<%=bean.getBase64Image()%>" width="250" height="300"></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getPrice()%></td>
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