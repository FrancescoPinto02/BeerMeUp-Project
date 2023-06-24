package it.beermeup.control;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import it.beermeup.model.Beer;
import it.beermeup.model.BeerDao;

@MultipartConfig
public class AddProduct extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	static BeerDao beerModel = new BeerDao();
	static Logger logger = Logger.getLogger(AddProduct.class.getName());
	
	//Metodo per convertire il valore di un oggetto Part p in una String
	private String stringValue(Part p) {
		byte[] byteArray = null;
		try {
			byteArray = p.getInputStream().readAllBytes();
		} catch (IOException e) {
			AddProduct.logger.log(Level.WARNING, "Errore Lettura Dati Form Multipart");
		}
		
		return new String(byteArray, StandardCharsets.UTF_8);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Beer beer = new Beer();
		
		for(Part p : request.getParts()) {
			if(p.getName().equals("beerName")) {
				beer.setName(stringValue(p));
			}
			else if(p.getName().equals("beerColor")) {
				beer.setColor(stringValue(p));
			}
			else if(p.getName().equals("beerGradation")) {
				beer.setGradation(new BigDecimal(stringValue(p)));
			}
			else if(p.getName().equals("beerPrice")) {
				beer.setPrice(new BigDecimal(stringValue(p)));
			}
			else if(p.getName().equals("beerIva")) {
				beer.setIva(new BigDecimal(stringValue(p)));
			}
			else if(p.getName().equals("beerStock")) {
				beer.setStock(Integer.parseInt(stringValue(p)));
			}
			else if(p.getName().equals("beerDiscount")) {
				beer.setDiscount(Integer.parseInt(stringValue(p)));
			}
			else if(p.getName().equals("beerDescription")) {
				beer.setDescription(stringValue(p));
			}
			else if(p.getName().equals("beerIngredients")) {
				beer.setIngredients(stringValue(p));
			}
			else if(p.getName().equals("beerBrewery")) {
				beer.setBreweryId(Integer.parseInt(stringValue(p)));
			}
			else if(p.getName().equals("beerStyle")) {
				beer.setStyleId(Integer.parseInt(stringValue(p)));
			}
			else if(p.getName().equals("beerImg")) {
				beer.setInputStreamImage(p.getInputStream());
			}
		}
		
		try {
			beerModel.doSave(beer);
		} 
		catch (SQLException e) {
			AddProduct.logger.log(Level.WARNING, "Errore Servlet AddProduct");
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/catalog-manager.jsp");
		dispatcher.forward(request, response);
	}
}
