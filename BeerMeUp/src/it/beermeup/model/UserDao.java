package it.beermeup.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDao{

	private static final String TABLE_NAME = "site_user";
	private static final String SELECT_ALL = "SELECT * FROM site_user";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(UserDao.class.getName());
	
	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/beer_me_up");

		} catch (NamingException e) {
			UserDao.logger.log(Level.WARNING, "Errore DataSource");
		}
	}
	
	//Liberare risorse al termine della query
	private void terminateQuery(PreparedStatement ps, Connection connection) throws SQLException {
		try {
			if(ps != null) {
				ps.close();
			}
		}
		finally {
			if(connection != null) {
				connection.close();
			}
		}
	}
	
	
	//Funzione per recuperare tutti i dati necessari da una riga del result set
	private User getUserFromRS(ResultSet rs) throws SQLException {
		User bean = new User();
		bean.setId(rs.getInt("id"));
		bean.setPw(rs.getString("pw"));
		bean.setEmail(rs.getString("email"));
		bean.setFirstName(rs.getString("first_name"));
		bean.setLastName(rs.getString("last_name"));
		bean.setAdmin(rs.getBoolean("is_admin"));
		return bean;
	}
	
	public synchronized User doRetrieveByKey(int id) throws SQLException {
		User bean = new User();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = SELECT_ALL + " WHERE id = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean = getUserFromRS(rs);	
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}	
		return bean;
	}
	
	public synchronized User doRetrieveByEmail(String email) throws SQLException {
		User bean = new User();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = SELECT_ALL + " WHERE email = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean = getUserFromRS(rs);
				
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return bean;
	
		
	}
	
	public synchronized Collection<User> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<User> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				User bean = getUserFromRS(rs);
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}

	public synchronized void doSave(User bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + UserDao.TABLE_NAME + " ( email, pw, first_name, last_name, is_admin)"
				+ " VALUES (?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(sql);
			ps.setString(1, bean.getEmail());
			ps.setString(2, bean.getPw());
			ps.setString(3, bean.getFirstName());
			ps.setString(4, bean.getLastName());
			ps.setBoolean(5, bean.isAdmin());
			
			ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		
	}

	public synchronized boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM " + UserDao.TABLE_NAME + " WHERE id = ?";
		int result = 0;
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			result = ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return (result!=0);
	}
	
}


