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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BeerDao implements Dao<Beer> {
	
	private static final String TABLE_NAME = "beer";
	
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
				bean.setBrewery_id(rs.getInt("brewery_id"));
				bean.setStyle_id(rs.getInt("style_id"));
				bean.setName(rs.getString("beer_name"));
				bean.setDescription(rs.getString("beer_description"));
				bean.setColor(rs.getString("color"));
				bean.setIngredients(rs.getString("ingredients"));
				bean.setGradation(rs.getBigDecimal("gradation"));
				bean.setPrice(rs.getBigDecimal("price"));
				bean.setIva(rs.getBigDecimal("iva"));
				bean.setStock(rs.getInt("stock"));
				bean.setDiscount(rs.getInt("discount"));
				
				try {
					bean.setBase64Image(imgConvert(rs.getBlob("img")));
				} 
				catch (IOException e) {
					System.out.println("Errore Lettura immagine");
				}
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
				Beer bean = new Beer();
				
				bean.setId(rs.getInt("id"));
				bean.setBrewery_id(rs.getInt("brewery_id"));
				bean.setStyle_id(rs.getInt("style_id"));
				bean.setName(rs.getString("beer_name"));
				bean.setDescription(rs.getString("beer_description"));
				bean.setColor(rs.getString("color"));
				bean.setIngredients(rs.getString("ingredients"));
				bean.setGradation(rs.getBigDecimal("gradation"));
				bean.setPrice(rs.getBigDecimal("price"));
				bean.setIva(rs.getBigDecimal("iva"));
				bean.setStock(rs.getInt("stock"));
				bean.setDiscount(rs.getInt("discount"));
				
				try {
					bean.setBase64Image(imgConvert(rs.getBlob("img")));
				} 
				catch (IOException e) {
					System.out.println("Errore Lettura immagine");
				}
				
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
		String sql = "INSERT INTO " + BeerDao.TABLE_NAME + " (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bean.getBrewery_id());
			ps.setInt(2, bean.getStyle_id());
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
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "UPDATE "+ BeerDao.TABLE_NAME + "SET brewery_id =?, style_id = ?, beer_name= ? , beer_description= ?, color = ?, ingredients = ?, gradation = ?,"+
		"price = ? ,iva = ? , stock = ?, discount = ? WHERE id = ?";
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bean.getBrewery_id());
			ps.setInt(2, bean.getStyle_id());
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
