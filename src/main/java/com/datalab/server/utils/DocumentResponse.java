package com.datalab.server.utils;

import java.io.Serializable;
import java.util.HashMap;

import com.datalab.server.enums.ResponseCode;

public class DocumentResponse implements Serializable {
	private static final long serialVersionUID = -1638229205485160264L;
	
	private ResponseCode responseCode;
	private String responseMessage;
	private HashMap<String, Object> responseData;	
	
	public DocumentResponse() {
	}
			
	public DocumentResponse(ResponseCode responseCode, String responseMessage) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public DocumentResponse(ResponseCode responseCode, String responseMessage, HashMap<String, Object> responseData) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseData = responseData;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public HashMap<String, Object> getResponseData() {
		return responseData;
	}

	public void setResponseData(HashMap<String, Object> responseData) {
		this.responseData = responseData;
	}
}