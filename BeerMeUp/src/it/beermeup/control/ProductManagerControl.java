package it.beermeup.control;

import java.io.IOException;
import java.math.BigDecimal;
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
import it.beermeup.model.Cart;
import it.beermeup.model.Style;
import it.beermeup.model.StyleDao;

public class ProductManagerControl extends HttpServlet {

	private static final long serialVersionUID = -660605298695811857L;
	static BeerDao beerModel = new BeerDao();
	static BreweryDao breweryModel = new BreweryDao();
	static StyleDao styleModel = new StyleDao();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
		
		
		try {
			if (action != null) {
		
				//Richiesta di tutti i prodotti
				if(action.equalsIgnoreCase("insertBeer")) {
					Beer beer = new Beer();
					beer.setName(request.getParameter("beerName"));
					beer.setColor(request.getParameter("beerColor"));
					beer.setGradation(new BigDecimal(request.getParameter("beerGradation")));
					beer.setPrice(new BigDecimal(request.getParameter("beerPrice")));
					beer.setIva(new BigDecimal(request.getParameter("beerIva")));
					beer.setStock(Integer.parseInt(request.getParameter("beerStock")));
					beer.setDiscount(Integer.parseInt(request.getParameter("beerDiscount")));
					beer.setDescription(request.getParameter("beerDescription"));
					beer.setIngredients(request.getParameter("beerIngredients"));
					beer.setBrewery_id(Integer.parseInt(request.getParameter("beerBrewery")));
					beer.setStyle_id(Integer.parseInt(request.getParameter("beerStyle")));
					
					beerModel.doSave(beer);
				}
				
			}			
		} catch (SQLException e) {
			System.out.println("Errore:" + e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product-manager.jsp");
		dispatcher.forward(request, response);		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
		
		
		try {
			if (action != null) {
		
				//Richiesta di tutti i prodotti
				if(action.equalsIgnoreCase("retrieveBreweryStyle")) {
					request.removeAttribute("breweryList");
					request.setAttribute("breweryList", breweryModel.doRetrieveAll("brewery_name"));
					request.removeAttribute("styleList");
					request.setAttribute("styleList", styleModel.doRetrieveAll("style_name"));
				}
				
			}			
		} catch (SQLException e) {
			System.out.println("Errore:" + e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product-manager.jsp");
		dispatcher.forward(request, response);		
	}
}

