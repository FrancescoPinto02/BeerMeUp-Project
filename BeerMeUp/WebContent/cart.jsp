<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   
<%Cart cart = (Cart) request.getSession().getAttribute("cart");
if(cart==null){
	cart = new Cart();
	request.getSession().setAttribute("cart", cart);
}%>


<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.*"%> 
<html lang="it">
<head>
	<%@ include file="head.jsp"%>
</head>
<body>
	<%@ include file="header.jsp" %>
	<h1>Carrello</h1>
	<div class="cart_table">
		<h2><a href="cart_control?action=deleteCart">Svuota Carrello</a></h2>
		<table>
			<tr>
				<th>Codice</th>
				<th>Nome</th>
				<th>Quantità</th>
				<th>Prezzo Totale</th>
				<th>Action</th>
			</tr>
			<% if(cart != null ) { 
				List<CartProduct> prodcart = cart.getProducts(); 	
		   		for(CartProduct x: prodcart) { 
		   			Beer beer = x.getProduct();%>
			<tr>
				<td><%=beer.getId()%></td>
				<td><%=beer.getName()%></td>
				<td>
					<%=x.getQta()%>
					<a href="cart_control?action=increaseQta&id=<%=beer.getId()%>">+</a>
					<a href="cart_control?action=decreaseQta&id=<%=beer.getId()%>">-</a>
				</td>
				<td><%=x.getPrice()%></td>
				<td><a href="cart_control?action=deleteFromCart&id=<%=beer.getId()%>">Delete from cart</a></td>
			</tr>
			<% } %>
			</table>		
		<% } %>	
	</div>
	<%@ include file="footer.jsp" %>	
</body>
</html>