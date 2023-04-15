package br.com.vagnux;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class KatanaApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	private String accessToken;

	@Before
	public void loginAndGetAccessToken() throws JSONException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> request = new HashMap<>();
		request.put("username", "testuser");
		request.put("password", "testpassword");

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

		ResponseEntity<String> loginResponse = restTemplate.postForEntity("/login", entity, String.class);
		assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

		String responseBody = loginResponse.getBody();
		JSONObject jsonResponse = new JSONObject(responseBody);

		this.accessToken = jsonResponse.getString("access_token");
	}

	@Test
	public void testGateway() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(this.accessToken);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange("/api/example", HttpMethod.GET, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello from Example Service", response.getBody());
	}

	@Test
	void contextLoads() {
	}

}
