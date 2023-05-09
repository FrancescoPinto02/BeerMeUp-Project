package it.beermeup.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.User;
import it.beermeup.model.UserDao;

public class UserControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	static UserDao model = new UserDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Recupero Id Utente
		Integer userId = (Integer)((request.getSession().getAttribute("user-id")));
		if(userId == null || userId.intValue()<=0) {
			response.sendRedirect("./login.jsp");
			return;
		}
		
		//Azione richiesta
		String action = request.getParameter("action");
		
		try {
			if(action!=null) {
				
				//Recupera Dati Utente
				if (action.equalsIgnoreCase("retrieveUserInfo")) {
					User userInfo = model.doRetrieveByKey(userId.intValue());
					request.removeAttribute("user-info");
					request.setAttribute("user-info", userInfo);
				}
			}
		}
		catch(Exception e) {
			System.out.println("Errore:" + e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-profile.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}

}
