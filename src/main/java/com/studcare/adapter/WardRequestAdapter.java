package com.studcare.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studcare.data.jpa.dto.WardDTO;
import com.studcare.exception.StudCareRuntimeException;
import com.studcare.model.HttpRequestData;
import com.studcare.model.WardRequestDTO;
import com.studcare.template.GenericRequestAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class WardRequestAdapter implements GenericRequestAdapter<HttpRequestData, WardRequestDTO> {
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public WardRequestDTO adapt(HttpRequestData httpRequestData) {
		WardRequestDTO wardRequestDTO = new WardRequestDTO();
		wardRequestDTO.setWardDTO(mapWardData(httpRequestData.getRequestBody()));
		return wardRequestDTO;
	}

	private WardDTO mapWardData(String requestBody) {
		try {
			return objectMapper.readValue(requestBody, WardDTO.class);
		} catch (JsonProcessingException exception) {
			log.error("WardRequestAdapter.mapWardData(): map user register request to user object failed", exception);
			throw new StudCareRuntimeException("user register request to user object failed");
		}
	}
}
