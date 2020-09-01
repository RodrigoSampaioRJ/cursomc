package com.rodrigosampaio.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigosampaio.cursomc.domain.Categoria;
import com.rodrigosampaio.cursomc.repositories.CategoriaRepository;
import com.rodrigosampaio.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria find(Integer id) {
		
		Optional<Categoria> obj = categoriaRepository.findById(id);

		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		
		return categoriaRepository.save(obj);
	}
	
}
