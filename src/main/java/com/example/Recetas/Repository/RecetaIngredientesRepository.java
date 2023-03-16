package com.example.Recetas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Recetas.model.Ingredientes;
import com.example.Recetas.model.RecetaIngredientes;
import com.example.Recetas.model.RecetaIngredientesId;

public interface RecetaIngredientesRepository extends JpaRepository<RecetaIngredientes, RecetaIngredientesId> {
	Ingredientes findByreceta(int receta);
}