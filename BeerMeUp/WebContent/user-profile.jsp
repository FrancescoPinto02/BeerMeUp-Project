<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
	if(userId == null || userId.intValue()<=0) {
		response.sendRedirect("./login.jsp");
		return;
	}
	
	User user = (User) request.getAttribute("user-info");
	if(user == null) {
		response.sendRedirect("./user_control?action=retrieveUserInfo");
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
	<link rel="stylesheet" href="css/card.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
</head>
<body>
	<%@ include file="header.jsp" %>
	<main>
		<div id="profile-page">
			<h1>Profilo Utente: <span><a href="./logout"> Logout</a></span></h1>
			<h2><%=user.getFirstName()%>  <%=user.getLastName()%></h2>
			<br><br>
			
			<div class="card-container">
        		<div class="card">
            		<a href="user-address.jsp">
                		<div class="card-content">
                    		<div class="image">
                        		<img src="img/address.png" alt="address">
                    		</div>
                   		 	<div class="title">
                        		<h3>I miei Indirizzi</h3>
                    		</div>
                		</div>
            		</a>
        		</div>
        		<div class="card">
            		<a href="user-paymentmethod.jsp">
                		<div class="card-content">
                    		<div class="image">
                        		<img src="img/card.png" alt="card">
                    		</div>
                   		 	<div class="title">
                        		<h3>I miei Metodi<br>di Pagamento</h3>
                    		</div>
                		</div>
            		</a>
        		</div>
        		<div class="card">
            		<a href="user-orders.jsp">
                		<div class="card-content">
                    		<div class="image">
                        		<img src="img/order.png" alt="order">
                    		</div>
                   		 	<div class="title">
                        		<h3>I miei ordini</h3>
                    		</div>
                		</div>
            		</a>
        		</div>
        		<div class="card">
            		<a href="user-data.jsp" target="_blank">
                		<div class="card-content">
                    		<div class="image">
                        		<img src="img/user.png" alt="user">
                    		</div>
                   		 	<div class="title">
                        		<h3>I miei Dati</h3>
                    		</div>
                		</div>
            		</a>
        		</div>    
    		</div>
		</div>				
	</main>
	<%@ include file="footer.jsp" %>
</body>
</html>