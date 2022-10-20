package com.peachkoder.tempmail.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peachkoder.tempmail.model.entity.Domain;

public interface DomainRepository extends JpaRepository<Domain, Integer> {

}
