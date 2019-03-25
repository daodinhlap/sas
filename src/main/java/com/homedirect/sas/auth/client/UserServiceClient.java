package com.homedirect.sas.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.homedirect.sas.auth.model.UserAuthentication;
import com.homedirect.sas.auth.model.UserCredentials;

/**
 *  @author: ducdd
 *    Email: duc.do@homedirect.com.vn
 *
 * Mar 5, 2019
 */

@FeignClient("sas-user-service")
public interface UserServiceClient {

	@PostMapping("/login")
	UserAuthentication login(UserCredentials userCredentials);
}
