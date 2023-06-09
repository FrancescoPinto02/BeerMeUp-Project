package it.beermeup.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrderDao{
	private static final String TABLE_NAME = "site_order";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(OrderDao.class.getName());
	static final String SELECT_ALL = "SELECT * FROM ";
	static final String USER_ID = "user_id";
	static final String SHIPPING_ADDRESS = "shipping_address";
	static final String BILLING_ADDRESS = "billing_address";
	static final String PAYMENT_INFO = "payment_info";
	static final String ORDER_STATUS = "order_status";
	static final String TOTAL = "total";
	static final String ORDER_DATE = "order_date";
	
	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/beer_me_up");

		} catch (NamingException e) {
			OrderDao.logger.log(Level.WARNING, "Errore DataSource");
		}
	}

	public synchronized Order doRetrieveByKey(int id) throws SQLException {
		Order bean = new Order();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = SELECT_ALL + OrderDao.TABLE_NAME + " WHERE id = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setUserId(rs.getInt(USER_ID));
				bean.setShippingAddress(rs.getString(SHIPPING_ADDRESS));
				bean.setBillingAddress(rs.getString(BILLING_ADDRESS));
				bean.setPaymentInfo(rs.getString(PAYMENT_INFO));
				bean.setStatus(rs.getString(ORDER_STATUS));
				bean.setTotal(rs.getBigDecimal(TOTAL));
				bean.setDate(rs.getDate(ORDER_DATE));
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
	

	public synchronized Collection<Order> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Order> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL + OrderDao.TABLE_NAME;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Order bean = new Order();
				
				bean.setId(rs.getInt("id"));
				bean.setUserId(rs.getInt(USER_ID));
				bean.setShippingAddress(rs.getString(SHIPPING_ADDRESS));
				bean.setBillingAddress(rs.getString(BILLING_ADDRESS));
				bean.setPaymentInfo(rs.getString(PAYMENT_INFO));
				bean.setStatus(rs.getString(ORDER_STATUS));
				bean.setTotal(rs.getBigDecimal(TOTAL));
				bean.setDate(rs.getDate(ORDER_DATE));
				
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
	
	public synchronized Collection<Order> doRetrieveByUser(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Order> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL + OrderDao.TABLE_NAME + " WHERE user_id = ? ";
		
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order bean = new Order();
				
				bean.setId(rs.getInt("id"));
				bean.setUserId(rs.getInt(USER_ID));
				bean.setShippingAddress(rs.getString(SHIPPING_ADDRESS));
				bean.setBillingAddress(rs.getString(BILLING_ADDRESS));
				bean.setPaymentInfo(rs.getString(PAYMENT_INFO));
				bean.setStatus(rs.getString(ORDER_STATUS));
				bean.setTotal(rs.getBigDecimal(TOTAL));
				bean.setDate(rs.getDate(ORDER_DATE));
				
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
	
	public synchronized Collection<Order> doRetrieveByDates(Date fromDate, Date toDate) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Order> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL + OrderDao.TABLE_NAME + " WHERE order_date>=? AND order_date<=?";
		
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			ps.setDate(1, fromDate);
			ps.setDate(2, toDate);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order bean = new Order();
				
				bean.setId(rs.getInt("id"));
				bean.setUserId(rs.getInt(USER_ID));
				bean.setShippingAddress(rs.getString(SHIPPING_ADDRESS));
				bean.setBillingAddress(rs.getString(BILLING_ADDRESS));
				bean.setPaymentInfo(rs.getString(PAYMENT_INFO));
				bean.setStatus(rs.getString(ORDER_STATUS));
				bean.setTotal(rs.getBigDecimal(TOTAL));
				bean.setDate(rs.getDate(ORDER_DATE));
				
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
	
	public synchronized int doSaveReturnKey(Order bean) throws SQLException{
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + OrderDao.TABLE_NAME + " (user_id, shipping_address, billing_address, payment_info, order_status, total, order_date) VALUES (?, ?, ?, ?, ?, ?, ?) ";
		int key = 0;
		
		try {
			connection = ds.getConnection(); 
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, bean.getUserId());
			ps.setString(2, bean.getShippingAddress());
			ps.setString(3, bean.getBillingAddress());
			ps.setString(4, bean.getPaymentInfo());
			ps.setString(5, bean.getStatus());
			ps.setBigDecimal(6, bean.getTotal());
			ps.setDate(7, bean.getDate());
			
			ps.executeUpdate();
			connection.commit();
			
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			key = rs.getInt(1);
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
		
		return key;
		
	}

}
