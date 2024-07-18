package com.studcare.model;

import com.studcare.data.jpa.dto.SchoolClassDTO;
import lombok.Data;

import java.util.Map;

@Data
public class ClassRequestDTO {
	private Map<String,String> headers;
	private Map<String,String> queryParams;
	private SchoolClassDTO schoolClassDTO;
	private String classTeacherEmail;
}
