package com.lulufan12.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Component
@AllArgsConstructor
public class Patcher {
	
	private final ObjectMapper mapper;
	
	@SneakyThrows
	public <E, T> T patch(E model, String json, Class<T> returnType) {		
		JsonNode node = mapper.convertValue(model, JsonNode.class);
		JsonMergePatch patch = mapper.readValue(json, JsonMergePatch.class);
		
		final JsonNode patched = patch.apply(node);
		
		return mapper.convertValue(patched, returnType);
	}
	
}
