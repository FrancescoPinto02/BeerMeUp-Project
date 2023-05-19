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
	Collection<?> beerList = (Collection<?>) request.getAttribute("beerList");
	
	if(breweryList==null || styleList==null || beerList==null) {
		response.sendRedirect("./productManager_control?action=initialize");
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
				<h2>Aggiungi Birra</h2>
				<form action="./addProduct" method="post" enctype="multipart/form-data" >
					<input type="hidden" name="action" id="action" value="insertBeer">
					<label for="beerName">Nome: </label><br>
					<input type="text" name="beerName" id="beerName" required><br>
					<label for="beerColor">Colore: </label><br>
					<input type="text" name="beerColor" id="beerColor" required><br>
					<label for="beerGradation">Gradazione: </label><br>
					<input type="number" name="beerGradation" id="beerGradation" min="0" max="99" required><br>
					<label for="beerPrice">Prezzo: </label><br>
					<input type="number" name="beerPrice" id="beerPrice" min="0.00" step="0.01" required>€<br>
					<label for="beerIva">IVA: </label><br>
					<input type="number" name="beerIva" id="beerIva" min="0.0" step="0.1" required>%<br>
					<label for="beerStock">Quantità: </label><br>
					<input type="number" name="beerStock" id="beerStock" min="0" step="1" required><br>
					<label for="beerDiscount">Sconto: </label><br>
					<input type="number" name="beerDiscount" id="beerDiscount" min="0" max="99" step="1" required>%<br>	
					<label for="beerDescription">Descrizione: </label><br>
					<textarea name="beerDescription" id="beerDescription" rows="10" cols="40" required></textarea><br>
					<label for="beerIngredients">Ingredienti: </label><br>
					<textarea name="beerIngredients" id="beerIngredients" rows="4" cols="40" required></textarea><br>
					<label for="beerImg">Immagine: </label><br>
					<input type="file" name="beerImg" id="beerImg" required><br>
					<label for="beerStyle">Stile: </label><br>
					<select name="beerStyle" id="beerStyle" required>
					<%
					if (styleList != null && styleList.size() != 0) {
						Iterator<?> styleIt = styleList.iterator();
						while (styleIt.hasNext()) {
							Style style = (Style) styleIt.next();
						%>
						<option value="<%=style.getId()%>"><%=style.getName()%></option>
						<%
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
						%>
							<option value="<%=brewery.getId()%>"><%=brewery.getName()%></option>
						<%
						}
					} 
					%>
					</select><br><br>
					<input type="submit" value="Submit">
				</form>
			</div>
			<div class="admin-page-form">
				<h2>Rimuovi Birra</h2>
				<form action="./productManager_control" method="post">
					<input type="hidden" name="action" id="action" value="deleteBeer">
					<label for="beerId">Birra: </label><br>
					<select name="beerId" id="beerId" required>
					<%
					if (beerList != null && beerList.size() != 0) {
						Iterator<?> beerIt = beerList.iterator();
						while (beerIt.hasNext()) {
							Beer beer = (Beer) beerIt.next();
						%>
							<option value="<%=beer.getId()%>"><%=beer.getName()%></option>
						<%
						}
					} 
					%>
					</select><br><br>
					<input type="submit" />
				</form>
			</div>
		</div>
	</main>
	
	<%@ include file="footer.jsp" %>
</body>
</html>