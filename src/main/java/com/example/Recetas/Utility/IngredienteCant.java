package com.example.Recetas.Utility;

import com.example.Recetas.model.Ingredientes;

public class IngredienteCant {

	public Ingredientes ingrediente;
	public int cantidad;
	public IngredienteCant(Ingredientes ingrediente, int cantidad) {
		super();
		this.ingrediente = ingrediente;
		this.cantidad = cantidad;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		return this.ingrediente.getId() == ((IngredienteCant)obj).ingrediente.getId();
	}
	
}
