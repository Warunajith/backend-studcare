package com.studcare.model;

import lombok.Data;

import java.util.List;

@Data
public class SubjectResultsRequestDTO {
	private Long subjectId;
	private Integer academicYear;
	private Integer termNumber;
	private String teacherNote;
	private List<StudentResultDTO> studentResults;
}

