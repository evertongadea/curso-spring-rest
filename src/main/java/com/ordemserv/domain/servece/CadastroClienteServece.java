package com.ordemserv.domain.servece;

import org.hibernate.hql.internal.ast.tree.IsNotNullLogicOperatorNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordemserv.domain.exception.NegocioException;
import com.ordemserv.domain.model.Cliente;
import com.ordemserv.domain.repository.ClienteRepository;

@Service
public class CadastroClienteServece {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if (clienteExistente != null && !cliente.equals(clienteExistente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail!");
		}
		return clienteRepository.save(cliente);
	}
	
	public void excluir(long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
}
