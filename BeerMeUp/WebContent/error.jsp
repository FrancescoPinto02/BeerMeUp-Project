<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="it">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html">
		<title>Beer Me Up</title>
		<meta name="viewport" content="widht=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/style.css">
		<link rel="stylesheet" href="css/error-style.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
	</head>
	
	
	<body>
		<main>
			<div id="err-page">
        		<div class="logo">  
            		<h1><i class="fa fa-beer"></i>BeerMeUp</h1>
        		</div>
        		<div class="err-info">
           	 		<h2>Oops!</h2>
           	 		<h2>Si è verificato un problema...</h2>
           	 		<% if(response.getStatus() == 500){ %>
           	 			<h2>Errore: <%=exception.getMessage()%></h2>
           	 		<%}else {%>
            			<h2>Codice Errore: <%=response.getStatus() %> </h2>
            		<%} %>
        		</div>
        		<div class="err-page-button">
            		<a href="home.jsp">Torna alla Home</a>
        		</div>
    		</div>		
		</main> 	
	</body>
</html>