package it.beermeup.model;

import java.sql.SQLException;
import java.util.Collection;


public interface Dao<T>{
	
	public T doRetrieveByKey(int id) throws SQLException;
	
	public Collection<T> doRetrieveAll(String order) throws SQLException;
	
	public void doSave(T bean) throws SQLException;
	
	public void doUpdate(T bean) throws SQLException;
	
	public boolean doDelete(int id) throws SQLException;
}
