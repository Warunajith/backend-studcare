package com.studcare.model;

import lombok.Data;

import java.util.List;

@Data
public class YearResultsDTO {
	private String academicYear;
	private List<TermResultsDTO> termResults;
}
