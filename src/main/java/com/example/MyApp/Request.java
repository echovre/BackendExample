package com.example.MyApp;

public class Request {

	private String body;
	private String id;

	public Request() {
	}

	public Request(String body, String id) {
		this.body= body;
		this.id=id;
	}

	public String getId() {
		return id;
	}
	
	public String getBody() {
		return body;
	}
}
