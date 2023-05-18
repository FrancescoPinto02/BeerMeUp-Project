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

public class PaymentMethodDao{

	private static final String TABLE_NAME = "paymentmethod";
	private static final String SELECT_ALL = "SELECT * FROM payment_method";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(PaymentMethodDao.class.getName());
	
	
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

	//Funzione per recuperare tutti i dati necessari da una riga del result set
	private PaymentMethod getPaymentMethodFromRS(ResultSet rs) throws SQLException {
		PaymentMethod bean = new PaymentMethod();
		bean.setId(rs.getInt("id"));
		bean.setUserId(rs.getInt("user_id"));
		bean.setOwner(rs.getString("card_owner"));
		bean.setNumber(rs.getString("card_number"));
		bean.setCvv(rs.getString("cvv"));
		bean.setExpirationDate(rs.getDate("expiration_date"));
		return bean;
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
	
	public PaymentMethod doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = SELECT_ALL + " WHERE id = ?";
		ResultSet rs = null;
		PaymentMethod bean = new PaymentMethod();
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean = getPaymentMethodFromRS(rs);	
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return bean;
	}

	public Collection<PaymentMethod> doRetrieveAll(String order) throws SQLException {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<PaymentMethod> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				PaymentMethod bean = getPaymentMethodFromRS(rs);
				
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}
	
	public Collection<PaymentMethod> doRetrieveByUser(int userId) throws SQLException {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<PaymentMethod> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL + " WHERE (user_id=?)";
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				PaymentMethod bean = getPaymentMethodFromRS(rs);
				
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}

	public void doSave(PaymentMethod bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + PaymentMethodDao.TABLE_NAME + " ( user_id, card_owner, card_number, cvv, expiration_date)"
				+ " VALUES (?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(sql);
			
			ps.setInt(1, bean.getUserId());
			ps.setString(2, bean.getOwner());
			ps.setString(3, bean.getNumber());
			ps.setString(4, bean.getCvv());
			ps.setDate(5, bean.getExpirationDate());
			
			ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
	}

	public boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM " + PaymentMethodDao.TABLE_NAME + " WHERE id = ?";
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
