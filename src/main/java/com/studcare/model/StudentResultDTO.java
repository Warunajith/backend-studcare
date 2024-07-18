package com.studcare.model;

import lombok.Data;

@Data
public class StudentResultDTO {
	private Long studentId;
	private Integer marks;
	private String grade;
}