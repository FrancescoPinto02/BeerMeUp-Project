package it.beermeup.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Cart {
	private List<CartProduct> products;
	
	public Cart() {
		products = new ArrayList<CartProduct>();
	}
	
	public void addProduct(Beer product) {
		addProduct(product, 1);
	}
	
	public void addProduct(Beer product, int qta) {
		boolean found = false;
		
		for(CartProduct x : products) {
			if(x.getProduct().equals(product)) {
				x.setQta(x.getQta() + qta);
				x.setPrice(x.getPrice().add(x.getProduct().getPrezzo().multiply(new BigDecimal(qta))));
				found = true;
				break;
			}
		}
		
		if(!found) {
			CartProduct prod = new CartProduct();
			prod.setProduct(product);
			prod.setQta(qta);
			prod.setPrice(product.getPrezzo().multiply(new BigDecimal(qta)));
			products.add(prod);                      
		}
		
	}
	
	public void deleteProduct(Beer product) {
		for(CartProduct x : products) {
			if(x.getProduct().equals(product)) {
				products.remove(x);
				break;
			}
		}
 	}
	
	public void deleteProduct(Beer product, int qta) {
		for(CartProduct x : products) {
			if(x.getProduct().equals(product)) {
				x.setQta(x.getQta() - qta);
				if(x.getQta()<=0) {
					products.remove(x);
				}
				else {
					x.setPrice(x.getPrice().subtract(x.getProduct().getPrezzo().divide(new BigDecimal(qta))));
				}
				break;
			}
		}
	}
	
	public List<CartProduct> getProducts() {
		return  products;
	}
}
