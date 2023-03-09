package com.example.Recetas.Services;

import java.util.List;
import java.util.Optional;

import com.example.Recetas.Utility.IngredienteCant;
import com.example.Recetas.model.Ingredientes;
import com.example.Recetas.model.Receta;



public interface RecetaService {

	public Receta save(Receta receta);
	public Optional<Receta> get(Integer id);
	public void update(Receta receta);
	public void delete(Integer id); 
	public List<Receta> getall ();
	public Receta getbyname(String name);
	public List<Receta> saveall (Receta... recetas);
	public List<Ingredientes> getingredientes (Integer id);
	public List<IngredienteCant> getingredientecant (Integer id);
	
}
