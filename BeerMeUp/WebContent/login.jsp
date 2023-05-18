<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
	if(userId != null && userId.intValue()>0) {
		response.sendRedirect("./user-profile.jsp");
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
	<link rel="stylesheet" href="css/login.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">			
</head>
	
<body>
	<%@ include file="header.jsp" %>
	
	<main>
		<div class ="login" id="login-page">
			<h1></h1>
			<%if(request.getSession().getAttribute("invalid-login")!=null){ %>
			<h2 style="color:red;">Credenziali Errate</h2>
			<%	request.getSession().removeAttribute("invalid-login");
			}%>
			<div class="login-page-form-container">
			<div class="login-page-form" id="form1">
				<h2>Login</h2>
				<br>
				<form action="./login" method ="post">
					<input type ="hidden" name="action" value="login">
   					<label for="email">E-mail </label>
   					<input id="email" type="text" name="email" required ><br>
   					<label for="password">Password</label>
   					<input id="password" type="password" name="password" required><br>
					<input type = "submit" value="Login"/>
   					<input type ="reset" value ="Reset"/>
   				</form>
			</div>
			
			<div class="login-page-form" id="form2">
				<h2>Registrazione</h2>
				<br>
				<form action="./login" method ="post">
					<input type ="hidden" name="action" value="sign-in">
					<label for="email">Email</label>
   					<input id="email" type="email" name="email"required><br>
   					<label for="password">Password</label>
   					<input id="password" type="password" name="password" required><br>
   					<label for="cpassword">Conferma Password</label>
   					<input id="cpassword" type="password" name="cpassword" required><br> <!-- FARE VERIFICA PASSWORD UGUALI? !-->
   					<label for="first_name">Nome</label>
   					<input id="first_name" type="text" name="first_name" required><br>
   					<label for="last_name">Cognome</label>
   					<input id="last_name" type="text" name="last_name" required><br>
   					<input type = "submit" value="Sign-in"/>
   					<input type ="reset" value ="Reset"/>
   				</form>
			</div>
			</div>
			<div class="homer-bender"><img class="homer-bender-photo" alt="homer-bender" src="img/homer-bender.png"></div>
		</div>
   	</main>
   	
	<%@ include file="footer.jsp" %>
</body>
</html>