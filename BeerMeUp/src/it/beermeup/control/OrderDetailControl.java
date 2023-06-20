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
	static OrderDao Ordermodel = new OrderDao();
	static Logger logger = Logger.getLogger(ProductDetailControl.class.getName());
	
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
				int id = Integer.parseInt(request.getParameter("order-id"));
				request.removeAttribute("order-id");
				request.getSession().setAttribute("order-id", id);
				request.setAttribute("order-id", id);
			}
				
			if (action != null && action.equalsIgnoreCase("retrieveProducts")) {
						
				Integer orderId = (Integer) request.getSession().getAttribute("order-id")	;
				request.removeAttribute("product-list");
				request.setAttribute("product-list",model.doRetrieveByOrder(orderId));
				request.removeAttribute("order");
				request.setAttribute("order", Ordermodel.doRetrieveByKey(orderId));
				}
		
		
		} catch (SQLException e) {
			ProductDetailControl.logger.log(Level.WARNING, "Errore Servlet order Detail Control");
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/order-detail.jsp");
		dispatcher.forward(request, response);		
	}
}
