package com.example.Recetas.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Recetas.Services.CalendarioServiceImpl;
import com.example.Recetas.Services.IngredientesServiceImpl;
import com.example.Recetas.Services.RecetaServiceImpl;
import com.example.Recetas.Utility.IngredienteCant;
import com.example.Recetas.Utility.ListaCompra;
import com.example.Recetas.model.Calendario;
import com.example.Recetas.model.Receta;
import com.example.Recetas.model.RecetaIngredientes;
import com.google.gson.Gson;

@CrossOrigin
@RestController
public class APIcalls {
	
	@Autowired
	RecetaServiceImpl receta=new RecetaServiceImpl();
	@Autowired
	IngredientesServiceImpl ingredientes=new IngredientesServiceImpl();
	@Autowired
	CalendarioServiceImpl calendario=new CalendarioServiceImpl();
	@Autowired
	ListaCompra lista= new ListaCompra();
	
	@SuppressWarnings("unused")
	private class recetatime{
		
		public String receta;
		public String dia;
		public String tiempo;
		
		@SuppressWarnings("deprecation")
		public recetatime(String receta, Timestamp tiempo) {
			super();
			this.receta = receta;
			this.dia = tiempo.toLocalDateTime().toLocalDate().toString();
			if (tiempo.getHours()==12) this.tiempo="comida";
			else this.tiempo="cena";
		}
	}
	private class recetatimeprecio{
		public String receta;
		public String tiempo;
		public int precio;
		
		
		public recetatimeprecio(Receta recet) {
			this.receta=recet.getNombre();
			this.tiempo=recet.getMomento();
			this.precio= recet.getprecio();
			
					
		}


		public recetatimeprecio(String receta, String tiempo, int precio) {
			super();
			this.receta = receta;
			this.tiempo = tiempo;
			this.precio = precio;
		}
		
		
		
	}
	@SuppressWarnings("unused")
private class ingcant {
	public String ingrediente;
	public int cantidad;
	
	
	
	public ingcant(IngredienteCant ingredientecantidad) {
		
		this.ingrediente = ingredientecantidad.ingrediente.getNombre();
		this.cantidad = ingredientecantidad.cantidad;
	}



	public ingcant(String ing, int cant) {
		super();
		this.ingrediente = ing;
		this.cantidad = cant;
	}
	
	
}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/getcalendario")
	public ResponseEntity<?> getsemana (@RequestBody String request){
		Gson gson = new Gson();
		Map<String, String> json = gson.fromJson(request, Map.class);
		
		
		
		
		List<Calendario> cals = calendario.getinterval(Timestamp.valueOf(json.get("tini")),Timestamp.valueOf(json.get("tf")));
		List<recetatime> retime = new ArrayList<>();
		
		for(Calendario cal: cals) {
		retime.add(new recetatime(cal.getReceta().getNombre(), cal.getFecha()));			
		}
		
		
		String response = gson.toJson(retime) ;
		
		
		return ResponseEntity.ok(response);
			
		
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/setcalendario")
	public ResponseEntity<?> setcalendario (@RequestBody String request){
		Gson gson = new Gson();
		Map<String, String> json = gson.fromJson(request, Map.class);
		Receta rec = receta.getbyname(json.get("receta"));
		Timestamp fecha = Timestamp.valueOf(json.get("fecha") + ((json.get("tiempo").equals("mediodia")) ? " 12:00:00":" 21:00:00" ));
		try {
		System.out.println(calendario.save(new Calendario(rec, fecha)).toString());
		}
		catch(DataIntegrityViolationException e) {
			return ResponseEntity.status(409).body("Error al insertar en BD. Entrada duplicada");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.toString());
		}
		
		return ResponseEntity.ok("inserted");
		
		
	}
	
	@GetMapping("/getrecetas")
	public ResponseEntity<?> getrecetas(){
		List<recetatimeprecio> recetas = new ArrayList<>();
		List<Receta> rec = receta.getall();
		for(Receta recet: rec) {
			recetas.add(new recetatimeprecio(recet));
			
		}
		
		String response = new Gson().toJson(recetas);
		return ResponseEntity.ok(response);
		
	}
	@PostMapping("/getingredientes")
	public ResponseEntity<?> getingredientes (@RequestBody String request){
		Gson gson = new Gson();
		Map<String, String> json = gson.fromJson(request, Map.class);
		List<IngredienteCant> ingcant = receta.getbyname(json.get("receta")).getingcant();
		List<ingcant> result = new ArrayList<>();
		for(IngredienteCant ican: ingcant) {
			result.add(new ingcant(ican));
			
			
		}
		String response = gson.toJson(result); 
		return ResponseEntity.ok(response);
		
	}
	
	
	
	
}
