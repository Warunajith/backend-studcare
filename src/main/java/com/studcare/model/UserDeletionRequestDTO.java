package com.studcare.model;

import lombok.Data;

import java.util.Map;

@Data
public class UserDeletionRequestDTO {
	private String userEmail;
	private Map<String,String> headers;
	private Map<String,String> queryParams;
}
