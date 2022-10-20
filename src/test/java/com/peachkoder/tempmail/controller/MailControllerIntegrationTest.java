package com.peachkoder.tempmail.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

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
	
	@Test
	void shouldReturnOneDomain() throws Exception {
		
		when(mailService.createMail(1)).thenReturn("[\"42lqcj@bheps.com\"]");
		
		mockMvc.perform(get("/mail/new").param("action", "genRandomMailbox").param("count", "1")) 
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn();
		
		verify(mailService).createMail(1);
		
	}
	
	@Test
	void shouldReturnTwoDomains() throws Exception {
		
		when(mailService.createMail(2)).thenReturn("[\r\n"
				+ "    \"5bfan6ak31n9@bheps.com\",\r\n"
				+ "    \"rev56k@bheps.com\"\r\n"
				+ "]");
		
		mockMvc.perform(get("/mail/new").param("action", "genRandomMailbox").param("count", "2")) 
		.andDo(print()) 
		.andExpect(status().isOk())
		.andReturn();
		
		verify(mailService).createMail(2);
		
	}

}
