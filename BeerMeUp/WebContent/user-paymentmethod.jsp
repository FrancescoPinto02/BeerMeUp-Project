<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
	if(userId == null || userId.intValue()<=0) {
		response.sendRedirect("./login.jsp");
		return;
	}
	
	Collection<?> paymentmethodList = (Collection<?>) request.getAttribute("payment-list");
	if(paymentmethodList==null){
		response.sendRedirect("./paymentmethod_control?action=retrieveUserPayment");
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
		<div id="user-payment-page">
			<h1>Metodi Di Pagamento</h1>
			<table>
				<caption></caption>
				<tr>
					<th>Proprietario</th>
					<th>Numero Della Carta</th>
					<th>CVV</th>
					<th>Data Di Scadenza</th>
				</tr>
				<%
				if (paymentmethodList != null && paymentmethodList.size() != 0) {
					Iterator<?> it = paymentmethodList.iterator();
					while (it.hasNext()) {
						PaymentMethod paymentmethod = (PaymentMethod) it.next();
				%>
					<tr>
						<td><%=paymentmethod.getOwner()%></td>
						<td><%=paymentmethod.getNumber()%></td>
						<td><%=paymentmethod.getCvv()%></td>
						<td><%=paymentmethod.getExpirationDate()%></td>
						<td><a href="paymentmethod_control?action=deletePaymentMethod&paymentmethod-id=<%=paymentmethod.getId()%>">Delete</a></td>
					</tr>
				<%
					}
				} 
				else {
					%>
					<tr>
						<td colspan="7">Nessun Metodo Di Pagamento Memorizzato</td>
					</tr>
				<%}%>
			</table><br><br>
			<div class="add-paymentmethod-form">
			<h2>Aggiungi Metodo di Pagamento</h2>
			<form action="./paymentmethod_control" method ="post">
				<input type ="hidden" name="action" value="addPaymentMethod">
   				<label for="card_owner">Proprietario</label>
   				<input id="card_owner" type="text" name="card_owner" placeholder="Proprietario" required><br>
   				<label for="card_number">Numero Della Carta</label>
  				<input id="card_number" type="text" name="card_number" placeholder="Card Number" required><br>
   				<label for="cvv">CVV</label>
   				<input id="cvv" type="text" name="cvv" placeholder="CVV"required><br>
   				<label for="expiration">Data di Scadenza</label>
   				<input id="expiration" type="date" name="expiration" placeholder="Expiration Date" required><br>
   				<input type = "submit" value="Add"/>
   				<input type ="reset" value ="Reset"/>
   			</form>
   			</div>
		</div>				
	</main>
	<%@ include file="footer.jsp" %>
</body>
</html>