package com.peachkoder.tempmail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peachkoder.tempmail.model.dao.DomainDAO;
import com.peachkoder.tempmail.model.entity.Domain;

@Service
public class DomainService {

	@Autowired
	private DomainDAO dao;
	
	public List<Domain> getAllDomains(){
		
		return dao.getAllDomains();
	}
}
