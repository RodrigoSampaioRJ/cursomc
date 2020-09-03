package com.rodrigosampaio.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigosampaio.cursomc.domain.Cliente;
import com.rodrigosampaio.cursomc.repositories.ClienteRepository;
import com.rodrigosampaio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id) {
		
		Optional<Cliente> obj = clienteRepository.findById(id);
		
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado ! ID: " + id ));
	}

}
