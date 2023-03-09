package com.example.Recetas.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Recetas.model.Ingredientes;


public interface IngredientesRepository extends JpaRepository<Ingredientes, Integer> {
	List<Ingredientes> findBynombre(String name);
}
