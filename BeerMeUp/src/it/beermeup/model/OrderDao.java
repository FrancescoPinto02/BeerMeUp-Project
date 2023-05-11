package it.beermeup.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrderDao implements Dao<Order> {
	private static final String TABLE_NAME = "site_order";
	
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
	public synchronized Order doRetrieveByKey(int id) throws SQLException {
		Order bean = new Order();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + OrderDao.TABLE_NAME + " WHERE id = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setUserId(rs.getInt("user_id"));
				bean.setShippingAddress(rs.getString("shipping_address"));
				bean.setPaymentInfo(rs.getString("payment_info"));
				bean.setStatus(rs.getString("order_status"));
				bean.setTotal(rs.getBigDecimal("total"));
				bean.setDate(rs.getDate("order_date"));
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
	public synchronized Collection<Order> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Order> collection = new ArrayList<Order>(); 
		
		String sql = "SELECT * FROM " + OrderDao.TABLE_NAME;
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
				bean.setUserId(rs.getInt("user_id"));
				bean.setShippingAddress(rs.getString("shipping_address"));
				bean.setPaymentInfo(rs.getString("payment_info"));
				bean.setStatus(rs.getString("order_status"));
				bean.setTotal(rs.getBigDecimal("total"));
				bean.setDate(rs.getDate("order_date"));
				
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
	public synchronized void doSave(Order bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + OrderDao.TABLE_NAME + " (user_id, shipping_address, billing_address, payment_info, order_status, total, order_date) VALUES (?, ?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bean.getUserId());
			ps.setString(2, bean.getShippingAddress());
			ps.setString(3, bean.getBillingAddress());
			ps.setString(4, bean.getPaymentInfo());
			ps.setString(5, bean.getStatus());
			ps.setBigDecimal(6, bean.getTotal());
			ps.setDate(7, bean.getDate());
			
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

	@Override
	public synchronized void doUpdate(Order bean) throws SQLException {
		return;
	}

	@Override
	public synchronized boolean doDelete(int id) throws SQLException {
		return false;
	}
}
