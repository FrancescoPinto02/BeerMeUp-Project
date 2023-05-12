package it.beermeup.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.Beer;
import it.beermeup.model.BeerDao;
import it.beermeup.model.Brewery;
import it.beermeup.model.BreweryDao;
import it.beermeup.model.Style;
import it.beermeup.model.StyleDao;

public class ProductDetailControl extends HttpServlet {
	private static final long serialVersionUID = 4604094836457987059L;
	static BeerDao beerModel = new BeerDao();
	static BreweryDao breweryModel = new BreweryDao();
	static StyleDao styleModel = new StyleDao();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
		
		try {
			if (action != null) {
				
				//Dettagli prodotto
				if (action.equalsIgnoreCase("showProductDetails")) {
					int id = Integer.parseInt(request.getParameter("id"));
					if(id<=0) {
						response.sendRedirect("./catalogo.jsp");
						return;
					}
					
					Beer beer = beerModel.doRetrieveByKey(id);
					Brewery brewery = breweryModel.doRetrieveByKey(beer.getBreweryId());
					Style style = styleModel.doRetrieveByKey(beer.getStyleId());
					
					request.removeAttribute("product");
					request.setAttribute("product", beer);
					request.removeAttribute("productBrewery");
					request.setAttribute("productBrewery", brewery);
					request.removeAttribute("productStyle");
					request.setAttribute("productStyle", style);
				}
			}			
		} catch (SQLException e) {
			System.out.println("Errore:" + e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product-detail.jsp");
		dispatcher.forward(request, response);		
	}
}
