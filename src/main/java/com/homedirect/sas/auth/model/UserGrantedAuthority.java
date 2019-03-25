package com.homedirect.sas.auth.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

/**
 *  @author: ducdd
 *    Email: duc.do@homedirect.com.vn
 *
 * Mar 5, 2019
 */

@Data
public class UserGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = -6669767958592093787L;

	private String authority;

	@Override
	public String getAuthority() {
		return authority;
	}

}
