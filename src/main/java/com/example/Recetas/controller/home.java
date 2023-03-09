package com.example.Recetas.controller;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.Recetas.Services.CalendarioServiceImpl;
import com.example.Recetas.Services.IngredientesServiceImpl;
import com.example.Recetas.Services.RecetaServiceImpl;
import com.example.Recetas.Utility.IngredienteCant;
import com.example.Recetas.Utility.ListaCompra;
import com.example.Recetas.model.Calendario;
import com.example.Recetas.model.Ingredientes;
import com.example.Recetas.model.Receta;

@Controller
public class home {
	@Autowired
	RecetaServiceImpl receta=new RecetaServiceImpl();
	@Autowired
	IngredientesServiceImpl ingredientes=new IngredientesServiceImpl();
	@Autowired
	CalendarioServiceImpl calendario=new CalendarioServiceImpl();
	@Autowired
	ListaCompra lista= new ListaCompra();
	
	@GetMapping("/homew")
	public ModelAndView homew() {
		ModelAndView tabla=new ModelAndView("index");
		tabla.addObject("Recetas",receta.getall());
		tabla.addObject("Ingredientes",ingredientes.getall());
		tabla.addObject("Calendario",calendario.getall());
		tabla.addObject("Lista",lista.genLista(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()+604800000)));
		return tabla;
	}
	
	@GetMapping("/rellenar")
	public String rellenar() {
		
		IngredienteCant manzana = new IngredienteCant( new Ingredientes("manzana",5),3);
		IngredienteCant lecheCoco =new IngredienteCant( new Ingredientes("leche de coco",7),1);
		IngredienteCant curry = new IngredienteCant( new Ingredientes("curry",3),999);
		IngredienteCant patata = new IngredienteCant( new Ingredientes("patata",1),5);
		Receta curry2 = new Receta("curry","mediodia",curry,manzana,lecheCoco);
		Receta patataAsada = new Receta("Patata asada","ambos",patata,curry);
		Receta manzanaCoco = new Receta("Manzana con coco","tarde",manzana,lecheCoco);
		Calendario hoy= new Calendario(curry2,Timestamp.valueOf("2023-03-10 12:00:00"));
		Calendario mañana = new Calendario(patataAsada,Timestamp.valueOf("2023-03-11 12:00:00"));
		Calendario mañana2 = new Calendario(manzanaCoco,Timestamp.valueOf("2023-03-11 21:00:00"));
		try {
		receta.saveall(curry2,patataAsada,manzanaCoco);
		}
		catch (DataIntegrityViolationException e) {
			System.out.println("se repite");

		}
		try {
		calendario.saveall(hoy,mañana,mañana2);
		}
		catch (DataIntegrityViolationException e) {
			System.out.println("se repite");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return "okay";
		
	}


}
