package com.studcare.model;

import com.studcare.constants.Status;
import lombok.Data;

@Data
public class LogoutResponseDTO {
	private Status responseCode;
	private String message;
}