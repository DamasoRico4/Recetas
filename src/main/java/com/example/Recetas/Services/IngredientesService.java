package com.example.Recetas.Services;

import java.util.List;
import java.util.Optional;

import com.example.Recetas.model.Ingredientes;
import com.example.Recetas.model.Receta;



public interface IngredientesService {

	public Ingredientes save(Ingredientes ingrediente);
	public Optional<Ingredientes> get(Integer id);
	public void update(Ingredientes patata);
	public void delete(Integer id); 
	public List<Ingredientes> getall ();
	public List<Ingredientes> getbyname(String name);
	public List<Ingredientes> saveall(Ingredientes... ingredientes);
	public List<Receta> getrecetas (Integer id);
	
}
