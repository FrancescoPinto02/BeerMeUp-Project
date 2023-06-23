package it.beermeup.control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import it.beermeup.model.AddressDao;
import it.beermeup.model.Beer;
import it.beermeup.model.BeerDao;
import it.beermeup.model.Cart;
import it.beermeup.model.CartProduct;
import it.beermeup.model.Order;
import it.beermeup.model.OrderDao;
import it.beermeup.model.OrderDetails;
import it.beermeup.model.OrderDetailsDao;
import it.beermeup.model.PaymentMethodDao;
import it.beermeup.model.UserDao;


public class CheckoutControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	static AddressDao addressModel = new AddressDao();
	static OrderDao orderModel = new OrderDao();
	static PaymentMethodDao paymentModel = new PaymentMethodDao();
	static OrderDetailsDao orderDetailsModel = new OrderDetailsDao();
	static BeerDao beerModel = new BeerDao();
	static UserDao userModel = new UserDao();
	static Logger logger = Logger.getLogger(CheckoutControl.class.getName());

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
				
				request.removeAttribute("user-info");
				request.setAttribute("user-info", userModel.doRetrieveByKey(userId));
				request.removeAttribute("address-list");
				request.setAttribute("address-list", addressModel.doRetrieveByUser(userId.intValue()));	
				request.removeAttribute("payment-list");
				request.setAttribute("payment-list", paymentModel.doRetrieveByUser(userId.intValue()));	
			}		
			
			
		}
		catch(Exception e) {
			CheckoutControl.logger.log(Level.WARNING, "Errore Servlet Checkout Control");
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/checkout.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
				
		Integer userId = (Integer)(request.getSession().getAttribute("user-id"));
		if(userId == null || userId.intValue()<=0) {
			response.sendRedirect("./login.jsp");
			return;
		}
		
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if(cart == null || cart.isEmpty()) {
			response.sendRedirect("./catalogo.jsp");
		}
		else {
			try {
				if(action!=null && action.equalsIgnoreCase("checkout")) {
					
					//Completa Checkout
					String address = request.getParameter("address");
					String paymentmethod = request.getParameter("paymentmethod");
					String status = "Completato";
					BigDecimal total = cart.getTotalPrice(true);
					Date date = new Date(System.currentTimeMillis());
						
					Order order = new Order();
					order.setUserId(userId.intValue());
					order.setShippingAddress(address);
					order.setBillingAddress(address);
					order.setPaymentInfo(paymentmethod);
					order.setStatus(status);
					order.setTotal(total);
					order.setDate(date);
						
					int orderKey = orderModel.doSaveReturnKey(order);
						
					for(CartProduct x : cart.getProducts()) {
						OrderDetails orderDetails = new OrderDetails();
							
						orderDetails.setOrderId(orderKey);
						orderDetails.setBeerId(x.getProduct().getId());
						orderDetails.setDesc(x.getProduct().getName());
						orderDetails.setQta(x.getQta());
						orderDetails.setIva(x.getProduct().getIva());
						orderDetails.setPrice(x.getPrice(true));
							
						orderDetailsModel.doSave(orderDetails);
							
						//Aggiorna quantità
						Beer beer = x.getProduct();
						beer.setStock(beer.getStock() - x.getQta());
						beerModel.doUpdate(beer);
					}		
					
					request.getSession().removeAttribute("cart");					
				}			
				
			}
			catch(Exception e) {
				CheckoutControl.logger.log(Level.WARNING, "Errore Servlet Checkout Control");
			}
		}
		
		
				
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home.jsp");
		dispatcher.forward(request, response);
	}
	 
}
