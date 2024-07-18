package com.studcare.model;

import lombok.Data;
import java.util.Map;

@Data
public class LogoutRequestDTO {
	private Map<String, String> headers;
	private Map<String, String> queryParams;
	private String email;
}