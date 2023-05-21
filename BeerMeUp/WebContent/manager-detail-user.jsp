<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <%
    Boolean isAdmin = (Boolean)request.getSession().getAttribute("admin-roles");
	if(isAdmin == null || isAdmin == Boolean.FALSE){
		response.sendRedirect("./login.jsp");
		return;
	}
	
	User users = (User) request.getAttribute("users");
	if(users==null){
		response.sendRedirect("./user_orders_control?action=retrieveAllUsers");
		return;
	}
    %>
    
 
<!DOCTYPE html>
<html lang="it">
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.*"%>
<head>
<meta charset="UTF-8">
	<title>Beer Me Up</title>
	<meta name="viewport" content="widht=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
</head>
<body>
	<%@ include file="header.jsp" %>
	<main>
	<div id="admin-users-pages">		
		<h1>Tutti Gli Utenti</h1>
		<table>
		<caption></caption>
				<tr>
					<th>ID</th>
					<th>E-Mail</th>
					<th>First Name</th>
					<th>Last Name</th>
				</tr>
				
				
			
					<tr>
						<td><%=users.getId()%></td>
						<td><%=users.getEmail()%></td>
						<td><%=users.getFirstName()%></td>
						<td><%=users.getLastName()%></td>
		
			</table><br><br>
		<a href="all-users.jsp">Tutti gli utenti</a>
</div>
</main>

	<%@ include file="footer.jsp" %>
</body>
</html>