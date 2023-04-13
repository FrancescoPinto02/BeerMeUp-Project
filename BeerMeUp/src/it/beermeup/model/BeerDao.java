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

public class BeerDao implements Dao<Beer> {
	
	private static final String TABLE_NAME = "birra";
	
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
	public synchronized Beer doRetrieveByKey(int id) throws SQLException {
		Beer bean = new Beer();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + BeerDao.TABLE_NAME + " WHERE id = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setProduttore_id(rs.getInt("produttore_id"));
				bean.setStile_id(rs.getInt("stile_id"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setColore(rs.getString("colore"));
				bean.setIngredienti(rs.getString("ingredienti"));
				bean.setGradazione(rs.getBigDecimal("gradazione"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setIva(rs.getBigDecimal("iva"));
				bean.setStock(rs.getInt("stock"));
				bean.setSconto(rs.getInt("sconto"));
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
	public Collection<Beer> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Beer> collection = new ArrayList<Beer>(); 
		
		String sql = "SELECT * FROM " + BeerDao.TABLE_NAME;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Beer bean = new Beer();
				
				bean.setId(rs.getInt("id"));
				bean.setProduttore_id(rs.getInt("produttore_id"));
				bean.setStile_id(rs.getInt("stile_id"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setColore(rs.getString("colore"));
				bean.setIngredienti(rs.getString("ingredienti"));
				bean.setGradazione(rs.getBigDecimal("gradazione"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setIva(rs.getBigDecimal("iva"));
				bean.setStock(rs.getInt("stock"));
				bean.setSconto(rs.getInt("sconto"));
				
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
	public synchronized void doSave(Beer bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + BeerDao.TABLE_NAME + " (produttore_id , stile_id, nome, descrizione, colore, ingredienti, gradazione, prezzo, iva, stock, sconto) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bean.getProduttore_id());
			ps.setInt(2, bean.getStile_id());
			ps.setString(3, bean.getNome());
			ps.setString(4, bean.getDescrizione());
			ps.setString(5, bean.getColore());
			ps.setString(6, bean.getIngredienti());
			ps.setBigDecimal(7, bean.getGradazione());
			ps.setBigDecimal(8, bean.getPrezzo());
			ps.setBigDecimal(9, bean.getIva());
			ps.setInt(10, bean.getStock());
			ps.setInt(11, bean.getSconto());
			
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
	public synchronized void doUpdate(Beer bean) throws SQLException {
		//Non ancora implementata
		return;
	}

	@Override
	public synchronized boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM " + BeerDao.TABLE_NAME + " WHERE id = ?";
		int result = 0;
		
		try {
			connection = ds.getConnection(); 
			
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
