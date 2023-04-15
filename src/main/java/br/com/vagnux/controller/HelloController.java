package br.com.vagnux.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/b1946ac92492d2347c6235b4d2611184")
public class HelloController {
	private static Logger logger = LoggerFactory.getLogger(HelloController.class);

	@GetMapping("/")
	public ResponseEntity<HashMap<String, Object>> geter(@RequestHeader Map<String, String> headers) throws Exception {
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (var entry : headers.entrySet()) {
			System.out.println("Header : " + entry.getKey() + " -> " + entry.getValue());
		}
		response.put("code", 200);
		response.put("method", "get");
		response.put("message", "ok");
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/")
	public ResponseEntity<HashMap<String, Object>> recept(@RequestHeader Map<String, String> headers,
			@RequestBody HashMap<String, Object> values) throws Exception {
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (var entry : headers.entrySet()) {
			System.out.println("Header : " + entry.getKey() + " -> " + entry.getValue());
		}

		for (var entry : values.entrySet()) {
			System.out.println("body value : " + entry.getKey() + " -> " + entry.getValue());
			response.put(entry.getKey(),  entry.getValue());
		}
		response.put("code", 200);
		response.put("method", "post");
		return ResponseEntity.status(200).body(response);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HashMap<String, Object>> del(@RequestHeader Map<String, String> headers,@PathVariable String id) throws Exception {
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (var entry : headers.entrySet()) {
			System.out.println("Header : " + entry.getKey() + " -> " + entry.getValue());
		}
		response.put("code", 200);
		response.put("method", "delete");
		response.put("id", id);
		return ResponseEntity.status(200).body(response);
	}
	
	
	@PutMapping("/")
	public ResponseEntity<HashMap<String, Object>> putting(@RequestHeader Map<String, String> headers,
			@RequestBody HashMap<String, Object> values) throws Exception {
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (var entry : headers.entrySet()) {
			System.out.println("Header : " + entry.getKey() + " -> " + entry.getValue());
		}

		for (var entry : values.entrySet()) {
			System.out.println("body value : " + entry.getKey() + " -> " + entry.getValue());
			response.put(entry.getKey(),  entry.getValue());
		}
		response.put("code", 200);
		response.put("method", "put");
		return ResponseEntity.status(200).body(response);
	}
	
	
	@PatchMapping("/")
	public ResponseEntity<HashMap<String, Object>> patching(@RequestHeader Map<String, String> headers,
			@RequestBody HashMap<String, Object> values) throws Exception {
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (var entry : headers.entrySet()) {
			System.out.println("Header : " + entry.getKey() + " -> " + entry.getValue());
		}

		for (var entry : values.entrySet()) {
			System.out.println("body value : " + entry.getKey() + " -> " + entry.getValue());
			response.put(entry.getKey(),  entry.getValue());
		}
		response.put("code", 200);
		response.put("method", "patch");
		return ResponseEntity.status(200).body(response);
	}
}
