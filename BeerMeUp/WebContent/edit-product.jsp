<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%	
	Boolean isAdmin = (Boolean)request.getSession().getAttribute("admin-roles");
	if(isAdmin == null || isAdmin == Boolean.FALSE){
		response.sendRedirect("./login.jsp");
		return;
	}
	Collection<?> breweryList = (Collection<?>) request.getAttribute("breweryList");
	Collection<?> styleList = (Collection<?>) request.getAttribute("styleList");
	Beer beer = (Beer) request.getAttribute("beer");
	
	if(breweryList==null || styleList==null || beer==null) {
		response.sendRedirect("./catalog-manager.jsp");
		return;
	}

%>    
    

<!DOCTYPE html>
<html lang="it"> <%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.*"%>

<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html">
	<title>Beer Me Up</title>
	<meta name="viewport" content="widht=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>
	<%@ include file="header.jsp" %>
	
	<main>
		<div id="admin-page">
			<div class="admin-page-form">
				<h2>Modifica Birra</h2>
				<form action="./catalogManager" method="post">
					<input type="hidden" name="action" id="action" value="editBeer">
					<input type="hidden" name="beerId" id="beerId" value="<%=beer.getId()%>">
					<label for="beerName">Nome: </label><br>
					<input type="text" name="beerName" id="beerName" value="<%=beer.getName()%>" required><br>
					<label for="beerColor">Colore: </label><br>
					<input type="text" name="beerColor" id="beerColor" value="<%=beer.getColor()%>" required><br>
					<label for="beerGradation">Gradazione: </label><br>
					<input type="number" name="beerGradation" id="beerGradation" min="0" max="99" value="<%=beer.getGradation()%>" required><br>
					<label for="beerPrice">Prezzo: </label><br>
					<input type="number" name="beerPrice" id="beerPrice" min="0.00" step="0.01" value="<%=beer.getPrice()%>" required>€<br>
					<label for="beerIva">IVA: </label><br>
					<input type="number" name="beerIva" id="beerIva" min="0.0" step="0.1" value="<%=beer.getIva()%>" required>%<br>
					<label for="beerStock">Quantità: </label><br>
					<input type="number" name="beerStock" id="beerStock" min="0" step="1" value="<%=beer.getStock()%>" required><br>
					<label for="beerDiscount">Sconto: </label><br>
					<input type="number" name="beerDiscount" id="beerDiscount" min="0" max="99" step="1" value="<%=beer.getDiscount()%>" required>%<br>	
					<label for="beerDescription">Descrizione: </label><br>
					<textarea name="beerDescription" id="beerDescription" rows="10" cols="40" required><%=beer.getDescription()%></textarea><br>
					<label for="beerIngredients">Ingredienti: </label><br>
					<textarea name="beerIngredients" id="beerIngredients" rows="4" cols="40" required><%=beer.getIngredients()%></textarea><br>

					<label for="beerStyle">Stile: </label><br>
					<select name="beerStyle" id="beerStyle" required>
					<%
					if (styleList != null && styleList.size() != 0) {
						Iterator<?> styleIt = styleList.iterator();
						while (styleIt.hasNext()) {
							Style style = (Style) styleIt.next();
							if(style.getId()==beer.getStyleId()){
							%>
								<option selected="<%=style.getId()%>"><%=style.getName()%></option>
							<%
							}
							else{
							%>
								<option value="<%=style.getId()%>"><%=style.getName()%></option>
					<%
							}
						}
					} 
					%>
					</select><br>
					<label for="beerBrewery">Birrificio: </label><br>
					<select name="beerBrewery" id="beerBrewery" required>
					<%
					if (breweryList != null && breweryList.size() != 0) {
						Iterator<?> breweryIt = breweryList.iterator();
						while (breweryIt.hasNext()) {
							Brewery brewery = (Brewery) breweryIt.next();
							if(brewery.getId()==beer.getBreweryId()){
							%>
								<option selected="<%=brewery.getId()%>"><%=brewery.getName()%></option>
							<%
							}
							else{
							%>
								<option value="<%=brewery.getId()%>"><%=brewery.getName()%></option>
					<%
							}
						}
					} 
					%>
					</select><br><br>
					<input type="submit" value="Submit">
				</form>
			</div>
		</div>
	</main>
	
	<%@ include file="footer.jsp" %>
</body>
</html>