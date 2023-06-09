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
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html">
		<title>Beer Me Up</title>
		<meta name="viewport" content="widht=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/style.css?ts=<?=time()?>&quot">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>
	<%@ include file="header.jsp"%>
	
	<main>
	<div id="cart-page"> <!--È solo temporaneo, bisogna modificarlo-->
		<h1>Carrello</h1>
		<div class="product-container">
		<div class="scroll-table">
			<table class="product-table"> 
				<caption></caption>
				<tr>
					<th>Codice</th>
					<th>Nome</th>
					<th>Quantità</th>
					<th>Prezzo Totale</th>
					<th>Action</th>
				</tr>
				<% if(!cart.isEmpty() && cart != null) { 
					List<CartProduct> prodcart = cart.getProducts(); 	
		   			for(CartProduct x: prodcart) { 
		   				Beer beer = x.getProduct();%>
				<tr>
					<td><%=beer.getId()%></td>
					<td><%=beer.getName()%></td>
					<td>
						<a class ="no" href="cart_control?action=decreaseQta&id=<%=beer.getId()%>">-</a>
						<%=x.getQta()%>
						<a class="no" href="cart_control?action=increaseQta&id=<%=beer.getId()%>">+</a>
					</td>
					<td><%=x.getPrice(true)%>€</td>
					<td><a class="action" href="cart_control?action=deleteFromCart&id=<%=beer.getId()%>">Delete from cart</a></td>
				</tr>
					<% } %>
				<% } 
				else { %>
					<td colspan="5">CARRELLO VUOTO</td>
				<% } %>
			</table><br><br>
			</div>
			<a class="buttons" href="cart_control?action=deleteCart">Svuota Carrello</a>
			<a class="buttons" href="checkout.jsp">Checkout</a>	
		</div>
	</div>
	</main>
	
	<%@ include file="footer.jsp" %>	
</body>
</html>