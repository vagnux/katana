package br.com.vagnux.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

	
	
	@GetMapping("/free")
	public ResponseEntity<HashMap<String, Object>> helloMessage() {
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			
			response.put("message","Hello !");
			return ResponseEntity.ok().body(response);
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			response.put("message", e.getMessage());
			response.put("code", 424);
			return ResponseEntity.ok().body(response);
		}
	}
	
	
	@GetMapping("/loggin")
	public ResponseEntity<HashMap<String, Object>> hellologinMessage() {
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			
			response.put("message","Hello you are logged !");
			return ResponseEntity.ok().body(response);
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			response.put("message", e.getMessage());
			response.put("code", 424);
			return ResponseEntity.ok().body(response);
		}
	}
	
}
