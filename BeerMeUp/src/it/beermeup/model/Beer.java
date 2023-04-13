package it.beermeup.model;

import java.math.BigDecimal;

public class Beer {

	private int id = 0;
	private int produttore_id = 0;
	private int stile_id = 0;
	private String nome = "";
	private String descrizione = "";
	private String colore = "";
	private String ingredienti= "";
	private BigDecimal gradazione = new BigDecimal(0);
	private BigDecimal prezzo = new BigDecimal(0);
	private BigDecimal iva = new BigDecimal(0);
	private int stock = 0;
	private int sconto = 0;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduttore_id() {
		return produttore_id;
	}
	public void setProduttore_id(int produttore_id) {
		this.produttore_id = produttore_id;
	}
	public int getStile_id() {
		return stile_id;
	}
	public void setStile_id(int stile_id) {
		this.stile_id = stile_id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getColore() {
		return colore;
	}
	public void setColore(String colore) {
		this.colore = colore;
	}
	public String getIngredienti() {
		return ingredienti;
	}
	public void setIngredienti(String ingredienti) {
		this.ingredienti = ingredienti;
	}
	public BigDecimal getGradazione() {
		return gradazione;
	}
	public void setGradazione(BigDecimal gradazione) {
		this.gradazione = gradazione;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
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
	public int getSconto() {
		return sconto;
	}
	public void setSconto(int sconto) {
		this.sconto = sconto;
	}
	
	
	@Override
	public String toString() {
		return "Beer [id=" + id + ", nome=" + nome + ", gradazione=" + gradazione + ", prezzo=" + prezzo + ", iva="
				+ iva + ", stock=" + stock + "]";
	}
	
	
	
	
	
}
