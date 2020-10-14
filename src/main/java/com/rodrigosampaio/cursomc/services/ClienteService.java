package com.rodrigosampaio.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.rodrigosampaio.cursomc.domain.Cidade;
import com.rodrigosampaio.cursomc.domain.Cliente;
import com.rodrigosampaio.cursomc.domain.Endereco;
import com.rodrigosampaio.cursomc.domain.enums.TipoCliente;
import com.rodrigosampaio.cursomc.dto.ClienteDTO;
import com.rodrigosampaio.cursomc.dto.NewClienteDTO;
import com.rodrigosampaio.cursomc.repositories.ClienteRepository;
import com.rodrigosampaio.cursomc.repositories.EnderecoRepository;
import com.rodrigosampaio.cursomc.services.exceptions.DataIntegrityException;
import com.rodrigosampaio.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {

		Optional<Cliente> obj = clienteRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado ! ID: " + id));
	}

	public List<Cliente> findAll() {

		List<Cliente> list = clienteRepository.findAll();

		return list;
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos()); 
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir cliente com pedidos relacionados!");
		}
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}

	public Cliente fromDTO(NewClienteDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),objDTO.getBairro(),
		 objDTO.getCep(), cli, cid);

		 cli.getEnderecos().add(end);

		 cli.getTelefones().add(objDTO.getTelefone1());
		 if(objDTO.getTelefone2() != null) {
			 cli.getTelefones().add(objDTO.getTelefone2());
		 }
		 if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}

		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
