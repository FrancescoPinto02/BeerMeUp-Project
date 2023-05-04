<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="head.jsp"%>
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
                <li><a href="#">Link1</a></li>
                <li><a href="#">Link2</a></li>
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