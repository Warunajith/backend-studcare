package com.studcare.data.jpa.dto;

import com.studcare.model.UserDTO;
import lombok.Data;

import java.util.List;

@Data
public class WardDTO {
	private String wardName;
	private UserDTO hostelMaster;
}
