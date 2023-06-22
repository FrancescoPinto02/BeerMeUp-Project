<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 	Beer beer = (Beer) request.getAttribute("product");
	Brewery brewery = (Brewery) request.getAttribute("productBrewery");
	Style style = (Style) request.getAttribute("productStyle");
	if(beer.getId()<=0 || brewery.getId()<=0 || style.getId()<=0){
		response.sendRedirect("catalogo.jsp");
	}%>
<!DOCTYPE html>
<html lang="it"> 
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.*"%>

<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html">
	<title>Beer Me Up</title>
	<meta name="viewport" content="widht=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="css/prod-page-style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>
	<%@ include file="header.jsp"%>
	
	<main>
		<div id=prod-page>
			<div class="prod-page-container">
				<h1 class="prod-name"><%=beer.getName()%></h1>
				<%if(beer.getStock()>0){%>
					<h3 class="disp">Disponibile</h3>
				<%}else{%>
					<h3 class="not-disp">Esaurito</h3>
				<%}%>
				<div class="prod-info">
                	<div class="img">
                    	<img src="data:image/png;base64,<%=beer.getBase64Image()%>" alt="prodotto">
                	</div>
                
                	<div class="info">
                    	<div class="info-item"><h3>Birrificio: <span><%=brewery.getName()%> </span></h3></div>
                    	<div class="info-item"><h3>Stile: <span><%=style.getName()%></span></h3></div>
                    	<div class="info-item"><h3>Colore: <span><%=beer.getColor()%></span></h3></div>
                    	<div class="info-item"><h3>Gradazione: <span><%=beer.getGradation()%>%</span></h3></div>
                    	<div class="info-item"><h3>Prezzo: <span><%=beer.getPrice(true)%>€</span></h3></div>
						
						<%if(beer.getStock()>0){%>
							<div class="button">
                        	<a href="catalogo_control?action=addToCart&id=<%=beer.getId()%>">Add To Cart</a>
                    		</div>
						<%}%>
                    	
                	</div>
            	</div>

            	<div class="bonus-info">
                	<div>
                    	<h2>Descrizione</h2>
                    	<h3><%=beer.getDescription()%></h3><br>
                	</div>
                	<div>
                    	<h2>Allergeni</h2>
                    	<h3><%=beer.getIngredients()%></h3><br><br>
                	</div>

                	<div>
                    	<h2>Il Birrificio</h2>
                    	<h3><%=brewery.getStory()%></h3><br>
                	</div>
                	<div>
                    	<h2>Lo Stile</h2>
                    	<h3><%=style.getTraits()%></h3>
                	</div>
            	</div>
			</div>
		</div>
	</main>
	
	<%@ include file="footer.jsp" %>
</body>
</html>