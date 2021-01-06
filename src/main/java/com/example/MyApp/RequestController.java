package com.example.MyApp;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class RequestController {

	ObjectMapper mapper = new ObjectMapper();

	@PostMapping(path = "/request", consumes = "application/json", produces = "application/json")
	public String request(@RequestBody Body payload) throws JsonMappingException, JsonProcessingException {
		//TODO error handling for body
		String uniqueID = UUID.randomUUID().toString();
		Request req=new Request(payload.getBody(),uniqueID);
		boolean success=makeThirdPartyRequest(req);
		if(success) {
			return "Initiated request for document:"+payload.getBody();
		}else {
			return "FAILED to make request for document:"+payload.getBody();
		}
	}

	private boolean makeThirdPartyRequest(Request req){
		String url="http://example.com/request";
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			String requestObj=mapper.writeValueAsString(req);
			System.out.println("Object to send:"+requestObj);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(requestObj));
			System.out.println("Executing request " + httpPost.getRequestLine());
			
			ResponseHandler<Integer> responseHandler = response -> {
				return response.getStatusLine().getStatusCode();
			};
			int status = httpclient.execute(httpPost, responseHandler);
			System.out.println("Third party request returned status code:"+status);
			return status >= 200 && status < 300;
		} catch (IOException e) {
			System.out.println("Third party request FAILED!");
			return false;
		}
	}

	@PostMapping(path = "/callback/{itemid}")
	@ResponseStatus
	public Response postCallback(@PathVariable("itemid") String itemid) {
		System.out.println("got "+itemid);
		return Response.status(204).build();
	}

	/*
	@PutMapping("/callback")
	@ResponseStatus
	public Response putCallback(@RequestBody Body callback) {
		return Response.status(204).build();
	}
	*/

	@GetMapping("/status")
	public Object doSomething(@RequestParam(value = "id", defaultValue = "") Integer id) {
		return new Object();
	}

}

// http://localhost:8080/greeting?name=blah
// ./mvnw spring-boot:run

//(may have to run mvn -N io.takari:maven:wrapper to get .mvn directory)

/*
 * curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"body":"value"}' http://localhost:8080/request
request initiated for document:value
 */
