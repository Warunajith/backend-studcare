package com.studcare.model;

import lombok.Data;

import java.util.List;

@Data
public class StudentResultsDTO {
	private Long studentId;
	private String studentName;
	private List<SubjectResultDTO> subjectResults;
}