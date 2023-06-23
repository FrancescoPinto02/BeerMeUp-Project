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
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.*, java.math.BigDecimal"%>

<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html">
	<title>Beer Me Up</title>
	<meta name="viewport" content="widht=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/invoice-style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>	
	<main>
		<div class="invoice">
    		<div class="invoice-header">
      			<div class="logo"><h3><i class="fa fa-beer"></i>BeerMeUp</h3></div>
      			<div class="info">
        			Numero Fattura: #<%=order.getId()%><br>
        			Data: <%=order.getDate()%><br>
      			</div>
    		</div>
    		<div class="invoice-body">
      			<table class="invoice-table">
      			<caption></caption>
        			<thead>
          				<tr>
            				<th>Descrizione</th>
            				<th>Qta</th>
           	 				<th>Prezzo</th>
           	 				<th>Iva</th>
            				<th>Totale</th>
          				</tr>
        			</thead>
        			<tbody>
        				<%
							if (productList != null && productList.size() != 0) {
								Iterator<?> it = productList.iterator();
								while (it.hasNext()) {
									OrderDetails orderdetails = (OrderDetails) it.next();
						%>
          				<tr>
            				<td><%=orderdetails.getDesc()%></td>
            				<td><%=orderdetails.getQta()%></td>
            				<td><%=orderdetails.getPrice().divide(new BigDecimal(orderdetails.getQta()))%>€</td>
            				<td><%=orderdetails.getIva()%>%</td>
            				<td><%=orderdetails.getPrice()%>€</td>
          				</tr>
          				
          				<%
								}
							} 
							else {
						%>
						<tr>
							<td colspan="5">errore</td>
						</tr>
						<%}%>
          				
          				
        			</tbody>
        			<tfoot>
          				<tr>
            				<td colspan="4" class="invoice-total">Totale:</td>
            				<td><%=order.getTotal()%>€</td>
          				</tr>
        			</tfoot>
      			</table>
    		</div>
    		<div class="invoice-footer">
      			<h4>Indirizzo di Spedizione</h4>
      			<h5><%=order.getShippingAddress() %></h5><br><br>
      			<h4>Indirizzo di Fatturazione</h4>
      			<h5><%=order.getBillingAddress()%></h5><br><br>
      			<h4>Metodo di Pagamento</h4>
      			<h5><%=order.getPaymentInfo()%></h5><br><br>
    		</div>
  		</div>
	</main>
</body>
</html>