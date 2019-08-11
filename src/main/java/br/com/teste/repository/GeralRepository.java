package br.com.teste.repository;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GeralRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	public Long carregaPrimeiroCodigoDisponivel();
	public Session getSession();
	
}
