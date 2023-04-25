package it.beermeup.model;

import java.io.InputStream;
import java.math.BigDecimal;



public class Beer {

	private int id = 0;
	private int brewery_id = 0;
	private int style_id = 0;
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
	public int getBrewery_id() {
		return brewery_id;
	}
	public void setBrewery_id(int brewery_id) {
		this.brewery_id = brewery_id;
	}
	public int getStyle_id() {
		return style_id;
	}
	public void setStyle_id(int style_id) {
		this.style_id = style_id;
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
		return true;
	}
	
	@Override
	public String toString() {
		return "Beer [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
	
	
	
	
	
	
	
	
}
