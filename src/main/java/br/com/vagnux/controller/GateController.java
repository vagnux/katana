package br.com.vagnux.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.com.vagnux.service.GateService;

@RestController
@SpringBootApplication
public class GateController {

	@Autowired
	GateService gateService;

	@GetMapping(value = { "/api/{service}", "/api/{service}/{p1}", "/api/{service}/{p1}/{p2}",
			"/api/{service}/{p1}/{p2}/{p3}", "/api/{service}/{p1}/{p2}/{p3}/{p4}",
			"/api/{service}/{p1}/{p2}/{p3}/{p4}/{p5}" })
	public ResponseEntity<String> proxyget(@RequestHeader Map<String, String> headers,
			@PathVariable(required = false) Map<String, String> pathVarsMap) {

		gateService.get();
		gateService.setHeaders(headers);
		gateService.setService(pathVarsMap.get("service"));
		;
		for (Entry<String, String> entry : pathVarsMap.entrySet()) {
			gateService.addParam(entry.getValue());
		}

		return gateService.send();
	}

	@PostMapping(value = { "/api/{service}", "/api/{service}/{p1}", "/api/{service}/{p1}/{p2}",
			"/api/{service}/{p1}/{p2}/{p3}", "/api/{service}/{p1}/{p2}/{p3}/{p4}",
			"/api/{service}/{p1}/{p2}/{p3}/{p4}/{p5}" })
	public ResponseEntity<String> proxypost(@RequestHeader Map<String, String> headers,

			@RequestBody byte[] dataPost, @PathVariable(required = false) Map<String, String> pathVarsMap)
			throws Exception {

		gateService.post();
		gateService.setHeaders(headers);
		gateService.setService(pathVarsMap.get("service"));
		;
		for (Entry<String, String> entry : pathVarsMap.entrySet()) {
			gateService.addParam(entry.getValue());
		}
		gateService.setDataPost(dataPost);
		return gateService.send();

	}

	@DeleteMapping(value = { "/api/{service}", "/api/{service}/{p1}", "/api/{service}/{p1}/{p2}",
			"/api/{service}/{p1}/{p2}/{p3}", "/api/{service}/{p1}/{p2}/{p3}/{p4}",
			"/api/{service}/{p1}/{p2}/{p3}/{p4}/{p5}" })
	public ResponseEntity<String> proxydelete(@RequestHeader Map<String, String> headers,

			@PathVariable(required = false) Map<String, String> pathVarsMap) throws Exception {

		gateService.delete();
		gateService.setHeaders(headers);
		gateService.setService(pathVarsMap.get("service"));
		;
		for (Entry<String, String> entry : pathVarsMap.entrySet()) {
			gateService.addParam(entry.getValue());
		}
		return gateService.send();

	}

	@PutMapping(value = { "/api/{service}", "/api/{service}/{p1}", "/api/{service}/{p1}/{p2}",
			"/api/{service}/{p1}/{p2}/{p3}", "/api/{service}/{p1}/{p2}/{p3}/{p4}",
			"/api/{service}/{p1}/{p2}/{p3}/{p4}/{p5}" })
	public ResponseEntity<String> proxyput(@RequestHeader(required = false) Map<String, String> headers,

			@RequestBody byte[] dataPost, @PathVariable(required = false) Map<String, String> pathVarsMap)
			throws Exception {

		gateService.put();
		gateService.setHeaders(headers);
		gateService.setService(pathVarsMap.get("service"));
		;
		for (Entry<String, String> entry : pathVarsMap.entrySet()) {
			gateService.addParam(entry.getValue());
		}
		gateService.setDataPost(dataPost);
		return gateService.send();

	}

	@PatchMapping(value = { "/api/{service}", "/api/{service}/{p1}", "/api/{service}/{p1}/{p2}",
			"/api/{service}/{p1}/{p2}/{p3}", "/api/{service}/{p1}/{p2}/{p3}/{p4}",
			"/api/{service}/{p1}/{p2}/{p3}/{p4}/{p5}" })
	public ResponseEntity<String> proxypatch(@RequestHeader(required = false) Map<String, String> headers,

			@RequestBody byte[] dataPost, @PathVariable(required = false) Map<String, String> pathVarsMap)
			throws Exception {

		gateService.patch();
		gateService.setHeaders(headers);
		gateService.setService(pathVarsMap.get("service"));
		;
		for (Entry<String, String> entry : pathVarsMap.entrySet()) {
			gateService.addParam(entry.getValue());
		}
		gateService.setDataPost(dataPost);
		return gateService.send();

	}

}
