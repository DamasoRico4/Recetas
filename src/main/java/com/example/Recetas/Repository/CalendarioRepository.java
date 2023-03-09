package com.example.Recetas.Repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Recetas.model.Calendario;
import com.example.Recetas.model.CalendarioId;


public interface CalendarioRepository extends JpaRepository<Calendario, CalendarioId> {
 public List<Calendario> findByfechaBetween(Timestamp tinicial,Timestamp tfinal);
 
}
