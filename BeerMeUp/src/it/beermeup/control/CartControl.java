package it.beermeup.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.BeerDao;
import it.beermeup.model.Cart;

public class CartControl extends HttpServlet {
	private static final long serialVersionUID = 341101359352558610L;
	
	static BeerDao model = new BeerDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Recupero carrello (o creazione)
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if(cart == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		//Azione richiesta
		String action = request.getParameter("action");
		
		try {
			if(action!=null) {
				//Aumenta quantità prodotto
				if (action.equalsIgnoreCase("increaseQta")) {
					int id = Integer.parseInt(request.getParameter("id"));
					cart.addProduct(model.doRetrieveByKey(id));
					request.getSession().setAttribute("cart", cart);
					request.setAttribute("cart", cart);
				}
				
				//Decrementa quantità prodotto
				if (action.equalsIgnoreCase("decreaseQta")) {
					int id = Integer.parseInt(request.getParameter("id"));
					cart.deleteProduct(model.doRetrieveByKey(id),1);
					request.getSession().setAttribute("cart", cart);
					request.setAttribute("cart", cart);
				}
				
				//Rimozione prodotto dal carrello
				else if (action.equalsIgnoreCase("deleteFromCart")) {
					int id = Integer.parseInt(request.getParameter("id"));
					cart.deleteProduct(model.doRetrieveByKey(id));
					request.getSession().setAttribute("cart", cart);
					request.setAttribute("cart", cart);
				}
				
				//Svuota Carrello
				else if(action.equalsIgnoreCase("deleteCart")) {
					request.getSession().removeAttribute("cart");
					request.removeAttribute("cart");
				}			
			}
			
			
		}
		catch(Exception e) {
			System.out.println("Errore:" + e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cart.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
