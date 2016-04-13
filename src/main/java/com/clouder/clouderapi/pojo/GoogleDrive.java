package com.clouder.clouderapi.pojo;

import com.clouder.clouderapi.document.Cloud;

public class GoogleDrive extends Cloud {

	private String accessToken;

	private String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public GoogleDrive(String cloudType, String accessToken, String refreshToken) {
		super(cloudType);
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "GoogleDrive [accessToken=" + accessToken + ", refreshToken="
				+ refreshToken + "]";
	}

}
