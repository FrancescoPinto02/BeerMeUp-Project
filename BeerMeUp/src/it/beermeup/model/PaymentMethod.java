package it.beermeup.model;

import java.sql.Date;

public class PaymentMethod {
	
	private int id = 0;
	private int userId = 0;
	private String card_owner = "";
	private String card_number = "";
	private String cvv = "";
	private Date expiration_date = new Date(0);
	
	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getOwner(){
		return card_owner;
	}
	
	public void setOwner(String card_owner){
		this.card_owner = card_owner;
	}
	
	public String getNumber() {
		return card_number;
	}
	
	public void setNumber(String card_number){
		this.card_number = card_number;
	}
	
	public String getCvv(){
		return cvv;
	}
	
	public void setCvv(String cvv){
		this.cvv = cvv;
	}
	
	public Date getExpirationDate(){
		return expiration_date;
	}
	
	public void setExpirationDate(Date expiration_date2){
		this.expiration_date = expiration_date2;
	}
	
	public String toString() {
		return "Payment Method [Owner=" + card_owner + ", Card Number=" + card_number + ", CVV=" + cvv + ", Expiration Date=" + expiration_date
				+ "]";
	}
}
