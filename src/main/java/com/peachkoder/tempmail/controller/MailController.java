package com.peachkoder.tempmail.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peachkoder.tempmail.service.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping("/new")
	private ResponseEntity<String> createMail(@RequestParam Integer count) {
		try {
			String mails = mailService.createMail(count);
			return ResponseEntity.ok(mails);
		} catch (URISyntaxException | IOException | InterruptedException e) { 
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}
	
	@RequestMapping("/domainlist")
	private ResponseEntity<String> getActiveDomains() {
		try {
			String list = mailService.getDomainList();
			return ResponseEntity.ok(list);
		} catch (URISyntaxException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}
	
	@RequestMapping("/messages")
	private ResponseEntity<?> getMessages(@RequestParam @NonNull String login, @RequestParam @NonNull String domain){
		String message;
		try {
			message = mailService.getMessages(login, domain);
			if (message.equals("[]"))
				return ResponseEntity.noContent().build();
			
			return ResponseEntity.ok(message);
		} catch (URISyntaxException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}
	
	@RequestMapping("/message")
	private ResponseEntity<String> getMessage(@RequestParam @NonNull String login, @RequestParam @NonNull String domain,
			@RequestParam @NonNull Long id){
		String message;
		try {
			message = mailService.getMessage(id, login, domain);
			return ResponseEntity.ok(message);
		} catch (URISyntaxException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}	

} 
