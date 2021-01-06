package com.example.MyApp;

import java.io.IOException;
import java.util.UUID;
import javax.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class HelloWorldController {

    ObjectMapper mapper = new ObjectMapper();

	@PostMapping(path = "/request", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String request(@RequestBody Body payload) throws JsonMappingException, JsonProcessingException {
	    String uniqueID = UUID.randomUUID().toString();
	    Request req=new Request(payload.getBody(),uniqueID);
	    try {
			makeThirdPartyRequest(req);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "request initiated for document:"+payload.getBody();
	}
	/*
	 * curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"body":"value"}' http://localhost:8080/request
request initiated for document:value
	 */
	private String makeThirdPartyRequest(Request req) throws IOException, InterruptedException {
		return "";
    }
	
	@PostMapping("/callback")
	@ResponseStatus
	public Response postCallback(@RequestParam(value = "name", defaultValue = "2") String value) {
		return Response.status(204).build();
	}

	@PutMapping("/callback")
	@ResponseStatus
	public Response putCallback(@RequestBody Body callback) {
		return Response.status(204).build();
	}
	
	@GetMapping("/status")
	public Object doSomething(@RequestParam(value = "id", defaultValue = "") Integer id) {
		return new Object();
	}
	
}

// http://localhost:8080/greeting?name=blah
// ./mvnw spring-boot:run

//(may have to run mvn -N io.takari:maven:wrapper to get .mvn directory)