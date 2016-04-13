package com.clouder.clouderapi.document;

public class DropBox extends Cloud {

	private String dropBoxAccessToken;

	private String dropboxRefreshToken;

	public DropBox(String cloudType, String dropBoxAccessToken,
			String dropboxRefreshToken) {
		super(cloudType);
		this.dropBoxAccessToken = dropBoxAccessToken;
		this.dropboxRefreshToken = dropboxRefreshToken;
	}

	public String getDropBoxAccessToken() {
		return dropBoxAccessToken;
	}

	public void setDropBoxAccessToken(String dropBoxAccessToken) {
		this.dropBoxAccessToken = dropBoxAccessToken;
	}

	public String getDropboxRefreshToken() {
		return dropboxRefreshToken;
	}

	public void setDropboxRefreshToken(String dropboxRefreshToken) {
		this.dropboxRefreshToken = dropboxRefreshToken;
	}

	@Override
	public String toString() {
		return "DropBox [dropBoxAccessToken=" + dropBoxAccessToken
				+ ", dropboxRefreshToken=" + dropboxRefreshToken + "]";
	}

}
