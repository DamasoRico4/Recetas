package com.example.Recetas.controller;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.example.Recetas.model.Ingredientes;
import com.example.Recetas.model.Receta;
import com.example.Recetas.model.RecetaIngredientes;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

@CrossOrigin
@RestController
public class APIcalls {

	@Autowired
	private RecetaServiceImpl receta=new RecetaServiceImpl();
	@Autowired
	private IngredientesServiceImpl ingredientes=new IngredientesServiceImpl();
	@Autowired
	private CalendarioServiceImpl calendario=new CalendarioServiceImpl();
	@Autowired
	private ListaCompra lista= new ListaCompra();

	private Gson gson = new Gson();

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
	@SuppressWarnings("unused")
	private class recetatimeprecio{

		public String receta;
		public String tiempo;
		public double precio;


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



		public String getIngrediente() {
			return ingrediente;
		}



		public void setIngrediente(String ingrediente) {
			this.ingrediente = ingrediente;
		}



		public int getCantidad() {
			return cantidad;
		}



		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}



	}


	@SuppressWarnings("unchecked")
	@PostMapping("/getcalendario")
	public ResponseEntity<?> getsemana (@RequestBody String request){
		Map<String, String> json = gson.fromJson(request, Map.class);
		String tini = json.get("tini");
		String tf = json.get("tf");
		System.out.println("tini: "+tini+" tf: "+tf);
		List<Calendario> cals = calendario.getinterval(Timestamp.valueOf(tini),Timestamp.valueOf(tf));
		String response = gson.toJson(cals.stream().map( (c) -> new recetatime(c.getReceta().getNombre(),c.getFecha()) ).toList());

		return ResponseEntity.ok(response);



	}
	@SuppressWarnings("unchecked")
	@PostMapping("/setcalendario")
	public ResponseEntity<?> setcalendario (@RequestBody String request){


		Map<String, String> json = gson.fromJson(request, Map.class);
		Receta rec = receta.getbyname(json.get("receta"));
		if (rec == null) return ResponseEntity.badRequest().body("La receta no existe en BD");

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

		List<Receta> rec = receta.getall();
		String response =gson.toJson( rec.stream().map((r)-> r.tomap()).toList());
		return ResponseEntity.ok(response);

	}
	@SuppressWarnings("unchecked")
	@PostMapping("/getreceta")
	public ResponseEntity<?> getreceta(@RequestBody String request){
		Map<String, Object> json = gson.fromJson(request, Map.class);

		try {
			Receta result= receta.get( (int) json.get("id")).orElseThrow(IllegalArgumentException::new) ;
			return ResponseEntity.ok(result.tomap());

		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("La receta no existe en BBDD");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getStackTrace().toString());
		}
	}


	@SuppressWarnings("unchecked")
	@PostMapping("/setreceta")
	public ResponseEntity<?> setreceta (@RequestBody String request) {

		Map<String, Object> json = gson.fromJson(request, Map.class);
		
		ArrayList<LinkedTreeMap<String, Object>> ingredientesNuevos = (ArrayList<LinkedTreeMap<String, Object>>) json.get("ingredientesNuevos");
		ArrayList<LinkedTreeMap<String, Object>> ingrediente = (ArrayList<LinkedTreeMap<String, Object>>) json.get("ingredientescant");
		
		Receta recet = new Receta((String)json.get("nombre"),(String)json.get("momento"));
		HashSet<RecetaIngredientes> recetaIngredientes =  new HashSet<>();
		
	
		
		try {
		for(LinkedTreeMap<String, Object> x:ingredientesNuevos) {
			int cantidad = ((Double) x.get("cantidad")).intValue();
			Ingredientes ing = new Ingredientes(
					(String)(
							(LinkedTreeMap<String, Object>)
							x.get("ingrediente"))
					.get("nombre"),
					(Double)(
							(LinkedTreeMap<String, Object>)
							x.get("ingrediente"))
					.get("precio"));
			ingredientes.save(ing);
			recetaIngredientes.add(new RecetaIngredientes(recet,ing,cantidad));
			
		}
		}
		catch(DataIntegrityViolationException e){
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Un ingrediente ya existia en BD");
		}
		
		//TODO manejar errores por id incorrecta
		for(LinkedTreeMap<String, Object> x:ingrediente) {
			int cantidad = ((Double) x.get("cantidad")).intValue();
			Ingredientes ing = ingredientes.get(((Double) x.get("ingrediente")).intValue()).orElseThrow(IllegalArgumentException::new);
			recetaIngredientes.add(new RecetaIngredientes(recet,ing,cantidad));
			
		}
		
		recet.setIngredientes(recetaIngredientes);
		receta.save(recet);

		
		
		/*
		if (receta.getbyname((String)json.get("nombre"))!=null) {
			return ResponseEntity
					.badRequest()
					.body("Ya existe una receta con ese nombre");
		}
		if(ings.contains(null)) {
			String response="";	
			while(ings.contains(null)) {
				int ingNull = ings.indexOf(null);
				response+= " "+ ingcants.get(ingNull).ingrediente;
				ings.remove(null);
			}
			return ResponseEntity.badRequest().body("los siguientes ingredientes: \n"+response+" \n no existen en BD, debes crearlos primero");
		}

		IngredienteCant[] Ingcant = new IngredienteCant[ings.size()];
		System.out.println(ingcants.stream().map(ingcant::getIngrediente).toList().toString());
		System.out.println(ings.stream().map(Ingredientes::getNombre).toList().toString());
		int i=0;
		for(Ingredientes ing:ings) {
			Ingcant[i]=new IngredienteCant(ing, i);
			i++;
		}

		Receta recet = new Receta((String)json.get("nombre"),(String)json.get("momento"),Ingcant);
		receta.save(recet);



*/
		return ResponseEntity.ok("Receta guardada");



	}




}
