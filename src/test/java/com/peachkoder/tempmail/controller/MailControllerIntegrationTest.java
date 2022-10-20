package com.peachkoder.tempmail.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.peachkoder.tempmail.model.entity.Domain;
import com.peachkoder.tempmail.service.DomainService;
import com.peachkoder.tempmail.service.MailService;

@WebMvcTest
public class MailControllerIntegrationTest {
	
	@Autowired
	private WebApplicationContext applicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MailService mailService;	

	@MockBean
	private DomainService domainService;
	
	final String DOMAIN1 = "1domain@test.com";

	final String DOMAIN2 = "2domain@test.com";
	

	
	@Test
	void whenValidInput_shouldReturnOneDomain() throws Exception {
		
		final String newDomain = "["+DOMAIN1+"]";
		
		when(mailService.createMail(1)).thenReturn(newDomain);
		
		MvcResult response = mockMvc.perform(get("/mail/new").param("action", "genRandomMailbox").param("count", "1")) 
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn();
		
		verify(mailService).createMail(1);
		
		assertThat(newDomain).isEqualTo(response.getResponse().getContentAsString());
		
//		String responseBody = response.getResponse().getContentAsString();
		
//		ObjectMapper mapper = new ObjectMapper();
//		assertThat(responseBody).isEqualToIgnoringWhitespace(mapper.writeValueAsString(domain));
		
	}
	
	@Test
	void shouldReturnTwoDomains() throws Exception {
		
		final String newDomains = "["+DOMAIN1+"," + DOMAIN2+ "]";
		
		when(mailService.createMail(2)).thenReturn(newDomains); 
		
		MvcResult response = mockMvc.perform(get("/mail/new").param("action", "genRandomMailbox").param("count", "2")) 
		.andDo(print()) 
		.andExpect(status().isOk())
		.andReturn();
		
		verify(mailService).createMail(2);
		
		assertThat(newDomains).isEqualTo(response.getResponse().getContentAsString());
		
		
	}
	
	@Test
	void shouldGetAllDomains() throws Exception {
		
		
		List<Domain> listDomains = new ArrayList<Domain>();		
		listDomains.add(new Domain(DOMAIN1));
		listDomains.add(new Domain(DOMAIN2));
		
		when(domainService.getAllDomains()).thenReturn(listDomains);
		
		MvcResult response = mockMvc.perform(get("/mail/domainlist"))
			.andDo(print())
			.andExpect(status().isOk()) 
			.andReturn();
		
		verify(domainService).getAllDomains();
	}
	
	@Test
	void shouldReturnEmptyListOfDomains() throws Exception { 
		
		when(domainService.getAllDomains()).thenReturn(null);
		
		MvcResult response = mockMvc.perform(get("/mail/domainlist"))
				.andDo(print())
				.andExpect(status().isNotFound()) 
				.andReturn();
		
		verify(domainService).getAllDomains();
	}
	
	@Test
	void shoudlReturnMessagesList() throws Exception {
		
		String msg = "[{\r\n"
				+ "	\"id\": 639,\r\n"
				+ "	\"from\": \"someone@example.com\",\r\n"
				+ "	\"subject\": \"Some subject\",\r\n"
				+ "	\"date\": \"2018-06-08 14:33:55\"\r\n"
				+ "}]";
		
		when(mailService.getMessages("login", "domain.com"))
			.thenReturn(msg);
		
		MvcResult response = mockMvc.perform(get("/mail/messages").param("login", "login")
				.param("domain", "domain.com"))
			.andDo(print())		
			.andExpect(status().isOk())
			.andReturn();
		
		verify(mailService).getMessages("login", "domain.com");
		
		assertThat(response.getResponse().getContentAsString()).isEqualTo(msg);
		
	}
	
	@Test
	void shouldReturnNoneMessages() throws Exception {
		
		when(mailService.getMessages(any(String.class), any(String.class)))
			.thenReturn("[]");
		
		
		MvcResult response = mockMvc.perform(get("/mail/messages").param("login", "login")
				.param("domain", "domain.com"))
			.andDo(print())
			.andExpect(status().isNoContent())
			.andReturn();
		
		
		verify(mailService).getMessages(any(String.class), any(String.class));
		
		assertThat(response.getResponse().getContentLength()).isEqualTo(0);
		
	}
	
	@Test
	void shouldReturnAMessage() throws Exception {
		
		String msg = "{\r\n"
				+ "	\"id\": 639,\r\n"
				+ "	\"from\": \"someone@example.com\",\r\n"
				+ "	\"subject\": \"Some subject\",\r\n"
				+ "	\"date\": \"2018-06-08 14:33:55\",\r\n"
				+ "	\"attachments\": [{\r\n"
				+ "		\"filename\": \"iometer.pdf\",\r\n"
				+ "		\"contentType\": \"application\\/pdf\",\r\n"
				+ "		\"size\": 47412\r\n"
				+ "	}],\r\n"
				+ "	\"body\": \"Some message body\\n\\n\",\r\n"
				+ "	\"textBody\": \"Some message body\\n\\n\",\r\n"
				+ "	\"htmlBody\": \"\"\r\n"
				+ "}";
		
		
		when(mailService.getMessage(any(Long.class), any(String.class), any(String.class)))
			.thenReturn(msg);
		
		MvcResult response = mockMvc.perform(get("/mail/message")
				.param("id", "1")
				.param("login", "login")
				.param("domain", "1domain.com"))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		
		verify(mailService).getMessage(any(Long.class),any(String.class), any(String.class));
		
		assertThat(response.getResponse().getContentAsString()).isEqualTo(msg);
	
		
	}

}

