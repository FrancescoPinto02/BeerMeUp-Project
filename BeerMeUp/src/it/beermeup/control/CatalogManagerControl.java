package it.beermeup.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.BeerDao;

public class CatalogManagerControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static BeerDao model = new BeerDao();
	static final String BEER_LIST = "beerList";
	static Logger logger = Logger.getLogger(CatalogManagerControl.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
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
				}
				//Rimozione Birra	
				else if(action.equalsIgnoreCase("delete")) {
					int beerId = Integer.parseInt(request.getParameter("beerId"));
					model.doDelete(beerId);
				}
			}			
		}
		catch (SQLException e) {
			CatalogManagerControl.logger.log(Level.WARNING, "Errore Servlet Catalog Manager:");
		}
				
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/catalog-manager.jsp");
		dispatcher.forward(request, response);	
	}
}
