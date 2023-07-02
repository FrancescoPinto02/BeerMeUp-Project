<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <%
    
    Boolean isAdmin = (Boolean)request.getSession().getAttribute("admin-roles");
	if(isAdmin == null || isAdmin == Boolean.FALSE){
		response.sendRedirect("./login.jsp");
		return;
	}
    
    Collection<?> ordersList = (Collection<?>) request.getAttribute("orders-list");
    Collection<?> usersList = (Collection<?>) request.getAttribute("users-list");
	if(ordersList==null || usersList==null){
		response.sendRedirect("./user_orders_control?action=retrieveAllOrders");
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
	<div id="admin-orders-pages">
		<h1>Tutti Gli Ordini: <span><a href="./user_orders_control?action=retrieveAllOrders">all</a></span></h1><br><br>
		
		<form action="./user_orders_control" method ="post">
				<h3>Ricerca per Data</h3>
				<input type="hidden" id="action" name="action" value="searchByDate">
				<label for="fromDate">Da:</label>
				<input type="date" id="fromDate" name="fromDate" required>
				<label for="toDate"><br>A:</label>
				<input type="date" id="toDate" name="toDate" required>
				<br>
				<input type="submit">
		</form><br><br>
		
		<form action="./user_orders_control" method ="post">
				<h3>Ricerca per Utente</h3>
				<input type="hidden" id="action" name="action" value="searchByUser">
				<label for="user">Utente:</label>
				<select name="user" id="user" required>
					<%
					if (usersList != null && usersList.size() != 0) {
						Iterator<?> usersIt = usersList.iterator();
						while (usersIt.hasNext()) {
							User user = (User) usersIt.next();
						%>
						<option value="<%=user.getId()%>"><%=user.getEmail()%></option>
						<%
						}
					} 
					%>
				</select>
				<input type="submit">
		</form><br><br>
			
		<table>
				<caption></caption>
				<tr>
					<th>User ID</th>
					<th>ID</th>
					<th>Total</th>
					<th>Status</th>
					<th>Date</th>
				</tr>
				<%
				if (ordersList != null && ordersList.size() != 0) {
					Iterator<?> it = ordersList.iterator();
					while (it.hasNext()) {
						Order order = (Order) it.next();
				%>
					<tr>
						<td><%=order.getUserId()%></td>
						<td><%=order.getId()%></td>
						<td><%=order.getTotal()%>€</td>
						<td><%=order.getStatus()%></td>
						<td><%=order.getDate()%></td>
						<td><a href="orderdetail_control?action=detailOrder&order-id=<%=order.getId()%>" target="_blank" rel="noopener">Visualizza ordine</a></td>
			<%
					}
				} 
				else {
					%>
					<tr>
						<td colspan="7">Nessun ordine presente</td>
					</tr>
				<%}%>
			</table><br><br>
			
			
</div>
</main>
<%@ include file="footer.jsp" %>
</body>
</html>