<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%	
	Boolean isAdmin = (Boolean)request.getSession().getAttribute("admin-roles");
	if(isAdmin == null || isAdmin == Boolean.FALSE){
		response.sendRedirect("./login.jsp");
		return;
	}

	Collection<?> beerList = (Collection<?>) request.getAttribute("beerList");
	
	if(beerList==null) {
		response.sendRedirect("./catalogManager?action=initialize");
		return;
	}

%>    
    

<!DOCTYPE html>
<html lang="it"> <%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.beermeup.model.*"%>

<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html">
	<title>Beer Me Up</title>
	<meta name="viewport" content="widht=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="css/catalogManager-style.css?ts=<?=time()?>&quot">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>
	<%@ include file="header.jsp" %>
	
	<main>
		<div id="catalog-manager">
        	<h1>Gestione Catalogo: <a href="product-manager.jsp">+add</a></h1>
        	<div class="card-container">
            	<%
				if (beerList != null && beerList.size() != 0) {
					Iterator<?> beerIt = beerList.iterator();
					while (beerIt.hasNext()) {
						Beer beer = (Beer) beerIt.next();
				%>
            	<div class="card">
                	<div class="card-header">
                       	<a href="productDetail_control?action=showProductDetails&id=<%=beer.getId()%>"><i class="fa fa-eye"></i></a>
                       	<a href="catalogManager?action=edit&beerId=<%=beer.getId()%>"><i class="fa fa-pencil"></i></a>
                        <a href="catalogManager?action=delete&beerId=<%=beer.getId()%>"><i class="fa fa-close"></i></a>
                	</div>
                	<div class="card-body">
                    	<div class="img">
                        	<img src="data:image/png;base64,<%=beer.getBase64Image()%>" alt="beer">
                    	</div>
                	</div>
                	<div class="card-footer">
                    	<h3>Codice: #<%=beer.getId()%></h3>
                    	<h3><%=beer.getName()%></h3>
                	</div>
            	</div>
            	<%	}
				}%>
        	</div>
    	</div> 
	</main>
	
	<%@ include file="footer.jsp" %>
</body>
</html>