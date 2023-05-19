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

import it.beermeup.model.Order;
import it.beermeup.model.OrderDao;



public class OrderDetailControl extends HttpServlet {
	private static final long serialVersionUID = 4604094836457987059L;
	static OrderDao orderModel = new OrderDao();
	
	static Logger logger = Logger.getLogger(ProductDetailControl.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
		
		try {
			if (action != null) {
				
				//Dettagli Ordine
				int id = Integer.parseInt(request.getParameter("id"));
				if(id<=0) {
					response.sendRedirect("./user-orders.jsp");
					return;
				}
					
				Order order = orderModel.doRetrieveByKey(id);
				
					
				request.removeAttribute("order");
				request.setAttribute("order", order);
		
			}			
		} catch (SQLException e) {
			ProductDetailControl.logger.log(Level.WARNING, "Errore Servlet Product Detail Control");
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/order-detail.jsp");
		dispatcher.forward(request, response);		
	}
}
