package com.peachkoder.tempmail.model.dao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.peachkoder.tempmail.model.entity.Domain;
import com.peachkoder.tempmail.model.repository.DomainRepository;

@Component
public class DomainDAO {

	private HttpClient client = HttpClient.newHttpClient();

	@Autowired
	private DomainRepository repository;

	public List<Domain> getAllDomains() {

		URI uri;
		try {
			uri = new URI("https://www.1secmail.com/api/v1/?action=getDomainList");
			HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			String body = response.body();

			if (body.isBlank())
				return null;

			body = body.replace("[", "").replace("]", "").replace("\"", "");

			String[] domains = body.split(",");
			Set<String> set = new HashSet<>(Arrays.asList(domains)); 

			Set<Domain> collect = set.stream().map(domain -> new Domain(domain)).collect(Collectors.toSet());
			collect = removeAll(collect);

			try {
				repository.saveAll(collect);
				List<Domain> list = repository.findAll();
				return list;
			} catch (ConstraintViolationException ex) {
				System.err.println(ex.getMessage());
				return repository.findAll();

			}

		} catch (URISyntaxException | IOException | InterruptedException e) { 
			e.printStackTrace(); 
		}

		return repository.findAll();

	}

	private Set<Domain> removeAll(Set<Domain> set) {

		Set<Domain> setFromDb = new HashSet<>(repository.findAll());

		set.removeAll(setFromDb);

		return set;

	}
}
