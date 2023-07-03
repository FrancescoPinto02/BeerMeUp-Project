package it.beermeup.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.User;
import it.beermeup.model.UserDao;


public class CheckEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static UserDao userModel = new UserDao();
	static Logger logger = Logger.getLogger(CheckEmail.class.getName());
 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		
		try {
			if(existingEmail(email))
			{
				response.getWriter().write("true");
				response.setContentType("text/plain");
			}
			else {
				response.sendRedirect("./login.jsp");
			}
		} catch (SQLException e) {
			
			CheckEmail.logger.log(Level.WARNING, "Errore check email:");
		}

	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	private boolean existingEmail(String email) throws SQLException {
		User p=userModel.doRetrieveByEmail(email);
		return p.getEmail().equalsIgnoreCase(email);
	}
}
