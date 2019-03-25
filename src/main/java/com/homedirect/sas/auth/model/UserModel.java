package com.homedirect.sas.auth.model;

import lombok.Data;

/**
 *  @author: ducdd
 *    Email: duc.do@homedirect.com.vn
 *
 * Mar 5, 2019
 */

@Data
public class UserModel {

	private Long userId;
	private String userName, phone, email;
	
	private UserProfileModel userProfile;
	
}
