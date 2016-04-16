package com.clouder.clouderapi.enums;

public enum CloudType {

	GOOGLEDRIVE("GOOGLEDRIVE"), ONEDRIVE("ONEDRIVE"), DROPBOX("DROPBOX");

	private String VALUE;

	private CloudType(String Value) {
		this.VALUE = Value;
	}

	@Override
	public String toString() {
		return this.VALUE;
	}
}
