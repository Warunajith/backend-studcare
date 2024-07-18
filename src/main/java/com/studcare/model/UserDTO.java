package com.studcare.model;

import com.studcare.data.jpa.entity.UserRole;
import lombok.Data;

@Data
public class UserDTO {
	private String email;
	private String password;
	private UserRole role;
	private	String username;
	private boolean isClassTeacher;
}
