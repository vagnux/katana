package br.com.vagnux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vagnux.component.AuthResponse;
import br.com.vagnux.component.LoginRequest;
import br.com.vagnux.configuration.JWTGenerator;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	private PasswordEncoder passwordEncoder;
	private JWTGenerator jwtGenerator;

	@Autowired
	public AuthController(PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
	}

	@PostMapping
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
		try {
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(), loginRequest.getPassword());
		//	KatanaUserManagerService authenticationManager = new KatanaUserManagerService();
			Authentication authentication = authenticationManager.authenticate(auth);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtGenerator.generateToken(authentication);
			return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>(new AuthResponse(""), HttpStatus.FORBIDDEN);
		}
	}

}
