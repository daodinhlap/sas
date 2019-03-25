package com.homedirect.sas.auth.security;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

/**
 *  @author: ducdd
 *    Email: duc.do@homedirect.com.vn
 *
 * Feb 27, 2019
 */

@Getter
public class JwtConfig {
	
	@Value("${security.jwt.uri:/auth/**}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.jwt.secret:qpfK6gr0E6VngaIKEj84}")
    private String secret;

}
