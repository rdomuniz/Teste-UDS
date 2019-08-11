package br.com.teste.service.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.teste.model.UserAccount;
import br.com.teste.model.UserDetailsResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	
	// EXPIRATION_TIME = 10 dias
	static final long EXPIRATION_TIME = 860_000_000;
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "bearer";
	static final String HEADER_STRING = "Authorization";
	static final String USER = "username";
	static final String ESTABLISHMENTS = "establishments";
	static final String AUTHORITIES = "authorities";
	
	public static void addAuthentication(HttpServletResponse response, Authentication auth) throws IOException {
		UserAccount user = (UserAccount)auth.getPrincipal();
		Map<String, Object> map = new HashMap<>();
        map.put(USER, user.getUsername());
        map.put(ESTABLISHMENTS, user.getEstablishments().stream()
    		.map(establishment -> establishment.toString())
    		.collect(Collectors.joining(","))
		);
        map.put(AUTHORITIES, auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))
        );
		String JWT = Jwts.builder()
			.setClaims(map)
			.setSubject(user.getId().toString())
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.signWith(SignatureAlgorithm.HS512, SECRET)
			.compact();
		response.getWriter().write(new ObjectMapper().writeValueAsString(new UserDetailsResponse(user,TOKEN_PREFIX + " " + JWT)));
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			Claims claims = Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody();
			if (claims.getSubject() != null) {
				UserAccount user = new UserAccount();
				user.setId(new Long(claims.getSubject()));
				user.setUsername(claims.get(USER).toString());
//				user.setEstablishments((StringUtils.isNotBlank(claims.get(ESTABLISHMENTS).toString()) ? Arrays.stream(claims.get(ESTABLISHMENTS).toString().split(","))
//	                .map(e -> new Long(e))
//	                .collect(Collectors.toList()) : new ArrayList<>()));
				Collection<GrantedAuthority> authorities = (StringUtils.isNotBlank(claims.get(AUTHORITIES).toString()) ? Arrays.stream(claims.get(AUTHORITIES).toString().split(","))
	                .map(a -> new SimpleGrantedAuthority("ROLE_"+a))
	                .collect(Collectors.toList()) : new ArrayList<>());
				user.setAuthorities(authorities);
				return new UsernamePasswordAuthenticationToken(user, null, authorities);
			}
		}
		return null;
	}
	
}
