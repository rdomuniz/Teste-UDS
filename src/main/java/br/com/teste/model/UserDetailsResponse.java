package br.com.teste.model;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class UserDetailsResponse {
	private Long id;
    private String name;
    private String username;
    private String email;
    private Collection<Long> establishments;
    private Collection<String> authorities;
    private String token; 
    
    public UserDetailsResponse(UserAccount user, String token) {
    	this.id = user.getId();
    	this.name = user.getName();
    	this.username = user.getUsername();
    	this.email = user.getEmail();
    	this.establishments = user.getEstablishments();
    	this.authorities = user.getAuthorities().stream().map(authorities -> authorities.toString()).collect(Collectors.toList());
    	this.token = token;
	}
}