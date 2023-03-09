package com.example.Recetas.Services;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Recetas.Repository.CalendarioRepository;
import com.example.Recetas.model.Calendario;
import com.example.Recetas.model.CalendarioId;

@Service
public class CalendarioServiceImpl implements CalendarioService {

	@Autowired
	private CalendarioRepository calrep;
	
	@Override
	public Calendario save(Calendario calendario) {
		return calrep.save(calendario);
	}

	@Override
	public Optional<Calendario> get(CalendarioId id) {
		return calrep.findById(id);
	}

	@Override
	public void update(Calendario calendario) {
		calrep.save(calendario);
	}

	@Override
	public void delete(CalendarioId id) {
		calrep.deleteById(id);

	}

	@Override
	public List<Calendario> getall() {
		return calrep.findAll();
	}
	@Override
	public List<Calendario> saveall(Calendario... calendario) {
		return calrep.saveAll(Arrays.asList(calendario));
	}
	@Override
	public List<Calendario> getinterval(Timestamp tinicial, Timestamp tfinal) {
		return calrep.findByfechaBetween(tinicial, tfinal);
	}

}
