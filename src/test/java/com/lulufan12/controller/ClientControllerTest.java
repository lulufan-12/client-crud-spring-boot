package com.lulufan12.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.lulufan12.model.Client;
import com.lulufan12.repository.ClientRepository;
import com.lulufan12.service.ClientService;
import com.lulufan12.utils.Patcher;

@TestInstance(Lifecycle.PER_CLASS)
public class ClientControllerTest {
	
	private ClientRepository repository;
	private Patcher patcher;
	private ObjectMapper mapper;
	private ClientController controller;
	private ClientService service;
	private Client model;
	
	@BeforeAll
	public void init() {
		repository = mock(ClientRepository.class);
		mapper = new ObjectMapper();
		patcher = new Patcher(mapper);
		service = new ClientService(patcher, repository);
		controller = new ClientController(service);
		model = new Client(1L, "Test Name", "test@email.com", 18, new ArrayList<>());
	}
	
	@Test
	public void findAll() {
		when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<Client>(Collections.emptyList()));
		
		Page<Client> page = controller.findAll(Pageable.unpaged());
		
		assertEquals(0, page.getSize());
	}
	
	@Test
	public void findByIdFails() {
		when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		assertThrows(Exception.class, () -> controller.findById(1L));
	}
	
	@Test
	public void findByIdSuccessful() {
		when(repository.findById(any(Long.class))).thenReturn(Optional.of(model));
		
		final var found = controller.findById(1L);
		assertEquals(model, found);
	}
	
	@Test
	public void save() {
		when(repository.save(any(Client.class))).thenReturn(model);

		Client newClient = new Client();
		final var saved = controller.save(newClient);
		assertEquals(model, saved);
	}
	
	@Test
	public void deleteSuccessful() {
		when(repository.findById(any(Long.class))).thenReturn(Optional.of(model));
		
		controller.delete(1L);
	}
	
	@Test
	public void deleteFails() {
		when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		assertThrows(Exception.class, () -> controller.delete(1L));
	}
	
	@Test
	public void patch() {
		Client returnValue = new Client(1L, "Test Name 2", "test@email.com", 18, Collections.emptyList());
		when(repository.findById(any(Long.class))).thenReturn(Optional.of(model));
		when(repository.save(any(Client.class))).thenReturn(returnValue);
		
		String json = """
			{
				"name":"Test Name 2"
			}	
		""";
		final var patched = controller.patch(1L, json);
		
		assertEquals(1L, patched.getId());
		assertEquals("Test Name 2", patched.getName());
		assertEquals("test@email.com", patched.getEmail());
		assertEquals(18, patched.getAge());
		assertEquals(Collections.EMPTY_LIST, patched.getTags());
	}
	
}
