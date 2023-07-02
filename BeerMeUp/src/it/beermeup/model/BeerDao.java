package it.beermeup.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BeerDao{
	
	private static final String TABLE_NAME = "beer";
	private static final String  SELECT_ALL = "SELECT * FROM beer";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(BeerDao.class.getName());
	
	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/beer_me_up");

		} catch (NamingException e) {
			BeerDao.logger.log(Level.WARNING, "Errore DataSource");
		}
	}
	
	//Per chiudere tutte le risorse dopo una query
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
	
	private Beer getBeerFromRS(ResultSet rs) throws SQLException {
		Beer bean = new Beer();
		bean.setId(rs.getInt("id"));
		bean.setBreweryId(rs.getInt("brewery_id"));
		bean.setStyleId(rs.getInt("style_id"));
		bean.setName(rs.getString("beer_name"));
		bean.setDescription(rs.getString("beer_description"));
		bean.setColor(rs.getString("color"));
		bean.setIngredients(rs.getString("ingredients"));
		bean.setGradation(rs.getBigDecimal("gradation"));
		bean.setPrice(rs.getBigDecimal("price"));
		bean.setIva(rs.getBigDecimal("iva"));
		bean.setStock(rs.getInt("stock"));
		bean.setDiscount(rs.getInt("discount"));
		bean.setInputStreamImage(rs.getBinaryStream("img"));
		
		try {
			bean.setBase64Image(imgConvert(rs.getBlob("img")));
		} 
		catch (IOException e) {
			BeerDao.logger.log(Level.WARNING, "Errore Lettura Immagine");
		}
		
		return bean;
	}
	
	private String imgConvert(Blob blob) throws IOException, SQLException {
		InputStream inputStream = blob.getBinaryStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
         
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);                  
        }
         
        byte[] imageBytes = outputStream.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
        inputStream.close();
        outputStream.close();
		
        return base64Image;
	}
	
	private void buildBeerPS(Beer bean, PreparedStatement ps) throws SQLException {
		ps.setInt(1, bean.getBreweryId());
		ps.setInt(2, bean.getStyleId());
		ps.setString(3, bean.getName());
		ps.setString(4, bean.getDescription());
		ps.setString(5, bean.getColor());
		ps.setString(6, bean.getIngredients());
		ps.setBigDecimal(7, bean.getGradation());
		ps.setBigDecimal(8, bean.getPrice());
		ps.setBigDecimal(9, bean.getIva());
		ps.setInt(10, bean.getStock());
		ps.setInt(11, bean.getDiscount());
		ps.setBinaryStream(12, bean.getInputStreamImage());
	}
	
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
				bean = getBeerFromRS(rs);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return bean;
	}

	public synchronized Collection<Beer> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Beer> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL;
		if(order!=null && !order.equals("")) {
			if(order.equalsIgnoreCase("price")) {
				order = "(price - ((price/100)*discount))";
			}
			
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Beer bean = getBeerFromRS(rs);
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}
	
	public synchronized Collection<Beer> doRetrievePromo() throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Beer> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL + " WHERE discount>0";
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Beer bean = getBeerFromRS(rs);
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}
	
	public synchronized Collection<Beer> doRetrieveNew() throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Beer> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL + " ORDER BY id DESC LIMIT 5";
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Beer bean = getBeerFromRS(rs);
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}

	public synchronized void doSave(Beer bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + BeerDao.TABLE_NAME + " (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			buildBeerPS(bean, ps);
			
			ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
	}

	public synchronized void doUpdate(Beer bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "UPDATE "+ BeerDao.TABLE_NAME + " SET brewery_id =?, style_id = ?, beer_name= ? , beer_description= ?, color = ?, ingredients = ?, gradation = ?,"+
		"price = ? ,iva = ? , stock = ?, discount = ? WHERE id = ?";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);
			
			ps.setInt(1, bean.getBreweryId());
			ps.setInt(2, bean.getStyleId());
			ps.setString(3, bean.getName());
			ps.setString(4, bean.getDescription());
			ps.setString(5, bean.getColor());
			ps.setString(6, bean.getIngredients());
			ps.setBigDecimal(7, bean.getGradation());
			ps.setBigDecimal(8, bean.getPrice());
			ps.setBigDecimal(9, bean.getIva());
			ps.setInt(10, bean.getStock());
			ps.setInt(11, bean.getDiscount());
			
			ps.setInt(12, bean.getId());
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
		String sql = "DELETE FROM " + BeerDao.TABLE_NAME + " WHERE id = ?";
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

	public synchronized  Collection<Beer> doRetrieveByNameDynamic(String name) throws SQLException {
		Beer bean = new Beer();
		Connection connection = null;
		PreparedStatement ps = null;
		Collection<Beer> collection = new ArrayList<>(); 
		String sql = "SELECT * FROM " + BeerDao.TABLE_NAME + " WHERE beer_name LIKE CONCAT('%',?,'%')";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			
			while(rs.next()) {		
				bean = getBeerFromRS(rs);
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}
}
