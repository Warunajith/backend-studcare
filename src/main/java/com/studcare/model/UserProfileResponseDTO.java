package com.studcare.model;

import com.studcare.constants.Status;
import lombok.Data;

@Data
public class UserProfileResponseDTO {
	private Status responseCode;
	private String message;
	private UserDTO userDTO;
}