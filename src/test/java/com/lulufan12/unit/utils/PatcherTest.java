package com.lulufan12.unit.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lulufan12.model.Client;
import com.lulufan12.utils.Patcher;

@TestInstance(Lifecycle.PER_CLASS)
public class PatcherTest {
	
	private ObjectMapper mapper;
	private Patcher patcher;
	private Client model;
	
	@BeforeAll
	public void init() {
		mapper = new ObjectMapper();
		patcher = new Patcher(mapper);
		model = new Client(1L, "Test Name", "test@email.com", 18, new ArrayList<>());
	}

	@Test
	public void patchSuccessfully() {
		String json = """
				{
					"name": "Test Name 2",
					"email": "test2@email.com"
				}
			""";
		
		final var patched = patcher.patch(model, json, Client.class);
		
		assertEquals(1L, patched.getId());
		assertEquals("Test Name 2", patched.getName());
		assertEquals("test2@email.com", patched.getEmail());
		assertEquals(18, patched.getAge());
		assertEquals(Collections.EMPTY_LIST, patched.getTags());
	}
}
