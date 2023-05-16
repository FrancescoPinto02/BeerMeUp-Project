<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
	if(userId == null || userId.intValue()<=0) {
		response.sendRedirect("./login.jsp");
		return;
	}
	
	User user = (User) request.getAttribute("user-info");
	if(user == null) {
		response.sendRedirect("./user_control?action=retrieveUserInfo");
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
		<div id="profile-page">
			<h1>Profilo Utente: <span><a href="./logout"> Logout</a></span></h1>
			<h2><%=user.getFirstName()%>  <%=user.getLastName()%></h2>
			<br><br>
			<a href="user-address.jsp">I miei Indirizzi</a>
			<a href="user-orders.jsp">I miei ordini</a>
			
			<%	Boolean isAdmin = (Boolean)request.getSession().getAttribute("admin-roles");
        		if(adminRoles != null && adminRoles == Boolean.TRUE){ %>
            		<a href="all-orders.jsp">Tutti gli Ordini</a>
            		<a href="all-users.jsp">Tutti gli Utenti</a>
            <%	}%>
			
		</div>				
	</main>
	<%@ include file="footer.jsp" %>
</body>
</html>