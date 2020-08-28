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
	
	public Categoria buscar(Integer id) {
		
		Optional<Categoria> obj = categoriaRepository.findById(id);

		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
}
