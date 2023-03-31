package br.com.vagnux.component;

public class AuthResponse {
	private String accessToken;
	private String tokenType = "Bearer ";

	public AuthResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
