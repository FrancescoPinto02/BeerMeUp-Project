<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
	if(userId == null || userId.intValue()<=0) {
		response.sendRedirect("./login.jsp");
		return;
	}
	
	Collection<?> ordersList = (Collection<?>) request.getAttribute("orders-list");
	if(ordersList==null){
		response.sendRedirect("./user_orders_control?action=retrieveUserOrders");
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
	<div id="user-orders-page">
			<h1>Ordini</h1>		
			<table>
				<caption></caption>
				<tr>
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
						<td><%=order.getId()%></td>
						<td><%=order.getTotal()%>€</td>
						<td><%=order.getStatus()%></td>
						<td><%=order.getDate()%></td>
					<td><a href="orderdetail_control?action=detailOrder&order-id=<%=order.getId()%>" target="_blank" rel="noopener">Visualizza ordine</a></td>
				 		</tr>
				<%
					}
				} 
				else {
					%>
					<tr>
						<td colspan="7">Nessun ordine effettuato</td>
					</tr>
				<%}%>
			</table><br><br>
	</div>
</main>
<%@ include file="footer.jsp" %>
</body>

</html>