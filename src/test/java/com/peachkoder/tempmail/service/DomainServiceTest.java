package com.peachkoder.tempmail.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.peachkoder.tempmail.model.dao.DomainDAO;
import com.peachkoder.tempmail.model.entity.Domain;

@ExtendWith(MockitoExtension.class)
public class DomainServiceTest {

	@InjectMocks
	private DomainService service;

	@Mock
	private DomainDAO dao;

	@Test
	void shouldCreateEmail() { 
		
		List<Domain> list = Arrays.asList(new Domain("login@domain.com"));

		when(service.getAllDomains()).thenReturn(list);

		assertEquals(list, service.getAllDomains());

	}

}
