package com.homedirect.sas.auth.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.homedirect.sas.auth.client.UserServiceClient;

/**
 *  @author: ducdd
 *    Email: duc.do@homedirect.com.vn
 *
 * Feb 27, 2019
 */

@EnableWebSecurity
public class SecurityCredentialsConfig extends WebSecurityConfigurerAdapter {

	private @Autowired JwtConfig jwtConfig;
	private @Autowired UserServiceClient userServiceClient;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		// make sure we use stateless session; session won't be used to store user's state.
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		// handle an authorized attempts 
		.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
		.and()
		// Add a filter to validate user credentials and add token in the response header

		// What's the authenticationManager()? 
		// An object provided by WebSecurityConfigurerAdapter, used to authenticate the user passing user's credentials
		// The filter needs this auth manager to authenticate the user.
		.addFilter(new JwtAuthenticationFilter(jwtConfig, userServiceClient))	
		.authorizeRequests()
		// allow all POST requests 
		.antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
		// any other requests must be authenticated
		.anyRequest().authenticated();
		
		afdsfsd
	}
}
