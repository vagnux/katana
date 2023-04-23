package br.com.vagnux;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.vagnux.model.ProfileModel;
import br.com.vagnux.model.RuleModel;
import br.com.vagnux.model.UserModel;
import br.com.vagnux.repository.ProfileRepository;
import br.com.vagnux.repository.RuleRepository;
import br.com.vagnux.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class KatanaApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private RuleRepository ruleRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	Environment environment;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	private String accessToken;

	private boolean dataCreate = false;

	private String userName;
	private String userPass;


	private void createUserAndAccess(String userName, String userPass) {
		if (!dataCreate) {
			long unixTime = System.currentTimeMillis() / 1000L;
			String ruleId = UUID.randomUUID().toString();
			String profileId = UUID.randomUUID().toString();
			String userId = UUID.randomUUID().toString();

			String profileName = "profile_" + String.valueOf(unixTime);

			RuleModel rule = new RuleModel();
			rule.setHttpPort(environment.getProperty("local.server.port"));
			rule.setIsEnable(true);
			rule.setIsPublic(false);
			rule.setMicroservice("localhost");
			rule.setRule("read_rule");
			rule.setId(ruleId);
			ruleRepository.save(rule);

			ProfileModel profile = new ProfileModel();

			Set<RuleModel> ruleRelation = new HashSet<RuleModel>();
			ruleRelation.add(rule);

			profile.setRules(ruleRelation);
			profile.setId(profileId);
			profile.setName(profileName + "_" + unixTime);
			profile.setIsEnable(true);
			profileRepository.save(profile);

			Set<ProfileModel> profileRelation = new HashSet<ProfileModel>();
			profileRelation.add(profile);

			UserModel user = new UserModel();
			user.setId(userId);
			user.setIsAccountNonExpired(true);
			user.setIsAccountNonLocked(true);
			user.setIsCredentialsNonExpired(true);
			user.setIsEnabled(true);
			user.setProfile(profileRelation);
			user.setUserName(userName);
			user.setPassword(passwordEncoder.encode(userPass));

			userRepository.save(user);
			this.dataCreate = true;
		}
	}

	private void loginAndGetAccessToken() throws JSONException {

		if (this.accessToken == null) {
			long unixTime = System.currentTimeMillis() / 1000L;
			
			String userName = "testUser_" + UUID.randomUUID().toString();
			String userPass = "testUser_" + String.valueOf(unixTime);
			this.userName = userName;
			this.userPass = userPass;
			this.createUserAndAccess(userName, userPass);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, String> request = new HashMap<>();
			request.put("username", this.userName);
			request.put("password", this.userPass);

			HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

			ResponseEntity<String> loginResponse = restTemplate.postForEntity("/api/auth/login", entity, String.class);
			assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

			String responseBody = loginResponse.getBody();
			JSONObject jsonResponse = new JSONObject(responseBody);

			this.accessToken = jsonResponse.getString("accessToken");
		}
	}

	@Test
	public void testGatewayGet() {

		try {
			this.loginAndGetAccessToken();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(this.accessToken);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange("/api/localhost/b1946ac92492d2347c6235b4d2611184",
				HttpMethod.GET, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"code\":200,\"method\":\"get\",\"message\":\"ok\"}", response.getBody());

	}

	@Test
	public void testGatewayPost() throws JSONException {

		try {
			this.loginAndGetAccessToken();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(this.accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject requestBody = new JSONObject();
		requestBody.put("field1", "this is a text");
		requestBody.put("field2", 123);

		HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange("/api/localhost/b1946ac92492d2347c6235b4d2611184",
				HttpMethod.POST, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"field1\":\"this is a text\",\"code\":200,\"method\":\"post\",\"field2\":123}",
				response.getBody());

	}

	@Test
	public void testGatewayPut() throws JSONException {

		try {
			this.loginAndGetAccessToken();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(this.accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject requestBody = new JSONObject();
		requestBody.put("field1", "this is a text");
		requestBody.put("field2", 123);

		HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange("/api/localhost/b1946ac92492d2347c6235b4d2611184",
				HttpMethod.PUT, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"field1\":\"this is a text\",\"code\":200,\"method\":\"put\",\"field2\":123}",
				response.getBody());

	}

	@Test
	public void testGatewayDelete() throws JSONException {

		try {
			this.loginAndGetAccessToken();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(this.accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject requestBody = new JSONObject();
		requestBody.put("field1", "this is a text");
		requestBody.put("field2", 123);

		HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange("/api/localhost/b1946ac92492d2347c6235b4d2611184/123",
				HttpMethod.DELETE, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"code\":200,\"method\":\"delete\",\"id\":\"123\"}", response.getBody());

	}


	/*  This test not work because patch is invalid method for  webclient. 
	@Test
	public void testGatewayPatch() throws JSONException {

		try {
			this.loginAndGetAccessToken();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(this.accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject requestBody = new JSONObject();
		requestBody.put("field1", "this is a text");
		requestBody.put("field2", 123);

		HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange("/api/localhost/b1946ac92492d2347c6235b4d2611184",
				HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"field1\":\"this is a text\",\"code\":200,\"method\":\"patch\",\"field2\":123}",
				response.getBody());

	}
	*/

	
}
