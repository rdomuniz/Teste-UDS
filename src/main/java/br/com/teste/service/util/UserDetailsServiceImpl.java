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

import br.com.teste.model.UserAccount;
import br.com.teste.model.enums.AcessoDoSistema;
import br.com.teste.model.enums.PermissaoDoSistema;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount user = new UserAccount();
		user.setAuthorities(new ArrayList<GrantedAuthority>());
		if(username.compareTo("ultramanutencao") == 0) {
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
		}
		return user;
	}
	
	public void updateDateAuthentication(Long id) {}

}
