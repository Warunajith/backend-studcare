package com.studcare.model;

import lombok.Data;

@Data
public class AddTeacherToSubjectRequestDTO {
	private String subject;
	private String teacher;
}