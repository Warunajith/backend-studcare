package com.studcare.model;

import lombok.Data;

import java.util.List;

@Data
public class StudentResultDTO {
	private String studentEmail;
	private String marks;
	private String grade;
	private String teacherNote;
	private List<TermResultsDTO> termResults;
}