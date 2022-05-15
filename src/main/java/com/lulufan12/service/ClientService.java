package com.lulufan12.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lulufan12.model.Client;
import com.lulufan12.repository.ClientRepository;
import com.lulufan12.utils.Patcher;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Service
@AllArgsConstructor
public class ClientService {
	
	private final Patcher patcher;
	private final ClientRepository repository;
	
	@SneakyThrows
	public Client findById(Long id) {
		Optional<Client> opt = repository.findById(id);
		
		if (opt.isEmpty()) {
			throw new Exception("Client not found");
		}
		
		return opt.get();
	}
	
	public Client save(Client model) {
		return repository.save(model);
	}
	
	@Transactional
	public Client patch(Long id, String json) {
		Client model = findById(id);
		
		final Client patched = patcher.patch(model, json, Client.class);
		
		return repository.save(patched);
	}

	public void delete(Long id) {
		Client model = findById(id);
		repository.delete(model);
	}

	public Page<Client> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
}
