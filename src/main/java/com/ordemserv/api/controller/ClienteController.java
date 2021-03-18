package com.ordemserv.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ordemserv.domain.model.Cliente;
import com.ordemserv.domain.repository.ClienteRepository;
import com.ordemserv.domain.servece.CadastroClienteServece;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
//	@PersistenceContext
//	private EntityManager meneger;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteServece cadCliServece;
	
	@GetMapping
	public List<Cliente> listar() {

		return clienteRepository.findAll();
//		return clienteRepository.findByNomeContaining("o");
//		return clienteRepository.findByNome("Everton");
		
		
//		TESTE 02
//		return meneger.createQuery("from Cliente", Cliente.class).getResultList();
		
		
//		TESTE 01
//		var cliente1 = new Cliente(1L, "Everton", "evegadea@gmail.com", "719999999");
//		var cliente2 = new Cliente(2L, "João", "joao@gmail.com", "719999999");
//		var cliente3 = new Cliente(3L, "Paulo", "paulo@gmail.com", "719999999");
//		return Arrays.asList(cliente1, cliente2, cliente3);
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
		
//		return cliente.orElse(null);
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar (@Valid @RequestBody Cliente cliente) {
		return cadCliServece.salvar(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId,@RequestBody Cliente cliente) {
		
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId);
		cliente = clienteRepository.save(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable long clienteId) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadCliServece.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}
}
