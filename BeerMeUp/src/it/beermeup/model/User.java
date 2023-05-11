package it.beermeup.model;

public class User {

	private int id = 0;
	private String email = "";
	private String pw = "";
	private String first_name = "";
	private String last_name = "";
	private boolean admin = false ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", pw=" + pw + ", first_name=" + first_name + ", last_name="
				+ last_name + ", admin=" + admin + "]";
	}
	
	
}
