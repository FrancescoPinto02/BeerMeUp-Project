package it.beermeup.model;

import java.sql.Date;

public class PaymentMethod {
	
	private int id = 0;
	private int userId = 0;
	private String cardOwner = "";
	private String cardNumber = "";
	private String cvv = "";
	private Date expirationDate = new Date(0);
	
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
		return cardOwner;
	}
	
	public void setOwner(String cardOwner){
		this.cardOwner = cardOwner;
	}
	
	public String getNumber() {
		return cardNumber;
	}
	
	public void setNumber(String cardNumber){
		this.cardNumber = cardNumber;
	}
	
	public String getCvv(){
		return cvv;
	}
	
	public void setCvv(String cvv){
		this.cvv = cvv;
	}
	
	public Date getExpirationDate(){
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate2){
		this.expirationDate = expirationDate2;
	}
	
	public String toString() {
		return "Payment Method [Owner=" + cardOwner + ", Card Number=" + cardNumber + ", CVV=" + cvv + ", Expiration Date=" + expirationDate
				+ "]";
	}
}
