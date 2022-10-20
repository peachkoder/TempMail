package com.peachkoder.tempmail.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
	
	@Mock
	private MailService service;

	@Test
	void testCreateMail() throws URISyntaxException, IOException, InterruptedException {
		
		when(service.createMail(1)).thenReturn("email created"); 
		
		assertEquals("email created", service.createMail(1));

		verify(service).createMail(1);
	}

	@Test
	void testGetMessages() throws URISyntaxException, IOException, InterruptedException {

		when(service.getMessages("login", "domain")).thenReturn("messages");
 
		assertEquals("messages", service.getMessages("login", "domain"));
		
		verify(service).getMessages("login", "domain");
		
	}

	@Test
	void testGetMessage() throws URISyntaxException, IOException, InterruptedException {

		when(service.getMessage(1L, "login", "domain")).thenReturn("message");
		 
		assertEquals("message", service.getMessage(1L, "login", "domain"));
		
		verify(service).getMessage(1L,"login", "domain");
	}

}
