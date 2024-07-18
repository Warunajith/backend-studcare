package com.studcare.data.jpa.dto;

import com.studcare.model.UserDTO;
import lombok.Data;

@Data
public class StudentDTO {
	private SchoolClassDTO schoolClassDTO;
	private WardDTO wardDTO;
	private UserDTO userDTO;
}
