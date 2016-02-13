package com.landcraft.server.enums;

public enum ResponseCode {
	SUCCESS(0),
	FAILURE(1);
	
	private Integer code;
	
	private ResponseCode(Integer code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return code;
	}
}
