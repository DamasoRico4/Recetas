package com.example.Recetas.model;

import java.io.Serializable;


public class CalendarioId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2387060023091567853L;
	private int receta;
	private java.sql.Timestamp fecha;

	@Override
	public boolean equals(Object obj) {
		CalendarioId calobj= (CalendarioId) obj;
		return (this.fecha == calobj.fecha && this.receta == calobj.receta);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
}
