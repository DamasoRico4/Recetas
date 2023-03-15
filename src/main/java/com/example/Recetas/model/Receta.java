package com.example.Recetas.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import com.example.Recetas.Utility.IngredienteCant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="receta")
public class Receta{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false,unique = true)
	private String nombre;
	
	@Column(name="momento")
	private String momento;
	
	@OneToMany(mappedBy = "receta",cascade = CascadeType.ALL, orphanRemoval = true)
	Set<RecetaIngredientes> recetaingredientes;
	
 @OneToMany(mappedBy = "receta")
 Set<Calendario> fechas;
	
	
	
	public Receta() {
	super();
}

	

	public Receta(int id, String nombre, Set<RecetaIngredientes> recetaingredientes, Set<Calendario> fechas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.recetaingredientes = recetaingredientes;
		this.fechas = fechas;
	}



	public Receta(String nombre,String momento, IngredienteCant... ingredientecants ) {
		super();
		this.nombre = nombre;
		this.momento = momento;
		this.recetaingredientes = new HashSet<>();
		for(IngredienteCant ingredientecant : ingredientecants) {
			
			RecetaIngredientes recetaingrediente = new RecetaIngredientes(this,ingredientecant.ingrediente,ingredientecant.cantidad);
			
			this.recetaingredientes.add(recetaingrediente);
		}
	}



	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMomento() {
		return momento;
	}

	public void setMomento(String momento) {
		this.momento = momento;
	}

	public Set<RecetaIngredientes> getIngredientes() {
		return recetaingredientes;
	}

	public void setIngredientes(Set<RecetaIngredientes> recetaingredientes) {
		this.recetaingredientes = recetaingredientes;
	}

	public Set<Calendario> getFechas() {
		return fechas;
	}

	public void setFechas(Set<Calendario> fechas) {
		this.fechas = fechas;
	}
	
	public List<IngredienteCant> getingcant () {
		return recetaingredientes.stream().map( (r)-> new IngredienteCant(r.getIngrediente(), r.getCantidad()) ).toList();
		
		/*List<IngredienteCant> result =new ArrayList<>();
		 * List<IngredienteCant> result =new ArrayList<>();
		 * for(RecetaIngredientes receing: recetaingredientes) {
		 * result.add(new IngredienteCant(receing.getIngrediente(), receing.getCantidad()));
		 * } */
		
	}
	
	public int getprecio (){
		int result=0;
		for(RecetaIngredientes receing: recetaingredientes) {			
			result+=receing.getCantidad()*receing.getIngrediente().getPrecio();
		}
		return result;		
	}


	
	
	
	
	
	
	
	
}