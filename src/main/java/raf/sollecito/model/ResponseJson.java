package raf.sollecito.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseJson {

	@JsonProperty(value="message")
	private String message;
	@JsonProperty(value="isError")
	private Boolean isError;
	
	
	public ResponseJson() {
		super();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getIsError() {
		return isError;
	}
	public void setIsError(Boolean isError) {
		this.isError = isError;
	}
	
	
	
}
