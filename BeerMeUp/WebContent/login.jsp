
<!DOCTYPE html>
<html>
<head>
		<%@ include file="head.jsp"%>			
	</head>
	
<body>
		
		<div>
			<h1>
				LOGIN PAGE
			</h1>
		</div>

   <form action="./login" method ="post">
   Login
   <input type ="hidden" name="action" value="login">
   	<fieldset>
   		<label for="username">Login </label>
   		<input id="username" type="text" name="username" required >
   		<br>
   		<label for="password">Password</label>
   		<input id="password" type="password" name="password" required>

   		<input type = "submit" value="Login"/>
   		<input type ="submit" value ="Reset"/>
   	</fieldset>
   </form>
   <br>
   <form action="./login" method ="post">
   Sign-in
   <input type ="hidden" name="action" value="sign-in">
   	<fieldset>
   		<label for="email">Email</label>
   		<input id="email" type="email" name="email"required>
   		<br>
   		<label for="password">Password</label>
   		<input id="password" type="password" name="password" required>
   		<br>
   		<label for="cpassword">Conferma Password</label>
   		<input id="cpassword" type="password" name="cpassword" required> <!-- FARE VERIFICA PASSWORD UGUALI? !-->
   		<br>
   		<label for="first_name">Nome</label>
   		<input id="first_name" type="text" name="first_name" required>
   		<br>
   		<label for="last_name">Cognome</label>
   		<input id="last_name" type="text" name="last_name" required>
   		<br>
   		<label for="telephone">Telefono</label>
   		<input id="telephone" type="text" name="telephone" required>
   		<br>
   		<label for="street">Via</label>
   		<input id="street" type="text" name="street" required>
   		<br>
   		<label for="num">Numero Civico</label>
   		<input id="num" type="text" name="num" required>
   		<br>
   		<label for="cap">CAP</label>
   		<input id="cap" type="text" name="cap" required>
   		<br>
   		<label for="city">Città</label>
   		<input id="city" type="text" name="city" required>
   		<br>
   		<label for="nation">Nazione</label>
   		<input id="nation" type="text" name="nation" required>

   		<input type = "submit" value="Sign-in"/>
   		<input type ="submit" value ="Reset"/>
   	</fieldset>
   </form>
   

<%@ include file="footer.jsp" %>
</body>
</html>