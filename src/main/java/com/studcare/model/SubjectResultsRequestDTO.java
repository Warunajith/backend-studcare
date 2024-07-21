package com.studcare.model;

import lombok.Data;

import java.util.List;

@Data
public class SubjectResultsRequestDTO {
	private String subjectName;
	private String academicYear;
	private String termNumber;
	private List<StudentResultDTO> studentResults;
}

