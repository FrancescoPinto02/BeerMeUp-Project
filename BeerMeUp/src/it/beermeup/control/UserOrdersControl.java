package it.beermeup.control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.OrderDao;

public class UserOrdersControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String PROD_ATT = "ordersList";
	
	static OrderDao model = new OrderDao();
	static Logger logger = Logger.getLogger(UserOrdersControl.class.getName());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
		
		Integer userId = (Integer)(request.getSession().getAttribute("user-id"));
		if(userId == null || userId.intValue()<=0) {
			response.sendRedirect("./login.jsp");
			return;
		}
		
		try {
			if(action!=null) {
				//Recupera tutti gli ordini dell'utente
				if (action.equalsIgnoreCase("retrieveUserOrders")) {
					request.removeAttribute("orders-list");
					request.setAttribute("orders-list", model.doRetrieveByUser(userId.intValue()));
				}
		
			}
			
		}
		catch(Exception e) {
			AddressControl.logger.log(Level.WARNING, "Errore Servlet Address Control");
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-orders.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
		return;
	}
}