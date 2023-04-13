package it.beermeup.model;

import java.util.ArrayList;
import java.util.List;


public class Cart {
	private List<Beer> products;
	
	public Cart() {
		products = new ArrayList<Beer>();
	}
	
	public void addProduct(Beer product) {
		products.add(product);
	}
	
	public void deleteProduct(Beer product) {
		for(Beer x : products) {
			if(x.getId() == product.getId()) {
				products.remove(x);
				break;
			}
		}
 	}
	
	public List<Beer> getProducts() {
		return  products;
	}
}
