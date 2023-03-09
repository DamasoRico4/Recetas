package com.example.Recetas.Services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.example.Recetas.model.Calendario;
import com.example.Recetas.model.CalendarioId;


public interface CalendarioService {

	public Calendario save(Calendario calendario);
	public Optional<Calendario> get(CalendarioId id);
	public void update(Calendario calendario);
	public void delete(CalendarioId id); 
	public List<Calendario> getall ();
	public List<Calendario> saveall(Calendario... calendario);
	public List<Calendario> getinterval (Timestamp tinicial,Timestamp tfinal);
	
	
}
