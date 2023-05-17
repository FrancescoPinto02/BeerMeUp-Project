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
import it.beermeup.model.UserDao;

public class UserOrdersControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static OrderDao model = new OrderDao();
	static UserDao modelUser = new UserDao();
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
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-orders.jsp");
					dispatcher.forward(request, response);
				}
				
				if (action.equalsIgnoreCase("retrieveAllOrders"))
				{
					request.removeAttribute("orders-list");
					request.setAttribute("orders-list", model.doRetrieveAll("user_id"));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/all-orders.jsp");
					dispatcher.forward(request, response);
				}

				if (action.equalsIgnoreCase("retrieveAllUsers"))
				{
					request.removeAttribute("users-list");
					request.setAttribute("users-list", modelUser.doRetrieveAll("id"));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/all-users.jsp");
					dispatcher.forward(request, response);
				}
				}
			
		}
		catch(Exception e) {
			AddressControl.logger.log(Level.WARNING, "Errore Servlet Address Control");
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}