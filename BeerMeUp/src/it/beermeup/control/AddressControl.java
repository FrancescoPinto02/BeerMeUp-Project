package it.beermeup.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.Address;
import it.beermeup.model.AddressDao;

public class AddressControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static AddressDao model = new AddressDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
		
		Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
		if(userId == null || userId.intValue()<=0) {
			response.sendRedirect("./login.jsp");
			return;
		}
		
		try {
			if(action!=null) {
				//Recupera tutti gli indirizzi dell`utente
				if (action.equalsIgnoreCase("retrieveUserAddress")) {
					request.removeAttribute("address-list");
					request.setAttribute("address-list", model.doRetrieveByUser(userId.intValue()));
				}
				
				//Rimuovi Indirizzo
				else if (action.equalsIgnoreCase("deleteAddress")) {
					int addressId = Integer.valueOf(request.getParameter("address-id"));
					
					//controllo per maggiore sicurezza
					if((model.doRetrieveByKey(addressId)).getUserId() == userId.intValue()) {
						model.doDelete(addressId);
					}
				}
			}
			
			
		}
		catch(Exception e) {
			System.out.println("Errore:" + e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-address.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Azione richiesta
		String action = request.getParameter("action");
		
		Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
		if(userId == null || userId.intValue()<=0) {
			response.sendRedirect("./login.jsp");
			return;
		}
		
		try {
			if(action!=null) {
				//Aggiungi indirizzo
				if (action.equalsIgnoreCase("addAddress")) {
					String street = request.getParameter("street");
					String num = request.getParameter("num");
					String cap = request.getParameter("cap");
					String city = request.getParameter("city");
					String nation = request.getParameter("nation");
					String telephone = request.getParameter("telephone");
					
					Address address = new Address();
					address.setUserId(userId.intValue());
					address.setStreet(street);
					address.setNum(num);
					address.setCap(cap);
					address.setCity(city);
					address.setNation(nation);
					address.setTelephone(telephone);
					
					
					model.doSave(address);
				}
			}
			
			
		}
		catch(Exception e) {
			System.out.println("Errore:" + e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-address.jsp");
		dispatcher.forward(request, response);
	}
}
