package it.beermeup.model;

public class Style {
	private int id = 0;
	private String name = "";
	private String traits = "";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTraits() {
		return traits;
	}
	public void setTraits(String traits) {
		this.traits = traits;
	}
	
	@Override
	public String toString() {
		return "Style [id=" + id + ", name=" + name + "]";
	}
	
	
}
