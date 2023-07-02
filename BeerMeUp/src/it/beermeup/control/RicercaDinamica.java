package it.beermeup.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import it.beermeup.model.Beer;
import it.beermeup.model.BeerDao;


@WebServlet("/RicercaDinamica")
public class RicercaDinamica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static BeerDao model = new BeerDao();
	static Beer modelBeer = new Beer();
	static Logger logger = Logger.getLogger(RicercaDinamica.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String name=request.getParameter("val");
		if(name==null|| name.trim().equals(""))
		{
			
		}
		else {
			
			try {
				Collection<Beer> collection = model.doRetrieveByNameDynamic(name);
				if (collection != null && collection.size() != 0) {
					Iterator <Beer> iterator = collection.iterator();
					while (iterator.hasNext()) {
					Beer beer = (Beer) iterator.next();
					out.print("<a href='productDetail_control?action=showProductDetails&id="+beer.getId()+"'>"+ beer.getName()+"</a><br>");
					
				}
					
					
					}
					 
				
				
			} catch (SQLException e) {
				
				RicercaDinamica.logger.log(Level.WARNING, "Errore Servlet Ricerca Dinamica :");
			}
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
