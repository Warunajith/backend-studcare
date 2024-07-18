package com.studcare.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class HttpResponseData {
	private Map<String,String> headers;
	private Map<String,String> queryParams;
	private String responseBody;
	private HttpStatus httpStatus;
}
