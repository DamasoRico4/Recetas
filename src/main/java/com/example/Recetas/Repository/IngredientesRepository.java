package com.example.Recetas.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Recetas.model.Ingredientes;


public interface IngredientesRepository extends JpaRepository<Ingredientes, Integer> {
	Ingredientes findBynombre(String name);
}
