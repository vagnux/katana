package br.com.vagnux.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import br.com.vagnux.service.GateService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@SpringBootApplication
public class GateController {

	@Autowired
	GateService gateService;

	@GetMapping(value = { "/api/**" })
	public ResponseEntity<String> proxyget(@RequestHeader Map<String, String> headers, HttpServletRequest pathVars) {

		Map<String, String> pathVarsMap = resolvePath(pathVars);
		return this.execute("get", headers, pathVarsMap, null);
	}

	@PostMapping(value = { "/api/**" })
	public ResponseEntity<String> proxypost(@RequestHeader Map<String, String> headers,

			@RequestBody byte[] dataPost, HttpServletRequest pathVars) throws Exception {

		Map<String, String> pathVarsMap = resolvePath(pathVars);
		return this.execute("post", headers, pathVarsMap, dataPost);

	}

	@DeleteMapping(value = { "/api/**" })
	public ResponseEntity<String> proxydelete(@RequestHeader Map<String, String> headers,

			HttpServletRequest pathVars) throws Exception {

		Map<String, String> pathVarsMap = resolvePath(pathVars);
		return this.execute("delete", headers, pathVarsMap, null);

	}

	@PutMapping(value = { "/api/**" })
	public ResponseEntity<String> proxyput(@RequestHeader(required = false) Map<String, String> headers,

			@RequestBody byte[] dataPost, HttpServletRequest pathVars) throws Exception {

		Map<String, String> pathVarsMap = resolvePath(pathVars);
		return this.execute("put", headers, pathVarsMap, dataPost);

	}

	@PatchMapping(value = { "/api/**" })
	public ResponseEntity<String> proxypatch(@RequestHeader(required = false) Map<String, String> headers,

			@RequestBody byte[] dataPost, HttpServletRequest pathVars) throws Exception {
		Map<String, String> pathVarsMap = resolvePath(pathVars);
		return this.execute("patch", headers, pathVarsMap, dataPost);

	}

	private Map<String, String> resolvePath(HttpServletRequest pathVarsMap) {

		String restOfTheUrl = new AntPathMatcher().extractPathWithinPattern(
				pathVarsMap.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(),
				pathVarsMap.getRequestURI());
		String[] params = restOfTheUrl.split("/");
		HashMap<String, String> result = new HashMap<>();
		result.put("service", params[0]);
		String[] othersParams = Arrays.copyOfRange(params, 1, params.length);
		int i = 0;
		for (String p : othersParams) {
			result.put("p" + i, p);
			i++;
		}

		return result;
	}

	private ResponseEntity<String> execute(String method, Map<String, String> headers, Map<String, String> pathVarsMap,
			byte[] dataPost) {

		if (method.equals("get")) {
			gateService.get();
		}

		if (method.equals("delete")) {
			gateService.delete();
		}

		if (method.equals("put")) {
			gateService.put();
			gateService.setDataPost(dataPost);
		}

		if (method.equals("patch")) {
			gateService.patch();
			gateService.setDataPost(dataPost);
		}

		if (method.equals("post")) {
			gateService.post();
			gateService.setDataPost(dataPost);
		}

		gateService.setHeaders(headers);
		gateService.setService(pathVarsMap.get("service"));
		for (Entry<String, String> entry : pathVarsMap.entrySet()) {
			gateService.addParam(entry.getValue());
		}
		gateService.setDataPost(dataPost);
		return gateService.send();

	}

}
