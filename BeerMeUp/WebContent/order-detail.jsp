<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 	
	Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
	if(userId == null || userId.intValue()<=0) {
	response.sendRedirect("./login.jsp");
	return;
	}	

	Collection<?> productList = (Collection<?>) request.getAttribute("product-list");
	Order order = (Order) request.getAttribute("order");
	if(productList==null ||order==null ){
	response.sendRedirect("./orderdetail_control?action=retrieveProducts");
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
	<%@ include file="header.jsp"%>
	
	<main>
		<div id=order-page>
			<h1>Dettagli Ordine</h1>
			<table>
				<caption></caption>
				<tr>
					<th>ID Birra</th>
					<th>Descrizione</th>
					<th>Quantita'</th>
					<th>Iva</th>
					<th>Prezzo</th>
				
				</tr>
				<%
				if (productList != null && productList.size() != 0) {
					Iterator<?> it = productList.iterator();
					while (it.hasNext()) {
						OrderDetails orderdetails = (OrderDetails) it.next();
				%>
					<tr>
						<td><%=orderdetails.getBeerId()%></td>
						<td><%=orderdetails.getDesc()%></td>
						<td><%=orderdetails.getQta()%></td>
						<td><%=orderdetails.getIva()%>%</td>
						<td><%=orderdetails.getPrice()%></td>
						<td><a href="productDetail_control?action=showProductDetails&id=<%=orderdetails.getBeerId()%>">Visualizza prodotto</a></td>
					</tr>
			<%
					}
				} 
				else {
					%>
					<tr>
						<td colspan="7">errore</td>
					</tr>
				<%}%>
			</table><br><br>
			
			<table>
			<caption></caption>
			<tr>
				<th>Indirizzo di spedizione</th>
				<th>Metodo di pagamento</th>
			</tr>
			<tr>
				<td><%=order.getShippingAddress()%></td>
				<td><%=order.getPaymentInfo()%></td>
			</tr>
			</table>
		</div>
	</main>
	
	<%@ include file="footer.jsp" %>
</body>
</html>