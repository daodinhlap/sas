package com.homedirect.sas.auth.model;

import lombok.Data;

/**
 *  @author: ducdd
 *    Email: duc.do@homedirect.com.vn
 *
 * Mar 5, 2019
 */

@Data
public class UserProfileModel {

	private Long userId;
	private String userName, fullName, identityCard;
	private String address, province, district;
}
