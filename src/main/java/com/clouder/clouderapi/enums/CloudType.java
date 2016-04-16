package com.clouder.clouderapi.enums;

public enum CloudType {

	GOOGLEDRIVE("GOOGLEDRIVE"),
	ONEDRIVE("ONEDRIVE"),
	DROPBOX("DROPBOX")
	;

<<<<<<< HEAD
}
=======
	private String VALUE;
	
	private CloudType(String Value) {
		this.VALUE = Value;
	}
	
	@Override
	public String toString() {
		return this.VALUE;
	}
}
>>>>>>> f80886be39832de301d49cc37644cba227017e68
