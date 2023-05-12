package it.beermeup.model;

public class User {

	private int id = 0;
	private String email = "";
	private String pw = "";
	private String firstName = "";
	private String lastName = "";
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
		return firstName;
	}
	public void setFirst_name(String firstName) {
		this.firstName = firstName;
	}
	public String getLast_name() {
		return lastName;
	}
	public void setLast_name(String lastName) {
		this.lastName = lastName;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", pw=" + pw + ", first_name=" + firstName + ", last_name="
				+ lastName + ", admin=" + admin + "]";
	}
	
	
}
