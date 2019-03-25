package com.homedirect.sas.auth.security;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homedirect.sas.auth.client.UserServiceClient;
import com.homedirect.sas.auth.model.UserAuthentication;
import com.homedirect.sas.auth.model.UserCredentials;
import com.homedirect.sas.auth.model.UserProfileModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *  @author: ducdd
 *    Email: duc.do@homedirect.com.vn
 *
 * Feb 27, 2019
 */

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtConfig jwtConfig;
	private final UserServiceClient userServiceClient;

	public JwtAuthenticationFilter(JwtConfig jwtConfig, UserServiceClient userServiceClient) {
		this.jwtConfig = jwtConfig;
		this.userServiceClient = userServiceClient;

		// By default, UsernamePasswordAuthenticationFilter listens to "/login" path. 
		// In our case, we use "/auth". So, we need to override the defaults.
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
			return userServiceClient.login(creds);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Upon successful authentication, generate a token.
	// The 'auth' passed to successfulAuthentication() is the current authenticated user.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		UserAuthentication userAuth = (UserAuthentication) auth;

		Long now = System.currentTimeMillis();
		String username = userAuth.getPrincipal().getUserName();

		UserProfileModel userProfileModel = userAuth.getPrincipal().getUserProfile();
		String fullName = userProfileModel == null || userProfileModel.getFullName() == null ? username : userProfileModel.getFullName();

		String token = Jwts.builder()
				.setSubject(username)
				// Convert to list of strings. 
				// This is important because it affects the way we get them back in the Gateway.
				.claim("authorities", userAuth.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.claim("fullName", fullName)
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
				.compact();

		// Add token to header
		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
	}
}
