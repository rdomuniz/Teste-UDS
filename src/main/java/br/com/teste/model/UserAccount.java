package br.com.teste.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.teste.model.administracao.Usuario;
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
	public void fill(Usuario usuario) {
		if (usuario == null)
			return;
		this.id = usuario.getId();
		this.name = usuario.getNome();
		this.username = usuario.getLogin();
		this.password = usuario.getSenha();
		this.establishments = new ArrayList<Long>();
		this.authorities = new ArrayList<GrantedAuthority>();
		usuario.getGrupos().stream().filter(grupo -> grupo.getAtivo()).forEach(grupo -> {
			this.authorities.addAll(
				grupo.getAcessos().stream().map(acesso -> new SimpleGrantedAuthority(acesso.toString())).collect(Collectors.toList()).stream().filter(
					acesso -> !this.authorities.contains(acesso)
				).collect(Collectors.toList())
			);
			this.authorities.addAll(
				grupo.getPermissoes().stream().map(permissao -> new SimpleGrantedAuthority(permissao.toString())).collect(Collectors.toList()).stream().filter(
					permissao -> !this.authorities.contains(permissao)
				).collect(Collectors.toList())
			);
		});
	}
	
}