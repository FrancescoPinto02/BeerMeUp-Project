package it.beermeup.model;

public class Address {
	
	private int id = 0;
	private int userId = 0;
	private String street = "";
	private String num = "";
	private String cap = "";
	private String city = "";
	private String nation = "";
	private String telephone = "";
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getNum() {
		return num;
	}
	
	public void setNum(String num) {
		this.num = num;
	}
	
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getNation() {
		return nation;
	}
	
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	@Override
	public String toString() {
		return  street + "," + num + ", " + cap + ", " + city + ", " + nation
				+ ", Tel: " + telephone;
	}
}
