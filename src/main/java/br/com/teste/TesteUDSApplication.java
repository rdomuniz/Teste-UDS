package br.com.teste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import br.com.teste.repository.GeralRepositoryImpl;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages="br.com.teste")
@EntityScan(basePackages="br.com.teste.model")
@EnableJpaRepositories(basePackages="br.com.teste.repository", repositoryBaseClass=GeralRepositoryImpl.class)
public class TesteUDSApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteUDSApplication.class, args);
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
}
