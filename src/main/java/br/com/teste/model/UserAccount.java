package br.com.teste.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserAccount implements UserDetails {
	
	private static final long serialVersionUID = 4894467052425631739L;
	
	private Long id;
    private String name;
    private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;
    private Collection<Long> establishments;
    private Collection<GrantedAuthority> authorities;
    
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}