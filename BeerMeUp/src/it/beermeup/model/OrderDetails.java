package it.beermeup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderDetails {
	
	private int orderId = 0;
	private int beerId = 0;
	private String desc = "";
	private int qta = 0;
	private BigDecimal iva = new BigDecimal(22);
	private BigDecimal price = new BigDecimal(0);
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getBeerId() {
		return beerId;
	}
	public void setBeerId(int beerId) {
		this.beerId = beerId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getQta() {
		return qta;
	}
	public void setQta(int qta) {
		this.qta = qta;
	}
	public BigDecimal getIva() {
		return iva.setScale(1, RoundingMode.HALF_EVEN);
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	public BigDecimal getPrice() {
		return price.setScale(2, RoundingMode.HALF_EVEN);
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + beerId;
		result = prime * result + orderId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetails other = (OrderDetails) obj;
		if (beerId != other.beerId)
			return false;
		return orderId == other.orderId;
	}
	
	@Override
	public String toString() {
		return "OrderDetails [orderId=" + orderId + ", beerId=" + beerId + ", qta=" + qta + ", price=" + price + "]";
	}
	
	
	
}
