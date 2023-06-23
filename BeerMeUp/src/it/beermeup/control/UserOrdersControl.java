package it.beermeup.control;

import java.io.IOException;
import java.sql.Date;
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
	static UserDao userModel = new UserDao();
	static UserDao modelUser = new UserDao();
	static Logger logger = Logger.getLogger(UserOrdersControl.class.getName());
	static final String ORDERS_LIST = "orders-list";
	static final String USERS_LIST = "users-list";
	static final String ALL_ORDERS = "/all-orders.jsp";
	static final String USERS = "users";
	
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
					request.removeAttribute(ORDERS_LIST);
					request.setAttribute(ORDERS_LIST, model.doRetrieveByUser(userId.intValue()));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-orders.jsp");
					dispatcher.forward(request, response);
				}
				
				if (action.equalsIgnoreCase("retrieveAllOrders"))
				{
					request.removeAttribute(ORDERS_LIST);
					request.setAttribute(ORDERS_LIST, model.doRetrieveAll("user_id"));
					request.removeAttribute(USERS_LIST);
					request.setAttribute(USERS_LIST, userModel.doRetrieveAll(""));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(ALL_ORDERS);
					dispatcher.forward(request, response);
				}

				if (action.equalsIgnoreCase("retrieveAllUsers"))
				{
					request.removeAttribute(USERS_LIST);
					request.setAttribute(USERS_LIST, modelUser.doRetrieveAll("id"));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/all-users.jsp");
					dispatcher.forward(request, response);
				}
				}
			
		}
		catch(Exception e) {
			UserOrdersControl.logger.log(Level.WARNING, "Errore Servlet User Orders Control");
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String action = request.getParameter("action");
		try {
			if(action!=null) {
				if(action.equalsIgnoreCase("retrieveUser")){
					int users = Integer.parseInt(request.getParameter(USERS));
					request.removeAttribute(USERS);
					request.setAttribute(USERS, modelUser.doRetrieveByKey(users));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/manager-detail-user.jsp");
					dispatcher.forward(request, response);
				}
				else if(action.equalsIgnoreCase("searchByDate")) {
					Date fromDate = Date.valueOf(request.getParameter("fromDate"));
					Date toDate = Date.valueOf(request.getParameter("toDate"));
					request.removeAttribute(ORDERS_LIST);
					request.setAttribute(ORDERS_LIST, model.doRetrieveByDates(fromDate, toDate));
					request.removeAttribute(USERS_LIST);
					request.setAttribute(USERS_LIST, userModel.doRetrieveAll(""));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(ALL_ORDERS);
					dispatcher.forward(request, response);
				}
				else if(action.equalsIgnoreCase("searchByUser")) {
					int userId = Integer.parseInt(request.getParameter("user"));
					request.removeAttribute(ORDERS_LIST);
					request.setAttribute(ORDERS_LIST, model.doRetrieveByUser(userId));
					request.removeAttribute(USERS_LIST);
					request.setAttribute(USERS_LIST, userModel.doRetrieveAll(""));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(ALL_ORDERS);
					dispatcher.forward(request, response);
				}
			}
		}
		catch(Exception e) {
			UserOrdersControl.logger.log(Level.WARNING, "Errore Servlet User Orders Control");
		}
	}
	
}