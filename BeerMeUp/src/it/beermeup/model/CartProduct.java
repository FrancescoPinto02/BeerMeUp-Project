package it.beermeup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartProduct {
	private Beer product = new Beer();
	private int qta = 0;
	
	
	public Beer getProduct() {
		return product;
	}
	public void setProduct(Beer product) {
		this.product = product;
	}
	public int getQta() {
		return qta;
	}
	public void setQta(int qta) {
		if(qta > product.getStock()) {
			qta = product.getStock();
		}
		this.qta = qta;
	}
	
	
	public void increaseQta() {
		if(qta < product.getStock()) {
			qta++;
		}
	}
	public void decreaseQta() {
		if(qta>0) {
			qta--;
		}
	}
	
	public BigDecimal getPrice(boolean discount) {
		return (product.getPrice(discount).multiply(new BigDecimal(this.qta))).setScale(2, RoundingMode.HALF_EVEN);
	}
	
	@Override
	public String toString() {
		return "CartProduct [product=" + product + ", qta=" + qta + "]";
	}
	
	
	
}
