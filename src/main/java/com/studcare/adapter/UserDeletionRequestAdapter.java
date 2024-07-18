package com.studcare.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studcare.exception.StudCareRuntimeException;
import com.studcare.model.HttpRequestData;
import com.studcare.model.UserDTO;
import com.studcare.model.UserDeletionRequestDTO;
import com.studcare.model.UserRegisterRequestDTO;
import com.studcare.template.GenericRequestAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDeletionRequestAdapter implements GenericRequestAdapter<HttpRequestData, UserDeletionRequestDTO> {

	@Override
	public UserDeletionRequestDTO adapt(HttpRequestData httpRequestData) {
		UserDeletionRequestDTO userDeletionRequestDTO = new UserDeletionRequestDTO();
		userDeletionRequestDTO.setHeaders(httpRequestData.getHeaders());
		userDeletionRequestDTO.setQueryParams(httpRequestData.getQueryParams());
		userDeletionRequestDTO.setUserEmail(httpRequestData.getReference());
		return userDeletionRequestDTO;
	}

}