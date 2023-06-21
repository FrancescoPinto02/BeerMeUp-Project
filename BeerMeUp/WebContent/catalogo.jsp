<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> productsList = (Collection<?>) request.getAttribute("productsList");
	Map<?, ?> breweryMap = (Map<?, ?>) request.getAttribute("breweryMap");
	
	if(productsList == null || breweryMap == null){
		response.sendRedirect("./catalogo_control?action=catalogo");
		return;
	}
	
	
%>

<!DOCTYPE html>
<html lang="it">
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.Beer,it.beermeup.model.Cart,it.beermeup.model.CartProduct"%>

<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html">
	<title>Beer Me Up</title>
	<meta name="viewport" content="widht=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/style.css?ts=<?=time()?>&quot">
	<link rel="stylesheet" href="css/catalog-style.css?ts=<?=time()?>&quot">
	<link rel="stylesheet" href="css/product-card-style.css?ts=<?=time()?>&quot">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js" 
	integrity="sha384-UG8ao2jwOWB7/oDdObZc6ItJmwUkR/PfMyt9Qs5AwX7PsnYn1CRKCTWyncPTWvaS" 
	crossorigin="anonymous">
	</script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>
	<%@ include file="header.jsp" %>
	
	<main>
		<div id="catalog-page">
			<div class="catalog-container">
				<div class="title">
					<h1>Catalogo</h1>
					<h2><%=productsList.size() %> risultati</h2>	
				</div>
				<div class="sort">
					<h1>Ordina per: </h1>
					<h2>
						<span><a href="catalogo_control?action=catalogo&sort=id">Ultimi Arrivi</a> |</span> 
						<span><a href="catalogo_control?action=catalogo&sort=beer_name">Nome</a> |</span>
						<span><a href="catalogo_control?action=catalogo&sort=price">Prezzo</a></span>
					</h2>
					
				</div>
				<%
				if(productsList != null && productsList.size() != 0){
					Iterator<?> it = productsList.iterator();	
					while (it.hasNext()) {
						Beer beer = (Beer) it.next();
				%>
				
						<div class="product-card">
							<div class="top">
								<%if(beer.getDiscount()>0 && beer.getStock()>0){%><span class="discount">-<%=beer.getDiscount()%>%</span><%}%>
								<a href="productDetail_control?action=showProductDetails&id=<%=beer.getId()%>"><img class="image" src="data:image/png;base64,<%=beer.getBase64Image()%>" alt=""></a>
							</div>
							<div class="content">
								<h3 class="name"><%=beer.getName()%></h3>
                				<h4 class="description">Birrificio: <%=breweryMap.get(beer.getBreweryId())%></h4>
							</div>
							<div class="bottom">
								<%
								if(beer.getStock()>0){
								%>
									<div class="price">
										<%
										if(beer.getDiscount()>0){
										%>
											<span class="full-price"><%=beer.getPrice(false)%>€</span>
                    						<span class="discount-price"><%=beer.getPrice(true)%>€</span>
                    					<%
										}
										else{
                    					%>
                    						<span class="normal-price"><%=beer.getPrice()%>€</span>
                    					<%
										}
                    					%>
									</div>
									<div class="card-button">
                    					<a href="catalogo_control?action=addToCart&id=<%=beer.getId()%>"><i class="cart fa fa-cart-plus"></i></a>
                					</div>
								<%
								}
								else{
								%>
									<div class="out-of-stock">
                    					<h3>Esaurito</h3>
                					</div>
								<%
								}
								%>
							</div>
						</div>
				<%
					}
				}
				%>
			</div>
		</div>
	</main>
	
	<%@ include file="footer.jsp" %>
	
	<!--  IMPLEMENTAZIONE EVIDENZIA IMMAGINI AL PASSAGGIO DEL MOUSE CON JQUERY -->
	<script>
$(document).ready(function(){
  $(".product-card").hover(function(){
	  $(this).css("transform","scale(1.1)");
 	 }, function(){
  	$(this).css("transform", "scale(1.0)");
	});
});

</script>
	
</body>
</html>