<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 	Beer beer = (Beer) request.getAttribute("product");
	Brewery brewery = (Brewery) request.getAttribute("productBrewery");
	Style style = (Style) request.getAttribute("productStyle");
	if(beer==null || brewery==null || style==null){
		response.sendRedirect("catalogo.jsp");
	}%>
<!DOCTYPE html>
<html> <%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.*"%>

<head>
	<%@ include file="head.jsp"%>
</head>

<body>
	<%@ include file="header.jsp"%>
	
	<main>
		<div id=product-page>
			<h1><%=beer.getName()%></h1>
			<img src="data:image/png;base64,<%=beer.getBase64Image()%>" style="width: 25%; text-align: center; margin-left: auto; margin-right: auto;" >
			<table class="product-detail-table">
				<tr>
					<th>ID</th>
					<th>Birrificio</th>
					<th>Stile</th>
					<th>Colore</th>
					<th>Gradazione</th>
					<th>Prezzo</th>
					<th>Stock</th>
					<th>Action</th>
				</tr>
				<tr>
					<td><%=beer.getId()%></td>
					<td><%=brewery.getName()%></td>
					<td><%=style.getName()%></td>
					<td><%=beer.getColor()%></td>
					<td><%=beer.getGradation()%>%</td>
					<td><%=beer.getPrice()%>€</td>
					<td><%=beer.getStock()%></td>
					<td><a href="catalogo_control?action=addToCart&id=<%=beer.getId()%>">Add to cart</a></td>
				</tr>
			</table>
			<div class="product-description">
				<h2>Descrizione</h2>
				<p><%=beer.getDescription()%></p>
			</div>
	
			<div class="product-ingredients">
				<h2>Allergeni</h2>
				<p><%=beer.getIngredients()%></p>
			</div>
		</div>
	</main>
	
	<%@ include file="footer.jsp" %>
</body>
</html>