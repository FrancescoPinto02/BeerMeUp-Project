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
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
</head>
<body>
	<%@ include file="header.jsp" %>
	<main>
		<div id="profile-admin">
			<h1>Gestione Admin: </h1>
			<br><br>

			
		
            		<a href="all-orders.jsp">Tutti gli Ordini</a>
            		<a href="all-users.jsp">Tutti gli Utenti</a>
            		<a href="product-manager.jsp">Modifica catalogo</a>
		</div>				
	</main>
	<%@ include file="footer.jsp" %>
</body>
</html>