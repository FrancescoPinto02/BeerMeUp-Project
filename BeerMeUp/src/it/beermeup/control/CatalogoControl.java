package it.beermeup.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.BeerDao;
import it.beermeup.model.Brewery;
import it.beermeup.model.BreweryDao;
import it.beermeup.model.Cart;

public class CatalogoControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PROD_ATT = "productList";
	static BeerDao model = new BeerDao();
	static BreweryDao breweryModel = new BreweryDao();
	static Logger logger = Logger.getLogger(CatalogoControl.class.getName());
	
	private HashMap<Integer, String> getBreweryMap() throws SQLException {
		ArrayList<Brewery> breweryList =  (ArrayList<Brewery>) breweryModel.doRetrieveAll(null); 
		HashMap<Integer, String> breweryMap = new HashMap<>();
		for(Brewery x : breweryList) {
			breweryMap.put(x.getId(), x.getName());
		}
		
		return breweryMap;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
		
		//Recupero carrello (o creazione)
		Cart cart = (Cart)request.getSession().getAttribute("cart");
			if(cart == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		try {
			if (action != null) {
				
				//Richiesta di tutti i prodotti
				if(action.equalsIgnoreCase("catalogo")) {
					String sort = request.getParameter("sort");
					request.removeAttribute(PROD_ATT);
					request.setAttribute(PROD_ATT, model.doRetrieveAll(sort));
				}
				
				//Richiesta Birre in sconto
				else if(action.equalsIgnoreCase("promo")) {
					request.removeAttribute(PROD_ATT);
					request.setAttribute(PROD_ATT, model.doRetrievePromo());	
				}
				
				//Richiesta Novità
				else if(action.equalsIgnoreCase("new")) {
					request.removeAttribute(PROD_ATT);
					request.setAttribute(PROD_ATT, model.doRetrieveNew());
				}
				
				
				//Aggiungi prodotto al carrello
				else if (action.equalsIgnoreCase("addToCart")) {	
					int id = Integer.parseInt(request.getParameter("id"));
					cart.addProduct(model.doRetrieveByKey(id));
					request.getSession().setAttribute("cart", cart);
					request.setAttribute("cart", cart);
					response.sendRedirect("./cart.jsp");
					return;
				}
				
				request.removeAttribute("breweryMap");
				request.setAttribute("breweryMap", getBreweryMap());
				
			}			
		} catch (SQLException e) {
			CatalogoControl.logger.log(Level.WARNING, "Errore Servlet Address Control:");
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/catalogo.jsp");
		dispatcher.forward(request, response);		
	}
}
