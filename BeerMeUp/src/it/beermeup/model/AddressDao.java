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

public class AddressDao implements Dao<Address> {

	private static final String TABLE_NAME = "address";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(AddressDao.class.getName());
	
	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/beer_me_up");

		} catch (NamingException e) {
			AddressDao.logger.log(Level.WARNING, "Errore DataSource");
		}
	}

	@Override
	public Address doRetrieveByKey(int id) throws SQLException {
		
		Address bean = new Address();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + AddressDao.TABLE_NAME + " WHERE id = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setUserId(rs.getInt("user_id"));
				bean.setStreet(rs.getString("street"));
				bean.setNum(rs.getString("num"));
				bean.setCap(rs.getString("cap"));
				bean.setCity(rs.getString("city"));
				bean.setNation(rs.getString("nation"));
				bean.setTelephone(rs.getString("telephone"));
				
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
	public Collection<Address> doRetrieveAll(String order) throws SQLException {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Address> collection = new ArrayList<>(); 
		
		String sql = "SELECT * FROM " + AddressDao.TABLE_NAME;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Address bean = new Address();
				bean.setId(rs.getInt("id"));
				bean.setUserId(rs.getInt("user_id"));
				bean.setStreet(rs.getString("street"));
				bean.setNum(rs.getString("num"));
				bean.setCap(rs.getString("cap"));
				bean.setCity(rs.getString("city"));
				bean.setNation(rs.getString("nation"));
				bean.setTelephone(rs.getString("telephone"));
				
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
	
	public Collection<Address> doRetrieveByUser(int userId) throws SQLException {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Address> collection = new ArrayList<>(); 
		
		String sql = "SELECT * FROM " + AddressDao.TABLE_NAME + " WHERE (user_id=?)";
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Address bean = new Address();
				bean.setId(rs.getInt("id"));
				bean.setUserId(rs.getInt("user_id"));
				bean.setStreet(rs.getString("street"));
				bean.setNum(rs.getString("num"));
				bean.setCap(rs.getString("cap"));
				bean.setCity(rs.getString("city"));
				bean.setNation(rs.getString("nation"));
				bean.setTelephone(rs.getString("telephone"));
				
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
	public void doSave(Address bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + AddressDao.TABLE_NAME + " ( user_id, street, num, cap, city, nation, telephone)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(sql);
			
			ps.setInt(1, bean.getUserId());
			ps.setString(2, bean.getStreet());
			ps.setString(3, bean.getNum());
			ps.setString(4, bean.getCap());
			ps.setString(5, bean.getCity());
			ps.setString(6, bean.getNation());
			ps.setString(7, bean.getTelephone());
			
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
	public void doUpdate(Address bean) throws SQLException {
	}

	@Override
	public boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM " + AddressDao.TABLE_NAME + " WHERE id = ?";
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
