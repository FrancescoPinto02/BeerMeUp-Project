package it.beermeup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class Cart {
	private List<CartProduct> products;
	
	public Cart() {
		products = new ArrayList<>();
	}
	
	public void addProduct(Beer product) {
		addProduct(product, 1);
	}
	
	public void addProduct(Beer product, int qta) {
		if(product.getStock() > 0) {
			
			if(product.getStock() < qta) {
				qta = product.getStock();
			}
			
			boolean found = false;
			
			for(CartProduct x : products) {
				if(x.getProduct().equals(product)) {
					x.setQta(x.getQta() + qta);
					found = true;
					break;
				}
			}
			
			if(!found) {
				CartProduct prod = new CartProduct();
				prod.setProduct(product);
				prod.setQta(qta);
				products.add(prod);                      
			}
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
				break;
			}
		}
	}
	
	public List<CartProduct> getProducts() {
		return  products;
	}
	
	public BigDecimal getTotalPrice(boolean discount) {
		BigDecimal tot = new BigDecimal(0);
		for(CartProduct x : products) {
			tot = tot.add(x.getPrice(discount));
		}
		return tot.setScale(2, RoundingMode.HALF_EVEN);
	}
	
	public boolean isEmpty() {
		return products.isEmpty();
	}
}
