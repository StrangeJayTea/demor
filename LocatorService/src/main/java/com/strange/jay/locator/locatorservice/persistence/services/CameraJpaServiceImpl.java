package com.strange.jay.locator.locatorservice.persistence.services;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class CameraJpaServiceImpl {

    /** An EntityManager to use for communicating with the database. */
    @PersistenceContext
    private EntityManager em;



}


