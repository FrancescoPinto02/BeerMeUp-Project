package it.beermeup.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.Address;
import it.beermeup.model.AddressDao;
import it.beermeup.model.User;
import it.beermeup.model.UserDao;


public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static UserDao userModel = new UserDao();
	public static AddressDao addressModel = new AddressDao();

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if(action.equals("login")){
			String email = request.getParameter("email");
			String password = request.getParameter("password");
		
			try {
				boolean found = false;
				Collection<User> utenti = userModel.doRetrieveAll(null);
				for (User x: utenti){
					if(	x.getEmail().equalsIgnoreCase(email) && x.getPw().equals(password)){
						found = true;
						request.getSession().removeAttribute("user-id");
						request.getSession().setAttribute("user-id", x.getId());
						request.getSession().removeAttribute("invalid-login");
					
						if(x.isAdmin()) {
							request.getSession().removeAttribute("admin-roles");
							request.getSession().setAttribute("admin-roles", true) ;
						}
						else {
							request.getSession().removeAttribute("admin-roles");
							request.getSession().setAttribute("admin-roles", false) ;
						}
					
					break;
					}
				}
				if(!found) {
					request.getSession().setAttribute("invalid-login", true);
					response.sendRedirect("./login.jsp");
					return;
				}
				else {
					response.sendRedirect("./user-profile.jsp");
					return;
				}
				
				
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		
		
		if(action.equals("sign-in")){
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String first_name = request.getParameter("first_name");
			String last_name = request.getParameter("last_name");
			
			User u = new User();
			u.setEmail(email);
			u.setPw(password);
			u.setFirst_name(first_name);
			u.setLast_name(last_name);
			
			//Verifica email gia esistente

			try {
				User p = new User();
				p=userModel.doRetrieveByEmail(u.getEmail());
				if (p.getEmail().equalsIgnoreCase(u.getEmail())){
					response.sendRedirect(request.getContextPath() + "/login.jsp"); //EMAIL GIA' ESISTENTE
					return;
				}
				else {

					try {
						userModel.doSave(u);
						response.sendRedirect(request.getContextPath() + "/login.jsp"); //OPERAZIONE ANDATA A BUON FINE
						return;
					} 
					catch (SQLException e) {
						System.out.println("Errore:" + e.getMessage());
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}
}
	

 