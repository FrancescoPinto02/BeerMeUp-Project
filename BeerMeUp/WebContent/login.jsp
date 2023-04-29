<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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

   <form action="Login" method ="post">
   	<fieldset>
   		<label for="username">Login </label>
   		<input id="username" type="text" name="username" >
   		<br>
   		<label for="password">Password</label>
   		<input id="password" type="password" name="password">
   		<input type = "submit" value="Login"/>
   		<input type ="submit" value ="Reset"/>
   	</fieldset>
   </form>

<%@ include file="footer.jsp" %>
</body>
</html>