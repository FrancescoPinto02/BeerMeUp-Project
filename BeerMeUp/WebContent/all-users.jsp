<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <%
    Boolean isAdmin = (Boolean)request.getSession().getAttribute("admin-roles");
	if(isAdmin == null || isAdmin == Boolean.FALSE){
		response.sendRedirect("./login.jsp");
		return;
	}
	
	Collection<?> usersList = (Collection<?>) request.getAttribute("users-list");
	if(usersList==null){
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
		<div class="scroll-table">
		<table>
		<caption></caption>
				<tr>
					<th>ID</th>
					<th>E-Mail</th>
					<th>First Name</th>
					<th>Last Name</th>
				</tr>
				<%
				
				if (usersList != null && usersList.size() != 0) {
					Iterator<?> it = usersList.iterator();
					while (it.hasNext()) {
						User user = (User) it.next(); %>
			
					<tr>
						<td><%=user.getId()%></td>
						<td><%=user.getEmail()%></td>
						<td><%=user.getFirstName()%></td>
						<td><%=user.getLastName()%></td>
			<%
					}
				} 
				else {
					%>
					<tr>
						<td colspan="7">Nessun utente registrato</td>
					</tr>
				<%}%>
			</table><br><br>
		</div>
		<form action="./user_orders_control" method="post">
		<input type ="hidden" name="action" value="retrieveUser">
		<select name="users" id="users" required>
					<%
					if (usersList != null && usersList.size() != 0) {
						Iterator<?> userIt = usersList.iterator();
						while (userIt.hasNext()) {
							User user = (User) userIt.next();
						%>
						<option value="<%=user.getId()%>"><%=user.getEmail()%></option>
						<%
						}
					} 
					%>
		</select>
		<input type="submit" value="Submit">
		</form>
</div>
</main>

	<%@ include file="footer.jsp" %>
</body>
</html>