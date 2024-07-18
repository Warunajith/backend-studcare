package com.studcare.model;

import com.studcare.data.jpa.entity.UserRole;
import lombok.Data;

import java.util.Map;

@Data
public class UserRegisterRequestDTO {
	private Map<String,String> headers;
	private Map<String,String> queryParams;
	private UserDTO userDTO;
}
