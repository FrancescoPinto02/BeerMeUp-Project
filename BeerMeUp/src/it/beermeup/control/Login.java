package it.beermeup.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.beermeup.model.Address;
import it.beermeup.model.User;
import it.beermeup.model.UserDao;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static UserDao userModel = new UserDao();


	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if(action.equals("login"))
		{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		
		
		try {
			Collection<User> utenti =userModel.doRetrieveAll("");
			for (User x: utenti)
			{
				
			if(	x.getEmail().equals(username) && x.getPw().equals(password) && x.isAdmin())
				{
				request.getSession().setAttribute("AdminRoles", true ) ;
				response.sendRedirect(request.getContextPath()+ "/product-manager.jsp");
				}
			else if(x.getEmail().equals(username) && x.getPw().equals(password) && x.isAdmin()!= true)
			{
				request.getSession().setAttribute("User", true ) ;
				request.getSession().setAttribute("AdminRoles", false ) ;
				response.sendRedirect(request.getContextPath()); ////////???????????????
			}
			else {
				request.getSession().setAttribute("User", false ) ;
				request.getSession().setAttribute("AdminRoles", false) ;
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		}
		
		
		if(action.equals("sign-in"))
		{
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String first_name = request.getParameter("first_name");
			String last_name = request.getParameter("last_name");
			String telephone = request.getParameter("telephone");
			
			User u = new User();
			u.setEmail(email);
			u.setPw(password);
			u.setFirst_name(first_name);
			u.setLast_name(last_name);
			u.setTelephone(telephone);
			
			String street = request.getParameter("street");
			String num = request.getParameter("num");
			String cap = request.getParameter("cap");
			String city = request.getParameter("city");
			String nation = request.getParameter("nation");
			
			Address a = new Address();
			a.setUserId(u.getId());
			a.setStreet(street);
			a.setNum(num);
			a.setCap(cap);
			a.setCity(city);
			a.setNation(nation);

			try {
				userModel.doSave(u);
			} 
			catch (SQLException e) {
				System.out.println("Errore:" + e.getMessage());
		}
		
	}
}
}
	

 