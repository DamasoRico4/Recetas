package com.example.Recetas.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Recetas.Repository.RecetasRepository;
import com.example.Recetas.Utility.IngredienteCant;
import com.example.Recetas.model.Ingredientes;
import com.example.Recetas.model.Receta;
import com.example.Recetas.model.RecetaIngredientes;

@Service
public class RecetaServiceImpl implements RecetaService {

	@Autowired
	private RecetasRepository recetarep;
	
	@Override
	public Receta save(Receta receta) {
		return recetarep.save(receta);
	}

	@Override
	public Optional<Receta> get(Integer id) {
		// TODO Auto-generated method stub
		return recetarep.findById(id);
	}

	@Override
	public void update(Receta receta) {
		recetarep.save(receta);
	}

	@Override
	public void delete(Integer id) {
		recetarep.deleteById(id);

	}

	@Override
	public List<Receta> getall() {
		return recetarep.findAll();
	}

	@Override
	public Receta getbyname(String name) {
		return recetarep.findBynombre(name);
	}
	@Override
	public List<Receta> saveall(Receta... recetas) {
		return recetarep.saveAll(Arrays.asList(recetas));
		
	}
	@Override
	public List<Ingredientes> getingredientes(Integer id) {
		Receta receta = recetarep.findById(id).orElse(null);
		List<Ingredientes> result = new ArrayList<Ingredientes>();
		for (RecetaIngredientes recing: receta.getIngredientes()) {
			result.add(recing.getIngrediente());
		}
		return result;
	}
	
	@Override
	public List<IngredienteCant> getingredientecant(Integer id) {
		Receta receta = recetarep.findById(id).orElse(null);
		List<IngredienteCant> result = new ArrayList<IngredienteCant>();
		for (RecetaIngredientes recing: receta.getIngredientes()) {
			result.add(new IngredienteCant (recing.getIngrediente(), recing.getCantidad()));
		}
		return result;
		
		
	}
	

}
