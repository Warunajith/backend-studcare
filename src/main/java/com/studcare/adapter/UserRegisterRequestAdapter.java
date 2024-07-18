package com.studcare.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studcare.exception.StudCareRuntimeException;
import com.studcare.model.HttpRequestData;
import com.studcare.model.UserDTO;
import com.studcare.model.UserRegisterRequestDTO;
import com.studcare.template.GenericRequestAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRegisterRequestAdapter implements GenericRequestAdapter<HttpRequestData, UserRegisterRequestDTO> {
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public UserRegisterRequestDTO adapt(HttpRequestData httpRequestData) {
		UserRegisterRequestDTO userRegisterRequestDTO = new UserRegisterRequestDTO();
		userRegisterRequestDTO.setHeaders(httpRequestData.getHeaders());
		userRegisterRequestDTO.setQueryParams(httpRequestData.getQueryParams());
		userRegisterRequestDTO.setUserDTO(mapUserData(httpRequestData.getRequestBody()));
		return userRegisterRequestDTO;
	}

	private UserDTO mapUserData(String requestBody) {
		try {
			return objectMapper.readValue(requestBody, UserDTO.class);
		} catch (JsonProcessingException exception) {
			log.error("UserRegisterRequestAdapter.mapUserData(): map user register request to user object failed", exception);
			throw new StudCareRuntimeException("user register request to user object failed");
		}
	}
}
