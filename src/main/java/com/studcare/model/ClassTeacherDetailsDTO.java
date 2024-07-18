package com.studcare.model;

import com.studcare.data.jpa.dto.SubjectDTO;
import lombok.Data;

import java.util.List;

@Data
public class ClassTeacherDetailsDTO {
	private Long classId;
	private String className;
	private List<SubjectDTO> subjects;
}