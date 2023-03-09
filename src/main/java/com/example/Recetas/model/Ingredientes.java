package com.example.Recetas.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="ingrediente")
public class Ingredientes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false,unique = true)
	private String nombre;
	@Column(nullable = true)
	private int precio;
	@OneToMany(mappedBy = "ingrediente",cascade = CascadeType.ALL, orphanRemoval = true)
	Set<RecetaIngredientes> recetas;
	
	
	
	
	public Ingredientes(int id, String nombre, int precio, Set<RecetaIngredientes> recetas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.recetas = recetas;
	}
	public Ingredientes() {
		super();
	}
	public Ingredientes(String nombre, int precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public Set<RecetaIngredientes> getRecetas() {
		return recetas;
	}
	public void setRecetas(Set<RecetaIngredientes> recetas) {
		this.recetas = recetas;
	}

	
	
}
