package com.clouder.clouderapi.pojo;

public class ResponseEntity {
	Object response;
	ResponseStatus status;

	public ResponseEntity(Object response, ResponseStatus status) {
		super();
		this.response = response;
		this.status = status;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseEntity [response=" + response + ", status=" + status + "]";
	}

}
