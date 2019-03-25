package com.homedirect.sas.auth.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.Setter;

/**
 *  @author: ducdd
 *    Email: duc.do@homedirect.com.vn
 *
 * Mar 5, 2019
 */

@Setter
public class UserAuthentication implements Authentication {

	private static final long serialVersionUID = 7135788647884228674L;

	private List<UserGrantedAuthority> authorities;
	private UserModel principal;
	private String credentials;
	
	@Override
	public String getName() {
		return principal.getUserProfile() == null || principal.getUserProfile().getFullName() == null 
				? principal.getUserName() : principal.getUserProfile().getFullName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getCredentials() {
		return credentials;
	}

	@Override
	public UserModel getDetails() {
		return principal;
	}

	@Override
	public UserModel getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return credentials != null;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
	}
}
