package com.example.Recetas.Utility;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Recetas.Services.CalendarioServiceImpl;
import com.example.Recetas.model.Calendario;
import com.example.Recetas.model.RecetaIngredientes;

@Service
public class ListaCompra {
	@Autowired
	private CalendarioServiceImpl calser= new CalendarioServiceImpl();
	
	public List<IngredienteCant> genLista(Timestamp tinicial,Timestamp tfinal) {
		List<IngredienteCant> result = new ArrayList<IngredienteCant>();
		System.out.println(calser.getinterval(tinicial, tfinal).toString());
		for(Calendario cals: calser.getinterval(tinicial, tfinal)) {
			System.out.println(cals.toString());
			Set<RecetaIngredientes> receta = cals.getReceta().getIngredientes();
			for(RecetaIngredientes receing: receta) {
				
				IngredienteCant ingcant = new IngredienteCant(receing.getIngrediente(), receing.getCantidad());
				if(result.contains(ingcant)) {
					
					result.get(result.indexOf(ingcant)).cantidad+= ingcant.cantidad;
					
				}
				else {
			result.add(ingcant);
				}
			}

		}
		return result;


	}


}
