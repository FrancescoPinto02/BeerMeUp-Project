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

public class OrderDetailsDao implements Dao<OrderDetails> {
	private static final String TABLE_NAME = "order_details";
	
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
	//Non Utilizzare
	public synchronized OrderDetails doRetrieveByKey(int id) throws SQLException {
		return null;
	}

	public synchronized Collection<OrderDetails> doRetrieveByOrder(int orderId) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<OrderDetails> collection = new ArrayList<>(); 
		
		String sql = "SELECT * FROM " + OrderDetailsDao.TABLE_NAME;
		
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				OrderDetails bean = new OrderDetails();
				
				bean.setOrderId(rs.getInt("order_id"));
				bean.setBeerId(rs.getInt("beer_id"));
				bean.setDesc(rs.getString("des"));
				bean.setQta(rs.getInt("qta"));
				bean.setIva(rs.getBigDecimal("iva"));
				bean.setPrice(rs.getBigDecimal("price"));
				
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
	
	//Non Usare
	@Override
	public synchronized Collection<OrderDetails> doRetrieveAll(String order) throws SQLException {
		return null;
	}

	@Override
	public synchronized void doSave(OrderDetails bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + OrderDetailsDao.TABLE_NAME + " (order_id, beer_id, des, qta, iva, price) VALUES (?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bean.getOrderId());
			ps.setInt(2, bean.getBeerId());
			ps.setString(3, bean.getDesc());
			ps.setInt(4, bean.getQta());
			ps.setBigDecimal(5, bean.getIva());
			ps.setBigDecimal(6, bean.getPrice());
			
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
	public synchronized void doUpdate(OrderDetails bean) throws SQLException {
	}

	@Override
	public synchronized boolean doDelete(int id) throws SQLException {
		return false;
	}
}
