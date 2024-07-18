package com.studcare.model;

import com.studcare.data.jpa.dto.SchoolClassDTO;
import com.studcare.data.jpa.dto.SubjectDTO;
import lombok.Data;

import java.util.List;

@Data
public class SubjectClassesDTO {
	private SubjectDTO subject;
	private List<SchoolClassDTO> classes;

	public SubjectClassesDTO(SubjectDTO subject, List<SchoolClassDTO> classes) {
		this.subject = subject;
		this.classes = classes;
	}
}