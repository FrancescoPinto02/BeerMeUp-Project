<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html">
	<title>Beer Me Up</title>
	<meta name="viewport" content="widht=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/header-style.css">
</head>
<body>
	<header>
        <input type="checkbox" name="" id="toggler">
        <label for="toggler" class="fa fa-bars"></label>

        <a href="home.jsp" class="logo"><i class="fa fa-beer"></i>BeerMeUp</a>

        <nav>
            <ul>
                <li><a href="home.jsp"><i class="fa fa-fw fa-home"></i>Home</a></li>
                <li><a href="catalogo.jsp">Catalogo</a></li>
                <li><a href="catalogo_control?action=promo">Promo</a></li>
                <li><a href="catalogo_control?action=new">Novità</a></li>
            </ul>
        </nav>

        <div class="icons">
        	<%	Boolean adminRoles = (Boolean)request.getSession().getAttribute("admin-roles");
        		if(adminRoles != null && adminRoles == Boolean.TRUE){ %>
            		<a href="product-manager.jsp" class="fa fa-wrench"></a>
            <%	}%>
            <a href="login.jsp" class="fa fa-user"></a>
            <a href="cart.jsp" class="fa fa-shopping-cart"></a>
        </div>
    </header>
</body>
</html>