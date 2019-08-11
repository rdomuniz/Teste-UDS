package br.com.teste.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

public class GeralRepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	public Session getSession() {
		return (Session) entityManager.getDelegate();
	}
	
}
