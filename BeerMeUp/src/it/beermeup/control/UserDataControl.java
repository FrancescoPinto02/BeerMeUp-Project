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

import it.beermeup.model.User;
import it.beermeup.model.UserDao;

public class UserDataControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static UserDao model = new UserDao();
	static Logger logger = Logger.getLogger(UserDataControl.class.getName());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Recupero Id Utente
				Integer userId = (Integer)(request.getSession().getAttribute("user-id"));
				if(userId == null || userId.intValue()<=0) {
					response.sendRedirect("./login.jsp");
					return;
				}
				
				//Azione richiesta
				String action = request.getParameter("action");
				
		if(action!=null && action.equalsIgnoreCase("recupera-utente")) {
			
			//Recupera informazioni utente
			User userInfo;
			try {
				userInfo = model.doRetrieveByKey(userId.intValue());
			
			
			request.removeAttribute("user-info");
			request.setAttribute("user-info", userInfo);
			} catch (SQLException e) {
				
				UserDataControl.logger.log(Level.WARNING, "Errore Servlet User Data Control:");
				
	}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-data.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Recupero Id Utente
		Integer userId = (Integer)(request.getSession().getAttribute("user-id"));
		if(userId == null || userId.intValue()<=0) {
			response.sendRedirect("./login.jsp");
			return;
		}
		
		//Azione richiesta
		String action = request.getParameter("action");
		
		try {
		  if(action!=null)
		  {
			  String fname =  request.getParameter("fname");
			  String lname =  request.getParameter("lname");
			  String email = request.getParameter("email");
			  String pw = request.getParameter("pw");
	
	
			  User user = new User ();
	
			  user.setEmail(email);
			  user.setFirstName(fname);
			  user.setLastName(lname);
			  user.setPw(pw);
			  user.setId(userId.intValue());

			  model.doUpdate(user);
		  }

		}
		 catch (SQLException e) {
			 ProductManagerControl.logger.log(Level.WARNING, "Errore Servlet Data Control");
			 }
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-profile.jsp");
		dispatcher.forward(request, response);
	}
}

