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
			<h2><%=user.getFirst_name()%>  <%=user.getLast_name()%></h2>
		</div>				
	</main>
	<%@ include file="footer.jsp" %>
</body>
</html>