package com.example.Recetas.Utility;

import com.example.Recetas.controller.APIcalls;
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
@Override
public String toString() {
	// TODO Auto-generated method stub
	return this.ingrediente.getNombre()+""+this.cantidad;
}
	
	
}
