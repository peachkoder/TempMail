package com.peachkoder.tempmail.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

}
