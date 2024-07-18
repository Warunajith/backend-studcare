package com.studcare.model;

import lombok.Data;

@Data
public class AddTeacherToSubjectRequestDTO {
	private Long subjectId;
	private Long teacherId;
}