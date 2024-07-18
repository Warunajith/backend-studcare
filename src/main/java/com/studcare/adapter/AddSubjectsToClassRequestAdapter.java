package com.studcare.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studcare.exception.StudCareRuntimeException;
import com.studcare.model.AddStudentsDTO;
import com.studcare.model.AddStudentsRequestDTO;
import com.studcare.model.AddSubjectsToClassRequestDTO;
import com.studcare.model.HttpRequestData;
import com.studcare.template.GenericRequestAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AddSubjectsToClassRequestAdapter implements GenericRequestAdapter<HttpRequestData, AddSubjectsToClassRequestDTO> {
	@Autowired private ObjectMapper objectMapper;

	@Override public AddSubjectsToClassRequestDTO adapt(HttpRequestData httpRequestData) {
		AddSubjectsToClassRequestDTO subjectsToClassRequestDTO = new AddSubjectsToClassRequestDTO();
		subjectsToClassRequestDTO.setClassName(httpRequestData.getReference());
		subjectsToClassRequestDTO.setSubjectTeachers(mapTeachers(httpRequestData.getRequestBody()));
		return subjectsToClassRequestDTO;
	}

	private List mapTeachers(String requestBody) {
		try {
			return objectMapper.readValue(requestBody, List.class);
		} catch (JsonProcessingException exception) {
			log.error("AddStudentRequestAdapter.mapTeachers(): map user register request to user object failed", exception);
			throw new StudCareRuntimeException("request mapping failed");
		}
	}
}