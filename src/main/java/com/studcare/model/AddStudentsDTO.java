package com.studcare.model;

import lombok.Data;

import java.util.List;

@Data
public class AddStudentsDTO {
	private Long classId;
	private UserDTO classTeacher;
	private List<String> students;
}
