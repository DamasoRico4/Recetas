package com.example.Recetas.model;


import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="calendario")
@IdClass(CalendarioId.class)
public class Calendario {
	
	@Id
	@JoinColumn(name="receta",referencedColumnName = "id")
	@ManyToOne
	private Receta receta;
	@Id
	private java.sql.Timestamp fecha;
	
	
	
	public Calendario() {
		super();
	}

	public Calendario(Receta receta, Timestamp fecha) {
		super();
		this.receta = receta;
		this.fecha = fecha;
	}

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public java.sql.Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(java.sql.Timestamp fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.receta.getNombre()+": "+this.fecha.toString();
	}
	
	
}
