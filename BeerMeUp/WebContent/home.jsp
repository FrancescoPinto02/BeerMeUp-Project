<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html">
		<title>Beer Me Up</title>
		<meta name="viewport" content="widht=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/style.css">
		<link rel="stylesheet" href="css/home-style.css?ts=<?=time()?>&quot">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
	</head>
	
	
	<body>
		<%@ include file="header.jsp" %>
		<main>
			
			<div class="hero-section">
				<div class="hero-text">
					<h1>La tua birra di fiducia</h1><br>
					<p>ORDINA / APRI / GUSTA<br></p><br><br>
					<a href="catalogo.jsp"> Scopri i prodotti</a>
				</div>
			</div>
					
		</main> 	
		
		<%@ include file="footer.jsp" %>
	</body>
</html>