package com.example.Recetas.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Recetas.Services.CalendarioServiceImpl;
import com.example.Recetas.Services.IngredientesServiceImpl;
import com.example.Recetas.Services.RecetaServiceImpl;
import com.example.Recetas.Utility.ListaCompra;
import com.example.Recetas.model.Calendario;
import com.example.Recetas.model.Receta;
import com.google.gson.Gson;

@CrossOrigin
@RestController
public class APIcalls {
	
	private class recetatime{
		public String receta;
		public String dia;
		public String tiempo;
		
		public recetatime(String receta, Timestamp tiempo) {
			super();
			this.receta = receta;
			this.dia = tiempo.toLocalDateTime().toLocalDate().toString();
			if (tiempo.getHours()==12) this.tiempo="comida";
			else this.tiempo="cenar";
		}
		
		
		
	}

	@Autowired
	RecetaServiceImpl receta=new RecetaServiceImpl();
	@Autowired
	IngredientesServiceImpl ingredientes=new IngredientesServiceImpl();
	@Autowired
	CalendarioServiceImpl calendario=new CalendarioServiceImpl();
	@Autowired
	ListaCompra lista= new ListaCompra();
	
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
	
	@PostMapping("/setcalendario")
	public ResponseEntity<?> setcalendario (@RequestBody String request){
		Gson gson = new Gson();
		Map<String, String> json = gson.fromJson(request, Map.class);
		
		Receta rec = receta.getbyname(json.get("receta"));
		calendario.save(new Calendario(rec, null));
		
		
		String response="";
		return ResponseEntity.ok(response);
		
		
	}
	
	
	
}
