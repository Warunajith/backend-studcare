package com.studcare.model;

import lombok.Data;

import java.util.List;

@Data
public class ClassResultsDTO {
	private Long classId;
	private String className;
	private List<StudentResultsDTO> studentResults;
}