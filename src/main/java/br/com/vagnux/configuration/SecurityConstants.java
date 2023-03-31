package br.com.vagnux.configuration;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
	public static final long JWT_EXPIRATION = 7000000;
	public static final String JWT_SECRET = UUID.randomUUID().toString();
}
