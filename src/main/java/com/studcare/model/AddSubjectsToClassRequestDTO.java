package com.studcare.model;

import com.studcare.data.jpa.dto.SubjectTeacherDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AddSubjectsToClassRequestDTO {
	private SubjectsDTO subjects;
	private String className;
}
