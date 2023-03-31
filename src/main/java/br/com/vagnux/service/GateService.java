package br.com.vagnux.service;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GateService {

	@Autowired
	AccessService accessService;

	private String service = "";
	private String method = "get";
	private String port = "8080";
	private Map<String, String> headers;
	private List<String> uri = new ArrayList<String>();;
	private byte[] dataPost;

	public void addParam(String param) {
		this.uri.add(param.trim());
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public byte[] getDataPost() {
		return dataPost;
	}

	public void setDataPost(byte[] dataPost) {
		this.dataPost = dataPost;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void get() {
		this.method = "get";
	}

	public void post() {
		this.method = "post";
	}

	public void put() {
		this.method = "put";
	}

	public void patch() {
		this.method = "patch";
	}

	public void delete() {
		this.method = "delete";
	}

	public void setService(String service) {
		this.service = service.trim();
	}

	public ResponseEntity<String> send() {
		try {

			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (accessService.hasAccess(service, userDetails)) {

				this.port = accessService.getServicePort(service, userDetails);

				var url = "http://" + this.service + ":" + this.port;

				for (String str : this.uri) {
					if (!str.equals(this.service)) {
						url = url + "/" + str;
					}
				}
				this.uri.clear();

				String[] denyWords = { "connection", "content-length", "host", "http2-settings", "upgrade",
						"user-agent" };
				List denyHeaders = Arrays.asList(denyWords);

				Builder request1 = HttpRequest.newBuilder().uri(new URI(url));

				for (Entry<String, String> entry : this.headers.entrySet()) {
					if (!entry.getKey().substring(0, 4).toUpperCase().equals("ACL_")) {
						if (!denyHeaders.contains(entry.getKey())) {
							((Builder) request1).header(entry.getKey(), entry.getValue());
						}
					}
				}
				((Builder) request1).header("Content-Type", "application/json");
				((Builder) request1).header("UserName", userDetails.getUsername());
				((Builder) request1).header("UID", accessService.getUid(userDetails.getUsername()));

				List<String> aclList = accessService.aclList(service, userDetails.getUsername());
				for (String access : aclList) {
					((Builder) request1).header("ACL_" + access.toString(), access.toString());
				}

				if (this.method.toString().equals("get")) {
					((Builder) request1).GET();
				}

				if (this.method.toString().equals("post")) {
					((Builder) request1).POST(
							HttpRequest.BodyPublishers.ofInputStream(() -> new ByteArrayInputStream(this.dataPost)));
				}

				if (this.method.toString().equals("put")) {
					((Builder) request1).PUT(
							HttpRequest.BodyPublishers.ofInputStream(() -> new ByteArrayInputStream(this.dataPost)));
				}

				if (this.method.toString().equals("patch")) {
					// Não tem metodo Patch estou usando o PUT mas isso não é algo bonito de fazer
					// :(
					((Builder) request1).PUT(
							HttpRequest.BodyPublishers.ofInputStream(() -> new ByteArrayInputStream(this.dataPost)));
				}

				if (this.method.toString().equals("delete")) {
					((Builder) request1).DELETE();
				}

				HttpRequest request = request1.build();

				HttpClient httpClient = HttpClient.newHttpClient();
				var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

				return ResponseEntity.ok().body(response.body());
			} else {
				return ResponseEntity.status(401).body("{}");
			}
		} catch (Exception ex) {
			return ResponseEntity.status(500).body("{\"message\":\"" + ex.getMessage() + "\"}");
		}
	}

}
