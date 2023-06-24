package it.beermeup.control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.Beer;
import it.beermeup.model.BeerDao;
import it.beermeup.model.BreweryDao;
import it.beermeup.model.StyleDao;

public class CatalogManagerControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static BeerDao model = new BeerDao();
	static BreweryDao breweryModel = new BreweryDao();
	static StyleDao styleModel = new StyleDao();
	static final String BEER_LIST = "beerList";
	static Logger logger = Logger.getLogger(CatalogManagerControl.class.getName());
	static final String BEER_ID = "beerId";
	static final String CATALOG_MANAGER = "/catalog-manager.jsp";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
		
		try {
			if(action!=null) {
				Beer beer = new Beer();
				beer.setId(Integer.parseInt(request.getParameter(BEER_ID)));
				beer.setName(request.getParameter("beerName"));
				beer.setColor(request.getParameter("beerColor"));
				beer.setGradation(new BigDecimal(request.getParameter("beerGradation")));
				beer.setPrice(new BigDecimal(request.getParameter("beerPrice")));
				beer.setIva(new BigDecimal(request.getParameter("beerIva")));
				beer.setStock(Integer.parseInt(request.getParameter("beerStock")));
				beer.setDiscount(Integer.parseInt(request.getParameter("beerDiscount")));
				beer.setDescription(request.getParameter("beerDescription"));
				beer.setIngredients(request.getParameter("beerIngredients"));
				beer.setStyleId(Integer.parseInt(request.getParameter("beerStyle")));
				beer.setBreweryId(Integer.parseInt(request.getParameter("beerBrewery")));
				model.doUpdate(beer);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(CATALOG_MANAGER);
				dispatcher.forward(request, response);	
			}
		}
		catch (SQLException e) {
			CatalogManagerControl.logger.log(Level.WARNING, "Errore Servlet Catalog Manager:");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
						
		try {
			if (action != null) {
						
				//Richiesta di tutti i prodotti
				if(action.equalsIgnoreCase("initialize")) {
					request.removeAttribute(BEER_LIST);
					request.setAttribute(BEER_LIST, model.doRetrieveAll(""));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(CATALOG_MANAGER);
					dispatcher.forward(request, response);	
				}
				//Rimozione Birra	
				else if(action.equalsIgnoreCase("delete")) {
					int beerId = Integer.parseInt(request.getParameter(BEER_ID));
					model.doDelete(beerId);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(CATALOG_MANAGER);
					dispatcher.forward(request, response);	
				}
				//Rimozione Birra	
				else if(action.equalsIgnoreCase("edit")) {
					int beerId = Integer.parseInt(request.getParameter(BEER_ID));
					request.removeAttribute("beer");
					request.setAttribute("beer", model.doRetrieveByKey(beerId));
					request.removeAttribute("breweryList");
					request.setAttribute("breweryList", breweryModel.doRetrieveAll(""));
					request.removeAttribute("styleList");
					request.setAttribute("styleList", styleModel.doRetrieveAll(""));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/edit-product.jsp");
					dispatcher.forward(request, response);	
				}
			}			
		}
		catch (SQLException e) {
			CatalogManagerControl.logger.log(Level.WARNING, "Errore Servlet Catalog Manager:");
		}
	}
}
