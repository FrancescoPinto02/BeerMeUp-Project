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

public class StyleDao{

	private static final String TABLE_NAME = "style";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(StyleDao.class.getName());

	
	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/beer_me_up");

		} catch (NamingException e) {
			StyleDao.logger.log(Level.WARNING, "Errore DataSource");
		}
	}

	public synchronized Style doRetrieveByKey(int id) throws SQLException {
		Style bean = new Style();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + StyleDao.TABLE_NAME + " WHERE id = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("style_name"));
				bean.setTraits(rs.getString("traits"));
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

	public synchronized Collection<Style> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Style> collection = new ArrayList<>(); 
		
		String sql = "SELECT * FROM " + StyleDao.TABLE_NAME;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Style bean = new Style();
				
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("style_name"));
				bean.setTraits(rs.getString("traits"));
				
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

	public synchronized void doSave(Style bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + StyleDao.TABLE_NAME + " (style_name, traits) VALUES (?, ?) ";
		
		try {
			connection = ds.getConnection(); 
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getTraits());
			
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

	public synchronized boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM " + StyleDao.TABLE_NAME + " WHERE id = ?";
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
