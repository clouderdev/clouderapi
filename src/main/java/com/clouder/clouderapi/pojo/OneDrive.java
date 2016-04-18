package com.clouder.clouderapi.pojo;

import com.clouder.clouderapi.enums.CloudType;

public class OneDrive extends Cloud {

	private String accessToken;

	private String refreshToken;

	public OneDrive() {
		super(CloudType.ONEDRIVE);
	}
	
	public OneDrive(String accessToken, String refreshToken) {
		super(CloudType.ONEDRIVE);
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

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

	@Override
	public String toString() {
		return "OneDrive [accessToken=" + accessToken + ", refreshToken=" + refreshToken + "]";
	}
	
}
