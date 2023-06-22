<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	
	Boolean isAdmin = (Boolean)request.getSession().getAttribute("admin-roles");
	if(isAdmin == null || isAdmin == Boolean.FALSE){ 
		response.sendRedirect("./login.jsp");
		return;
	}
%>    
    

<!DOCTYPE html>
<html lang="it">
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.*"%>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html">
	<title>Beer Me Up</title>
	<meta name="viewport" content="widht=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="css/card.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
</head>
<body>
	<%@ include file="header.jsp" %>
	<main>
		<div id="profile-admin">
			<h1>Profilo Amministratore:<span><a href="./logout"> Logout</a></span></h1>
			<br><br>
			<div class="card-container">
        		<div class="card">
            		<a href="all-orders.jsp">
                		<div class="card-content">
                    		<div class="image">
                        		<img src="img/order.png" alt="order">
                    		</div>
                   		 	<div class="title">
                        		<h3>Tutti Gli Ordini</h3>
                    		</div>
                		</div>
            		</a>
        		</div>
        		<div class="card">
            		<a href="all-users.jsp">
                		<div class="card-content">
                    		<div class="image">
                        		<img src="img/user.png" alt="user">
                    		</div>
                   		 	<div class="title">
                        		<h3>Tutti Gli Utenti</h3>
                    		</div>
                		</div>
            		</a>
        		</div>
        		<div class="card">
            		<a href="product-manager.jsp">
                		<div class="card-content">
                    		<div class="image">
                        		<img src="img/beer.png" alt="beer">
                    		</div>
                   		 	<div class="title">
                        		<h3>Modifica Catalogo</h3>
                    		</div>
                		</div>
            		</a>
        		</div>   
    		</div>
		</div>				
	</main>
	<%@ include file="footer.jsp" %>
</body>
</html>