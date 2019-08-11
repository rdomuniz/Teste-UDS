package br.com.teste.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.teste.model.AccountCredentials;
import br.com.teste.model.UserAccount;
import br.com.teste.service.util.TokenAuthenticationService;
import br.com.teste.service.util.UserDetailsServiceImpl;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private UserDetailsService userDetailsService;

	public JWTLoginFilter(String url, AuthenticationManager authManager, UserDetailsService userDetailsService) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		AccountCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
		return getAuthenticationManager().authenticate(
			new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), 
				credentials.getPassword()
			)
		);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication auth) throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(response, auth);
		((UserDetailsServiceImpl)userDetailsService).updateDateAuthentication(((UserAccount)auth.getPrincipal()).getId());
	}

}
