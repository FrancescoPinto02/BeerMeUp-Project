package it.beermeup.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.BeerDao;
import it.beermeup.model.BreweryDao;
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
				
				//Rimozione Birra
				if(action.equalsIgnoreCase("deleteBeer")) {
					
					int beerId = Integer.parseInt(request.getParameter("beerId"));
					beerModel.doDelete(beerId);
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
				if(action.equalsIgnoreCase("initialize")) {
					request.removeAttribute("breweryList");
					request.setAttribute("breweryList", breweryModel.doRetrieveAll("brewery_name"));
					request.removeAttribute("styleList");
					request.setAttribute("styleList", styleModel.doRetrieveAll("style_name"));
					request.removeAttribute("beerList");
					request.setAttribute("beerList", beerModel.doRetrieveAll("beer_name"));
				}
				
			}			
		} catch (SQLException e) {
			System.out.println("Errore:" + e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product-manager.jsp");
		dispatcher.forward(request, response);		
	}
}

