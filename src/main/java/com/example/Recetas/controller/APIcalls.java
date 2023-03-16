package com.example.Recetas.controller;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
		String response =gson.toJson( rec.stream().map((r)-> new recetatimeprecio(r)).toList());
		return ResponseEntity.ok(response);

	}
	@PostMapping("/getingredientesreceta")
	public ResponseEntity<?> getingredientesreceta (@RequestBody String request){

		String jreceta = gson.fromJson(request, JsonObject.class).get("receta").getAsString();
		Receta rec = receta.getbyname(jreceta);
		if (rec == null) return ResponseEntity.badRequest().body("La receta no existe en BD");

		List<IngredienteCant> ingcants = rec.getingcant();
		//String response = gson.toJson( ingcants.stream().map( (ic)-> new ingcant(ic)).toList()); 
		return ResponseEntity.ok(gson.toJson(ingcants));

	}
	@SuppressWarnings("unchecked")
	@PostMapping("/setreceta")
	public ResponseEntity<?> setreceta (@RequestBody String request) {

		Map<String, Object> json = gson.fromJson(request, Map.class);

		Type ingcantType = new TypeToken<ArrayList<ingcant>>(){}.getType();

		ArrayList<ingcant> ingcants = gson.fromJson(gson.toJson(json.get("ingredientescant")), ingcantType);

		List<String> names = ingcants.stream().map(ingcant ::getIngrediente).toList();

		List<Ingredientes> ings= names.stream().map((n)->ingredientes.getbyname(n)).collect(Collectors.toCollection(LinkedList::new));

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




		return ResponseEntity.ok("Receta guardada");



	}




}
