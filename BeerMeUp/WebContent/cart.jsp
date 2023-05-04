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
	<%@ include file="header.jsp"%>
	
	<main>
	<div id=catalog> <!--È solo temporaneo, bisogna modificarlo-->
		<h1>Carrello</h1>
		<h2><a href="cart_control?action=deleteCart">Svuota Carrello</a></h2>
		<div class="product-container"> <!--È solo temporaneo, bisogna modificarlo-->
			<table class="product-table"> <!--È solo temporaneo, bisogna modificarlo-->
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
						<a class="no" href="cart_control?action=increaseQta&id=<%=beer.getId()%>">+</a>
						<%=x.getQta()%>
						<a class ="no" href="cart_control?action=decreaseQta&id=<%=beer.getId()%>">-</a>
					</td>
					<td><%=x.getPrice()%></td>
					<td><a href="cart_control?action=deleteFromCart&id=<%=beer.getId()%>">Delete from cart</a></td>
				</tr>
					<% } %>
				<% } %>	
			</table>		
		</div>
	</div>
	</main>
	
	<%@ include file="footer.jsp" %>	
</body>
</html>