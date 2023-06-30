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
	<link rel="stylesheet" href="css/style.css?ts=<?=time()?>&quot">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
	<script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/app-ajax.js" type="text/javascript"></script>		
</head>
	
<body>
	<%@ include file="header.jsp" %>
	
	<main>
		<div id="login-page">
			<h1></h1>
			<%if(request.getSession().getAttribute("invalid-login")!=null){ %>
			<h2 style="color:red; margin-top: 1rem">Credenziali Errate</h2>
			<%	request.getSession().removeAttribute("invalid-login");
			}%>
			<div class="login-page-form-container">
			<div class="login-page-form" id="form1">
				<h2>Login</h2>
				<br>
				<form action="./login" method ="post" onsubmit="event.preventDefault();validate(this)">
					<input type ="hidden" name="action" value="login">
   					<label for="email">E-mail </label>
   					<input id="email1" type="text" name="email1" placeholder="example@mail.com"required >  <p id =p_email1> </p><br>
   					<label for="password">Password</label>
   					<input id="password" type="password" name="password"placeholder="Password" required><br>
					<input type = "submit" value="Login" />
   					<input type ="reset" value ="Reset"/>
   				</form>
			</div>
			
			<div class="login-page-form" id="form2">
				<h2>Registrazione</h2>
				<br>
				<form action="./login" method ="post" onsubmit="event.preventDefault();validate1(this)">
					<input type ="hidden" name="action" value="sign-in">
					<label for="first_name">Nome</label>
   					<input id="first_name" type="text" name="first_name"placeholder="Name" required><br>
   					<label for="last_name">Cognome</label>
   					<input id="last_name" type="text" name="last_name" placeholder="Surname"required><br>
					<label for="email">Email</label>
   					<input id="email2" type="email" name="email2"placeholder="example@mail.com"  onblur="CheckEmail()" required>  <p id =p_email2> </p><br>
   					<label for="password">Password</label>
   					<input id="password" type="password" name="password"placeholder="Password" required><br>
   					<label for="cpassword">Conferma Password</label>
   					<input id="cpassword" type="password" name="cpassword"placeholder="Password" required><p id =p_password> </p><br> <!-- FARE VERIFICA PASSWORD UGUALI? !-->
   					<input type = "submit" value="Sign-in" />
   					<input type ="reset" value ="Reset"/>
   				</form>
			</div>
			</div>
			  <div class="homer-bender"><img class="homer-bender-photo" alt="homer-bender" src="img/homer-bender.png"></div>
		</div>
   	</main>
   	
	<%@ include file="footer.jsp" %>
	
	<script type="text/javascript">
	
	function validateEmail(uemail)
	{
		let mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if(uemail.value.match(mailformat))
			{
			return true;
			}
		else
			{
			uemail.focus();
		return false;
		}
	}
	
	function validate(obj)  //VALIDATION LOGIN
	{
	let valid = true;
	alert(valid);
	let email=document.getElementsByName("email1")[0];
	if(!validateEmail(email))
		{
		valid = false;
		email.classList.add("error");
		document.getElementById("p_email1").innerHTML ="Errore formato";
		}
	else {email.classList.remove("error");}
	
	if(valid) obj.submit();
	}
	
	function validate1(obj)  //VALIDATION REGISTRAZIONE
	{
		
	let valid = true;
	
	let email=document.getElementsByName("email2")[0];
	if(!validateEmail(email))
		{
		valid = false;
		email.classList.add("error");
		document.getElementById("p_email2").innerHTML ="Errore formato";
		}
	
	let p1 = document.getElementsByName("password")[1];
	let p2 = document.getElementsByName("cpassword")[0];
	
	if(p2.value!=p1.value)
	{
		valid = false;
		email.classList.add("error");
		document.getElementById("p_password").innerHTML ="Password diverse";
	}
	else {email.classList.remove("error");}
	
	if(valid) obj.submit();
	}
	
	function CheckEmail() {
	$(document).ready(function(evt){ 
		$("#email2").blur(function() {
			
			$.ajax({
				url : "./check_email",
				data : {
					email : $("#email2").val()
				},
				success : function(responseText) {
					if(responseText=="true")
						{
						document.getElementById("p_email2").innerHTML ="Email già esistente";
						}
					else{
					document.getElementById("p_email2").innerHTML ="";
					}
				}
			});
		});
	});

	}
	</script>
</body>
</html>