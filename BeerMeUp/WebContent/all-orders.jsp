<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <%
    
    Boolean isAdmin = (Boolean)request.getSession().getAttribute("admin-roles");
	if(isAdmin == null || isAdmin == Boolean.FALSE){
		response.sendRedirect("./home.jsp");
		return;
	}
    
    Collection<?> ordersList = (Collection<?>) request.getAttribute("orders-list");
	if(ordersList==null){
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
		<h1>Tutti Gli Ordini</h1>
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