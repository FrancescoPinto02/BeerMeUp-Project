<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   
<%Cart cart = (Cart) request.getSession().getAttribute("cart");
if(cart==null){
	cart = new Cart();
	request.getSession().setAttribute("cart", cart);
}%>


<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.*"%> 
<html>
<head>
	<meta charset="UTF-8">
	<title>Carrello</title>
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
		   		for(CartProduct x: prodcart) { %>
			<tr>
				<td><%=x.getProduct().getId()%></td>
				<td><%=x.getProduct().getNome()%></td>
				<td>
					<%=x.getQta()%>
					<a href="cart_control?action=increaseQta&id=<%=x.getProduct().getId()%>">+</a>
					<a href="cart_control?action=decreaseQta&id=<%=x.getProduct().getId()%>">-</a>
				</td>
				<td><%=x.getPrice()%></td>
				<td><a href="cart_control?action=deleteFromCart&id=<%=x.getProduct().getId()%>">Delete from cart</a></td>
			</tr>
			<%} %>
			</table>		
		<% } %>	
	</div>
	<%@ include file="footer.jsp" %>	
</body>
</html>