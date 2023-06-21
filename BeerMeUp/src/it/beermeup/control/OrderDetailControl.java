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

import it.beermeup.model.OrderDao;
import it.beermeup.model.OrderDetailsDao;



public class OrderDetailControl extends HttpServlet {
	private static final long serialVersionUID = 4604094836457987059L;
	static OrderDetailsDao model = new OrderDetailsDao();
	static OrderDao orderModel = new OrderDao();
	static Logger logger = Logger.getLogger(OrderDetailControl.class.getName());
	
	static final String ORDER_ID = "order-id";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");

		Integer userId = (Integer)(request.getSession().getAttribute("user-id"));
		if(userId == null || userId.intValue()<=0) {
			response.sendRedirect("./login.jsp");
			return;
		}
			
		
		try {
			if (action != null && action.equalsIgnoreCase("detailOrder")) {
				int id = Integer.parseInt(request.getParameter(ORDER_ID));
				request.removeAttribute(ORDER_ID);
				request.getSession().setAttribute(ORDER_ID, id);
				request.setAttribute(ORDER_ID, id);
			}
				
			if (action != null && action.equalsIgnoreCase("retrieveProducts")) {
						
				Integer orderId = (Integer) request.getSession().getAttribute(ORDER_ID)	;
				request.removeAttribute("product-list");
				request.setAttribute("product-list",model.doRetrieveByOrder(orderId));
				request.removeAttribute("order");
				request.setAttribute("order", orderModel.doRetrieveByKey(orderId));
				}
		
		
		} catch (SQLException e) {
			ProductDetailControl.logger.log(Level.WARNING, "Errore Servlet order Detail Control");
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/order-detail.jsp");
		dispatcher.forward(request, response);		
	}
}
