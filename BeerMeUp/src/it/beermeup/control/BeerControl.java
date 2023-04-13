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

public class BeerControl extends HttpServlet {
	
	private static final long serialVersionUID = 4068793247747745967L;
	
	static BeerDao model = new BeerDao();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
		//Azione richiesta
		String action = request.getParameter("action");
		
		try {
			if (action != null) {
				//Richiesta prodotto
				if (action.equalsIgnoreCase("retrieveBeer")) {
					int id = Integer.parseInt(request.getParameter("id"));
					request.removeAttribute("product");
					request.setAttribute("product", model.doRetrieveByKey(id));
				}
				
				//Eliminazione prodotto
				else if (action.equalsIgnoreCase("deleteBeer")) {
					int id = Integer.parseInt(request.getParameter("id"));
					model.doDelete(id);
				}
				
				//Inserimento prodotto
				else if (action.equalsIgnoreCase("insertBeer")) {
					int produttore_id = Integer.parseInt(request.getParameter("produttore_id"));
					int stile_id = Integer.parseInt(request.getParameter("stile_id"));
					String nome = request.getParameter("nome");
					String descrizione = request.getParameter("descrizione");
					String colore = request.getParameter("colore");
					String ingredienti = request.getParameter("ingredienti");
					BigDecimal gradazione = new BigDecimal(request.getParameter("gradazione"));
					BigDecimal prezzo = new BigDecimal(request.getParameter("prezzo"));
					BigDecimal iva = new BigDecimal(request.getParameter("iva"));
					int stock = Integer.parseInt(request.getParameter("stock"));
					int sconto = Integer.parseInt(request.getParameter("sconto"));

					Beer bean = new Beer();
					bean.setProduttore_id(produttore_id);
					bean.setStile_id(stile_id);
					bean.setNome(nome);
					bean.setDescrizione(descrizione);
					bean.setColore(colore);
					bean.setIngredienti(ingredienti);
					bean.setGradazione(gradazione);
					bean.setPrezzo(prezzo);
					bean.setIva(iva);
					bean.setStock(stock);
					bean.setSconto(sconto);
					model.doSave(bean);
				}
				
				//Richiesta di tutti i prodotti
				else if(action.equalsIgnoreCase("retrieveAllBeers")) {
					String sort = request.getParameter("sort");
					request.removeAttribute("products");
					request.setAttribute("products", model.doRetrieveAll(sort));
				}
			}			
		} catch (SQLException e) {
			System.out.println("Errore:" + e.getMessage());
		}
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/catalogo.jsp");
		dispatcher.forward(request, response);		
	}

}
