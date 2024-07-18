package com.studcare.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studcare.exception.StudCareRuntimeException;
import com.studcare.model.HttpRequestData;
import com.studcare.model.UserDTO;
import com.studcare.model.UserProfileRequestDTO;
import com.studcare.template.GenericRequestAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserProfileRequestAdapter implements GenericRequestAdapter<HttpRequestData, UserProfileRequestDTO> {

	@Override
	public UserProfileRequestDTO adapt(HttpRequestData httpRequestData) {
		UserProfileRequestDTO userProfileRequestDTO = new UserProfileRequestDTO();
		userProfileRequestDTO.setHeaders(httpRequestData.getHeaders());
		userProfileRequestDTO.setQueryParams(httpRequestData.getQueryParams());
		userProfileRequestDTO.setUserEmail(httpRequestData.getReference());
		return userProfileRequestDTO;
	}

}