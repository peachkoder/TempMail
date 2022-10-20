package com.peachkoder.tempmail.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

@Service
public class MailService { 
	
	private HttpClient client = HttpClient.newHttpClient();
	
	
	public String createMail(Integer count) throws URISyntaxException, IOException, InterruptedException {
		
	    URI uri = new URI("https://www.1secmail.com/api/v1/?action=genRandomMailbox&count=" + count);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();
		
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		
		return response.body();
	}


	public String getMessages(String login, String domain) throws URISyntaxException, IOException, InterruptedException {
		
		StringBuilder builder = new StringBuilder();
		builder.append("https://www.1secmail.com/api/v1/?action=getMessages&login=");
		builder.append(login);
		builder.append("&domain=");
		builder.append(domain); 
		
		URI uri = new URI(builder.toString());
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();
		
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		return response.body();
		
	}


	public String getMessage(Long id, String login, String domain) throws URISyntaxException, IOException, InterruptedException {

		StringBuilder builder = new StringBuilder();
		builder.append("https://www.1secmail.com/api/v1/?action=readMessage&login=");
		builder.append(login);
		builder.append("&domain=");
		builder.append(domain); 
		builder.append("&id=");
		builder.append(id); 
		
		URI uri = new URI(builder.toString());
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();
		
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		return response.body();
	}

}
