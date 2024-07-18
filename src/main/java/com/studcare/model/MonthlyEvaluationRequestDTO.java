package com.studcare.model;

import lombok.Data;

@Data
public class MonthlyEvaluationRequestDTO {
	private Integer evaluationMonth;
	private Integer evaluationYear;
	private String behavioralData;
	private String extraNote;
	private String extracurricularActivities;
	private String healthData;
}