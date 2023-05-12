package it.beermeup.model;

public class Brewery {
	
	private int id;
	private String name;
	private String story;
	private String nation;
	
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
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	@Override
	public String toString() {
		return "Brewery [id=" + id + ", name=" + name + ", nation=" + nation + "]";
	}
	
	
}
