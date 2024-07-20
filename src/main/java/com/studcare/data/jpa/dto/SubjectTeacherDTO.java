package com.studcare.data.jpa.dto;

import com.studcare.model.UserDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SubjectTeacherDTO {
	private String teacherMail;
	private SubjectDTO subject;
	private UserDTO teacher;

	public SubjectTeacherDTO(SubjectDTO subject, UserDTO teacher) {
		this.subject = subject;
		this.teacher = teacher;
	}
}