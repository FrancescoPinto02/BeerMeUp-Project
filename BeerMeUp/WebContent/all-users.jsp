<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <%
    Boolean isAdmin = (Boolean)request.getSession().getAttribute("admin-roles");
	if(isAdmin == null || isAdmin == Boolean.FALSE){
		response.sendRedirect("./home.jsp");
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
				<%
				UserDao userModel = new UserDao();
				Collection<User> utenti = userModel.doRetrieveAll("user-id");
				if (utenti != null && utenti.size() != 0) {
					Iterator<?> it = utenti.iterator();
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
</main>

	<%@ include file="footer.jsp" %>
</body>
</html>