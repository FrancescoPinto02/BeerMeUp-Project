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
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">			
</head>
	
<body>
	<%@ include file="header.jsp" %>
	
	<main>
		<div id="login-page">
			<h1>LOGIN PAGE</h1>
			<%if(request.getSession().getAttribute("invalid-login")!=null){ %>
			<h2 style="color:red;">Credenziali Errate</h2>
			<%	request.getSession().removeAttribute("invalid-login");
			}%>
			
			<div class="login-page-form">
				<h2>Login</h2>
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
			
			<div class="login-page-form">
				<h2>Registrazione</h2>
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
   					<label for="telephone">Telefono</label>
   					<input id="telephone" type="text" name="telephone" required><br>
   					<label for="street">Via</label>
   					<input id="street" type="text" name="street" required><br>
   					<label for="num">Numero Civico</label>
   					<input id="num" type="text" name="num" required><br>
   					<label for="cap">CAP</label>
   					<input id="cap" type="text" name="cap" required><br>
   					<label for="city">Città</label>
   					<input id="city" type="text" name="city" required><br>
   					<label for="nation">Nazione</label>
   					<input id="nation" type="text" name="nation" required><br>
   					<input type = "submit" value="Sign-in"/>
   					<input type ="reset" value ="Reset"/>
   				</form>
			</div>
		</div>
   	</main>
   	
	<%@ include file="footer.jsp" %>
</body>
</html>