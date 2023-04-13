<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) {
		response.sendRedirect("./catalogo_control?action=retrieveAllBeers");
		
		return;
	}
	
	
	Beer product = (Beer) request.getAttribute("product");
	Cart cart = (Cart) request.getSession().getAttribute("cart");
	if(cart==null){
		cart = new Cart();
		request.getSession().setAttribute("cart", cart);
	}
	
%>

<!DOCTYPE html>
<html lang="it">
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.Beer,it.beermeup.model.Cart"%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Catalogo BeerMeUp</title>
	<style>
		body{
			min-height:100vh;
			display:flex;
			flex-direction:column;	
		}
	</style>
</head>

<body>
	<%@ include file="header.jsp" %>
	<div class="product_table">
		<h2>Prodotti</h2>
		<table border="1">
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
				<td><a href="catalogo_control?action=addToCart&id=<%=bean.getId()%>">Add to cart</a></td>
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
	<div class="cart_table">
		<% if(cart != null) { %>
			<h2>Carrello</h2>
			<table border="1">
			<tr>
				<th>Codice</th>
				<th>Nome</th>
				<th>Action</th>
			</tr>
			<% List<Beer> prodcart = cart.getProducts(); 	
		   	for(Beer beancart: prodcart) {
			%>
			<tr>
				<td><%=beancart.getId()%></td>
				<td><%=beancart.getNome()%></td>
				<td><a href="catalogo_control?action=deleteFromCart&id=<%=beancart.getId()%>">Delete from cart</a></td>
			</tr>
			<%} %>
		</table>		
		<% } %>	
	</div>
		<div class="footer">
			<%@ include file="footer.jsp" %>
		</div>
</body>
</html>