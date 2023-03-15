package com.example.Recetas.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Recetas.Repository.IngredientesRepository;
import com.example.Recetas.model.Ingredientes;
import com.example.Recetas.model.Receta;
import com.example.Recetas.model.RecetaIngredientes;

@Service
public class IngredientesServiceImpl implements IngredientesService {

	@Autowired
	private IngredientesRepository ingredientesrep;

	@Override
	public Ingredientes save(Ingredientes ingrediente) {
		return ingredientesrep.save(ingrediente);
	}

	@Override
	public Optional<Ingredientes> get(Integer id) {
		return ingredientesrep.findById(id);
	}

	@Override
	public void update(Ingredientes ingrediente) {
		ingredientesrep.save(ingrediente);
	}

	@Override
	public void delete(Integer id) {
		ingredientesrep.deleteById(id);

	}

	@Override
	public List<Ingredientes> getall() {
		return ingredientesrep.findAll();
	}

	@Override
	public Ingredientes getbyname(String name) {
		return ingredientesrep.findBynombre(name);
	}
	@Override
	public List<Ingredientes> saveall(Ingredientes... ingredientes) {
		return ingredientesrep.saveAll(Arrays.asList(ingredientes));
	}
	@Override
	public List<Receta> getrecetas(Integer id) {
		Ingredientes ing = ingredientesrep.findById(id).orElse(null);
		List<Receta> result = new ArrayList<>();
		for (RecetaIngredientes recing: ing.getRecetas()) {
			result.add(recing.getReceta());	
		}
		return result;
	}

}
