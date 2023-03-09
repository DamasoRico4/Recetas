package com.example.Recetas.model;

import java.io.Serializable;


public class RecetaIngredientesId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6920583482561206498L;
	private int receta;
	private int ingrediente;

	
	
	
	public int getReceta() {
		return receta;
	}

	public void setReceta(int receta) {
		this.receta = receta;
	}

	public int getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(int ingrediente) {
		this.ingrediente = ingrediente;
	}

	@Override
	public boolean equals(Object obj) {
		RecetaIngredientesId ingobj= (RecetaIngredientesId) obj;
		return (this.ingrediente == ingobj.ingrediente && this.receta == ingobj.receta);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
