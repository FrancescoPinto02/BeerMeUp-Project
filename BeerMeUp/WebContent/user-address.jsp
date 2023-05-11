<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
	if(userId == null || userId.intValue()<=0) {
		response.sendRedirect("./login.jsp");
		return;
	}
	
	Collection<?> addressList = (Collection<?>) request.getAttribute("address-list");
	if(addressList==null){
		response.sendRedirect("./address_control?action=retrieveUserAddress");
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
		<div id="user-address-page">
			<h1>Indirizzi</h1>
			<table>
				<caption></caption>
				<tr>
					<th>Via</th>
					<th>Civico</th>
					<th>CAP</th>
					<th>Città</th>
					<th>Nazione</th>
					<th>Telefono</th>
					<th>Action</th>
				</tr>
				<%
				if (addressList != null && addressList.size() != 0) {
					Iterator<?> it = addressList.iterator();
					while (it.hasNext()) {
						Address address = (Address) it.next();
				%>
					<tr>
						<td><%=address.getStreet()%></td>
						<td><%=address.getNum()%></td>
						<td><%=address.getCap()%></td>
						<td><%=address.getCity()%></td>
						<td><%=address.getNation()%></td>
						<td><%=address.getTelephone()%></td>
						<td><a href="address_control?action=deleteAddress&address-id=<%=address.getId()%>">Delete</a></td>
					</tr>
				<%
					}
				} 
				else {
					%>
					<tr>
						<td colspan="7">Nessun Indirizzo Memorizzato</td>
					</tr>
				<%}%>
			</table><br><br>
			
			<h2>Aggiungi Indirizzo</h2>
			<form action="./address_control" method ="post">
				<input type ="hidden" name="action" value="addAddress">
   				<label for="street">Via</label>
   				<input id="street" type="text" name="street" required><br>
   				<label for="num">Civico</label>
  				<input id="num" type="text" name="num" required><br>
   				<label for="cap">CAP</label>
   				<input id="cap" type="text" name="cap" required><br>
   				<label for="city">Città</label>
   				<input id="city" type="text" name="city" required><br>
  				<label for="nation">Nazione</label>
   				<input id="nation" type="text" name="nation" required><br>
   				<label for="telephone">Telefono</label>
   				<input id="telephone" type="text" name="telephone" required><br>
   				<input type = "submit" value="Add"/>
   				<input type ="reset" value ="Reset"/>
   			</form>
		</div>				
	</main>
	<%@ include file="footer.jsp" %>
</body>
</html>