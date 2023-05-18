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
		response.sendRedirect("./user_control?action=change-data");
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
		<div id="user-data-change-page">
			<h1>Modifica Dati</h1>
	
				<form action="change-data">
				<input type ="hidden" name="action" value="change-data">
					<label for="fname">Nome</label>
					<input type="text" id="fname" name="fname" value="<%=user.getFirstName()%>"><br>
					<label for="lname">Cognome</label>
					<input type="text" id="lname" name="lname" value="<%=user.getLastName()%>"><br>
					<label for="email">E-Mail</label>
					<input type="text" id="email" name="email" value="<%=user.getEmail()%>"><br>
					<label for="pw">Password</label>
					<input type="text" id="pw" name="pw" value="<%=user.getPw()%>"><br><br>
					<input type="submit" value="Change">
				</form>
		</div>
	
	</main>
	
	
	
	
	<%@ include file="footer.jsp" %>
</body>
</html>