package com.rodrigosampaio.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigosampaio.cursomc.domain.Pedido;
import com.rodrigosampaio.cursomc.repositories.PedidoRepository;
import com.rodrigosampaio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
