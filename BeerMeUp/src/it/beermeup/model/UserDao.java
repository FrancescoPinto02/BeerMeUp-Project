package it.beermeup.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDao implements Dao<User> {

	private static final String TABLE_NAME = "site_user";
	
	private static DataSource ds;
	
	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/beer_me_up");

		} catch (NamingException e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}
	@Override
	public User doRetrieveByKey(int id) throws SQLException {
		User bean = new User();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + UserDao.TABLE_NAME + " WHERE id = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setPw(rs.getString("pw"));
				bean.setEmail(rs.getString("email"));
				bean.setFirst_name(rs.getString("first_name"));
				bean.setLast_name(rs.getString("last_name"));
				bean.setAdmin(rs.getBoolean("is_admin"));
				
			}		
		}
		finally {
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
		
		return bean;
	
	}
	
	public User doRetrieveByEmail(String email) throws SQLException {
		User bean = new User();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + UserDao.TABLE_NAME + " WHERE email = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setPw(rs.getString("pw"));
				bean.setEmail(rs.getString("email"));
				bean.setFirst_name(rs.getString("first_name"));
				bean.setLast_name(rs.getString("last_name"));
				bean.setAdmin(rs.getBoolean("is_admin"));
				
			}		
		}
		finally {
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
		
		return bean;
	
		
	}
	@Override
	public Collection<User> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<User> collection = new ArrayList<>(); 
		
		String sql = "SELECT * FROM " + UserDao.TABLE_NAME;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				User bean = new User();
				bean.setId(rs.getInt("id"));
				bean.setPw(rs.getString("pw"));
				bean.setEmail(rs.getString("email"));
				bean.setFirst_name(rs.getString("first_name"));
				bean.setLast_name(rs.getString("last_name"));
				bean.setAdmin(rs.getBoolean("is_admin"));
				
				
				collection.add(bean);
			}		
		}
		finally {
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
		
		return collection;
	}

	@Override
	public void doSave(User bean) throws SQLException {
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
			ps.setString(3, bean.getFirst_name());
			ps.setString(4, bean.getLast_name());
			ps.setBoolean(5, bean.isAdmin());
			
			ps.executeUpdate();
			connection.commit();		
		}
		finally {
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
		
		
	}

	@Override
	public void doUpdate(User bean) throws SQLException {		
	}

	@Override
	public boolean doDelete(int id) throws SQLException {
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
		
		return (result!=0);
	}
	
}


