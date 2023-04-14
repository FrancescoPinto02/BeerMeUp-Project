package it.beermeup.model;

import java.math.BigDecimal;

public class CartProduct {
	private Beer product = new Beer();
	private int qta = 0;
	private BigDecimal price = new BigDecimal(0);
	
	
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
		this.qta = qta;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
	public void increaseQta() {
		qta++;
	}
	public void decreaseQta() {
		if(qta>0) {
			qta--;
		}
	}
	
	@Override
	public String toString() {
		return "CartProduct [product=" + product + ", qta=" + qta + ", price=" + price + "]";
	}
	
	
	
}
