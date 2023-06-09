<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
	if(userId == null || userId.intValue()<=0) {
		response.sendRedirect("./login.jsp");
		return;
	}
	
	Cart cart = (Cart) request.getSession().getAttribute("cart");
	if(cart==null || cart.isEmpty()){
		response.sendRedirect("./catalogo.jsp");
		return;
	}
	
	

	Collection<?> addressList = (Collection<?>) request.getAttribute("address-list");
	Collection<?> paymentmethodList = (Collection<?>) request.getAttribute("payment-list");
	if(addressList==null || paymentmethodList==null){
		response.sendRedirect("./checkout_control?action=recupera-dati-checkout");
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
</head>
<body>
	<%@ include file="header.jsp" %>
	<main>
		<div id="checkout-page">
			<h1>Checkout</h1>
			<br><br>
			<form action="./checkout_control" method="post">
				<input type="hidden" name="action" id="action" value="checkout">
				<label for="address">Indirizzo:<a  class="no" href="user-address.jsp">+</a></label><br><br>
				<select name="address" id="address" required>
				<%
				if (addressList != null && addressList.size() != 0) {
					Iterator<?> it = addressList.iterator();
					User user = (User) request.getAttribute("user-info");
					while (it.hasNext()) {
						Address address = (Address) it.next();
				%>
						<option value="<%=user.getFirstName() + " " + user.getLastName() + ", " + address.toString()%>"><%=address.toString()%></option>
				<%
					}
				} 
				%>
				</select>
				<br><br><br>
				<label for="paymentmethod">Metodo di Pagamento:<a class="no" href="user-paymentmethod.jsp">+</a></label><br><br>
				<select name="paymentmethod" id="paymentmethod" required>
				<%	
			
				if (paymentmethodList != null && paymentmethodList.size() != 0) {
					Iterator<?> it = paymentmethodList.iterator();
					while (it.hasNext()) {
						PaymentMethod paymentmethod = (PaymentMethod) it.next();
				%>
						<option value="<%=paymentmethod.toString()%>"><%=paymentmethod.toString()%></option>
				<%
					}
				} 
				%>
				</select>
				<br><br><br>
				<h2>Totale Senza Sconto: <%=cart.getTotalPrice(false)%>€</h2>
				<h2>Totale: <%=cart.getTotalPrice(true)%>€</h2>
				<br><br>
				<input type = "submit" value="Confirm"/>
   				<input type ="reset" value ="Reset"/>
			</form>
		</div>				
	</main>
	<%@ include file="footer.jsp" %>
</body>
</html>