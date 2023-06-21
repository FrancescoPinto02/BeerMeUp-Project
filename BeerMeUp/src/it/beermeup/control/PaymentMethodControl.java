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

import it.beermeup.model.PaymentMethod;
import it.beermeup.model.PaymentMethodDao;

public class PaymentMethodControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static PaymentMethodDao model = new PaymentMethodDao();
	static Logger logger = Logger.getLogger(PaymentMethodControl.class.getName());
	
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
				//Recupera tutti i metodi di pagamento dell`utente
				if (action.equalsIgnoreCase("retrieveUserPayment")) {
					request.removeAttribute("payment-list");
					request.setAttribute("payment-list", model.doRetrieveByUser(userId.intValue()));
					
				}
				
				//Rimuovi Metodo Di Pagamento
				else if (action.equalsIgnoreCase("deletePaymentMethod")) {
					int paymentmethodId = Integer.parseInt(request.getParameter("paymentmethod-id"));
					
					//controllo per maggiore sicurezza
					if((model.doRetrieveByKey(paymentmethodId)).getUserId() == userId.intValue()) {
						model.doDelete(paymentmethodId);
					}
					
					
			}	
			}}
		
		catch(Exception e) {
			AddressControl.logger.log(Level.WARNING, "Errore Servlet Address Control");
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-paymentmethod.jsp");
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
		
		try {
			if(action!=null && action.equalsIgnoreCase("addPaymentMethod")) {
				//Aggiungi Metodo Di Pagamento
				String cardOwner = request.getParameter("card_owner");
				String cardNumber = request.getParameter("card_number");
				String cvv = request.getParameter("cvv");
				String expiration=(request.getParameter("expiration"));
				Date date=Date.valueOf(expiration);
				
				

					
				PaymentMethod paymentmethod = new PaymentMethod();
				paymentmethod.setUserId(userId.intValue());
				paymentmethod.setOwner(cardOwner);
				paymentmethod.setNumber(cardNumber);
				paymentmethod.setCvv(cvv);
				paymentmethod.setExpirationDate(date);
					
					
				model.doSave(paymentmethod);
			}	
		}
	catch(Exception e) {
			AddressControl.logger.log(Level.WARNING, "Errore Servlet Payment Method Control:");
		}
		 
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-paymentmethod.jsp");
		dispatcher.forward(request, response);
	}
}
