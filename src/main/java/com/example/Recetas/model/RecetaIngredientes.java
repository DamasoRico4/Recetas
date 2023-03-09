package com.example.Recetas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "receta_ingrediente" )
@IdClass(RecetaIngredientesId.class)
public class RecetaIngredientes {
	
	@Id
	@JoinColumn(name="receta")
	@ManyToOne
	private Receta receta;
	
	@Id
	@JoinColumn(name="ingrediente")
	@ManyToOne
	private Ingredientes ingrediente;
	
	@Column(nullable = false)
	private int cantidad;
	
	
	
	
	public RecetaIngredientes() {
		super();
	}
	public RecetaIngredientes(Receta receta, Ingredientes ingrediente, int cantidad) {
		super();
		this.receta = receta;
		this.ingrediente = ingrediente;
		this.cantidad = cantidad;
	}
	public Receta getReceta() {
		return receta;
	}
	public void setReceta(Receta receta) {
		this.receta = receta;
	}
	public Ingredientes getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(Ingredientes ingrediente) {
		this.ingrediente = ingrediente;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
	

}
