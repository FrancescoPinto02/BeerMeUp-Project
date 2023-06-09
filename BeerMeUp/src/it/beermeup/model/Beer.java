package it.beermeup.model;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;



public class Beer {

	private int id = 0;
	private int breweryId = 0;
	private int styleId = 0;
	private String name = "";
	private String description = "";
	private String color = "";
	private String ingredients= "";
	private BigDecimal gradation = new BigDecimal(0);
	private BigDecimal price = new BigDecimal(0);
	private BigDecimal iva = new BigDecimal(0);
	private int stock = 0;
	private int discount = 0;
	private String base64Image = "";
	private InputStream inputStreamImage = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBreweryId() {
		return breweryId;
	}
	public void setBreweryId(int breweryId) {
		this.breweryId = breweryId;
	}
	public int getStyleId() {
		return styleId;
	}
	public void setStyleId(int styleId) {
		this.styleId = styleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public BigDecimal getGradation() {
		return gradation;
	}
	public void setGradation(BigDecimal gradation) {
		this.gradation = gradation;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public BigDecimal getPrice(boolean discount) {
		if(!discount) {
			return this.getPrice();
		}
		else {
			BigDecimal sconto = (this.getPrice().divide(new BigDecimal(100))).multiply(new BigDecimal(this.getDiscount()));
			return (this.getPrice().subtract(sconto)).setScale(2, RoundingMode.HALF_EVEN);
		}
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getIva() {
		return iva;
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
	public InputStream getInputStreamImage() {
		return inputStreamImage;
	}
	public void setInputStreamImage(InputStream inputStreamImage) {
		this.inputStreamImage = inputStreamImage;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Beer other = (Beer) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Beer [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
	
	
	
	
	
	
	
	
}
