package it.beermeup.control;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String redirectpage;
		try {
			Collection<User> utenti =userModel.doRetrieveAll("");
			for (User x: utenti)
			{
				
			if(	x.getEmail().equals(username) && x.getPw().equals(password) && x.isAdmin())
				{
					
				}
			}
		}
		catch (Exception e)
		{
			
		}
		
		
	}
}
	

 