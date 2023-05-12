package it.beermeup.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.AddressDao;
import it.beermeup.model.User;
import it.beermeup.model.UserDao;


public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static UserDao userModel = new UserDao();
	static AddressDao addressModel = new AddressDao();
	static Logger logger = Logger.getLogger(Login.class.getName());
	private static final String ADMIN_ROLES = "admin-roles";

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if(action!=null) {
				if(action.equalsIgnoreCase("login")) {
					doLogin(request, response);
					return;
				}
				
				else if(action.equalsIgnoreCase("sign-in")) {
					doSignIn(request, response);
					return;
				}
			}
				
		}
		catch (Exception e){
			Login.logger.log(Level.WARNING, "Errore Servlet Login");
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}
	
	
	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		boolean found = false;
		Collection<User> utenti = userModel.doRetrieveAll(null);
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		for (User x: utenti){	
			if(	x.getEmail().equalsIgnoreCase(email) && x.getPw().equals(password)){
				found = true;
				request.getSession().removeAttribute("user-id");
				request.getSession().setAttribute("user-id", x.getId());
				request.getSession().removeAttribute("invalid-login");
			
				if(x.isAdmin()) {
					request.getSession().removeAttribute(ADMIN_ROLES);
					request.getSession().setAttribute(ADMIN_ROLES, true) ;
				}
				else {
					request.getSession().removeAttribute(ADMIN_ROLES);
					request.getSession().setAttribute(ADMIN_ROLES, false) ;
				}
			
				break;
			}
		}
		
		if(!found) {
			request.getSession().setAttribute("invalid-login", true);
			response.sendRedirect("./login.jsp");
		}
		else {
			response.sendRedirect("./user-profile.jsp");
		}
	}
	
	private void doSignIn(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		
		User u = new User();
		u.setEmail(email);
		u.setPw(password);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		
		if(!existingEmail(email)) {
			userModel.doSave(u);
		}		
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
	
	private boolean existingEmail(String email) throws SQLException {
		User p=userModel.doRetrieveByEmail(email);
		return p.getEmail().equalsIgnoreCase(email);
	}
}
	

 