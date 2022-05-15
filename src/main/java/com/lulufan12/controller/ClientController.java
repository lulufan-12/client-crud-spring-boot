package com.lulufan12.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lulufan12.model.Client;
import com.lulufan12.service.ClientService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ClientController {
	
	private final ClientService service;
	
	@PatchMapping(path = "{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Client patch(@PathVariable Long id, @RequestBody String json) {
		return service.patch(id, json);
	}
	
	@GetMapping(path = "{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Client findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Page<Client> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Client save(@RequestBody Client model) {
		return service.save(model);
	}
	
	@DeleteMapping(path = "{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
}
