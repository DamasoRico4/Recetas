package com.example.Recetas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Recetas.model.Receta;


public interface RecetasRepository extends JpaRepository<Receta, Integer> {
	Receta findBynombre(String name);
}
