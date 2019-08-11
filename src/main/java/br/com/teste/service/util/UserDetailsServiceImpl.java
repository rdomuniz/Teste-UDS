package br.com.teste.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.teste.exception.ResolvedException;
import br.com.teste.model.UserAccount;
import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.enums.AcessoDoSistema;
import br.com.teste.model.enums.PermissaoDoSistema;
import br.com.teste.service.administracao.UsuarioService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UsuarioService usuarioService;
	@Autowired
	public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	@Autowired
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount user = new UserAccount();
		user.setAuthorities(new ArrayList<GrantedAuthority>());
		if(username.compareTo("udsmanutencao") == 0) {
			user.setId(Long.valueOf(0));
			user.setName("Manutenção");
			user.setUsername(username);
			user.setPassword(bCryptPasswordEncoder.encode("password"));
			user.setEstablishments(new ArrayList<Long>());
			user.setAuthorities(new ArrayList<GrantedAuthority>());
			List<AcessoDoSistema> acessos = AcessoDoSistema.getAdministracao();
			acessos.addAll(AcessoDoSistema.getSemAdministracao());
			user.getAuthorities().addAll(acessos.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList()));
			user.getAuthorities().addAll(Arrays.stream(PermissaoDoSistema.values()).filter(r -> acessos.contains(r.getAcessoDoSistema())).collect(Collectors.toList()).stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList()));
		} else {
			Usuario usuario = usuarioService.buscarPorLogin(username);
			if(usuario.isInativo())
				throw new ResolvedException("Usuário inativo");
			user.fill(usuario);
			if(usuario.getAdministrador()) {
				List<AcessoDoSistema> administracao = AcessoDoSistema.getAdministracao();
				user.getAuthorities().addAll(administracao.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList()));
				user.getAuthorities().addAll(Arrays.stream(PermissaoDoSistema.values()).filter(r -> administracao.contains(r.getAcessoDoSistema())).collect(Collectors.toList()).stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList()));
			}
		}
		return user;
	}
	
	public void updateDateAuthentication(Long id) {
		if(id.compareTo(Long.valueOf(0)) == 0)
			return;
		usuarioService.atualizarDataDeAutenticacao(id);
	}

}
